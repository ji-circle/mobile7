package com.example.thirdapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.thirdapp.ui.theme.ThirdAppTheme
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThirdAppTheme {
                //viewModel을 여기서 부른다
                val viewModel = viewModel<BmiViewModel>()

                //recomposition이 되니까, bmi가 뷰모델에서 업데이트되면 이 bmi도 업데이트됨...
                val bmi = viewModel.result.value

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main") {
                    composable(route = "main") {
                        MainScreen{height, weight, selectedOption, checked ->
                            viewModel.bmiCalculate(height, weight, selectedOption, checked)
                            // 위 코드로, viewmodel 안의 bmi 값이 업데이트된다.
                            navController.navigate(route = "analysis")
                        }
                    }
//                    composable(route = "analysis/{value}") {
                    //뷰모델 쓰면 아래 코드로..
                    composable(route = "analysis") {
                        AnalysisScreen(
                            navController = navController,
                            result = bmi
                        )
                    }
                }
            }
        }
    }
}

//build.gradle에 "androidx.lifecycle:lifecycle-viewmodel-compose 추가하기
class BmiViewModel : ViewModel() {
    //viewModel에서는 remembersaveable을 사용할 필요가 없음...
    //  어차피 composable function 안에 있는 게 아니기때문에, composable function이 다시 호출되더라도 다시 기억할 필요가 없음...
    private val _result = mutableStateOf("Default")
    val result: State<String> = _result
    // result 안에서, 따로 로직을 통해서만 값을 바꿀 수 있게 하고 싶을 때.
    //  수정은 안되고 읽기만 되게 하게 하고 싶으면
    //    private 로 _ 포함해 만들고, 밑에 val으로 추가.
    // State는 읽기만 가능

    fun bmiCalculate(
        height: Double, weight: Double, selectedOption: String, checked: Boolean
    ) {
        val resultList = listOf(
            "Underweight",
            "Normal",
            "Overweight",
            "Obese",
            "Not Allowed"
        )
        val bmi = weight / (height / 100.0).pow(2.0)
        if (checked) {
            _result.value = resultList[4]
        }
        when (selectedOption) {
            "Simplified" -> {
                if (bmi >= 25.0) _result.value = resultList[2]
                else _result.value = resultList[1]
            }

            "Normal" -> {
                if (bmi >= 25.0) _result.value = resultList[2]
                else if (bmi <= 18.5) _result.value = resultList[0]
                else _result.value = resultList[1]
            }

            "Detailed" -> {
                if (bmi >= 30.0) _result.value = resultList[3]
                else if (bmi >= 25.0) _result.value = resultList[2]
                else if (bmi <= 18.5) _result.value = resultList[0]
                else _result.value = resultList[1]
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onResultClicked: (Double, Double, String, Boolean) -> Unit
) {
    var height by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }
    var selectedOption by rememberSaveable { mutableStateOf("Normal") }
    var checked by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Obesity Analysis") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = height,
                onValueChange = { height = it },
                label = { Text("Height") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Weight") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.padding(vertical = 16.dp))
            Text(text = "Choose the analysis style.")
            RadioButtonSet(selectedOption = selectedOption, onChange = { selectedOption = it })

            Spacer(modifier = Modifier.padding(vertical = 16.dp))
            CheckBoxSet(checked = checked, onChange = { checked = it })

            Spacer(modifier = Modifier.padding(vertical = 16.dp))
            ElevatedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (height.isNotEmpty() && weight.isNotEmpty()) {
                        onResultClicked(
                            height.toDouble(),
                            weight.toDouble(),
                            selectedOption,
                            checked
                        )
                    }
                }
            ) {
                Text("Enter")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen(navController: NavController, result: String) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Analysis Result") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Your Obesity result is...", fontSize = 20.sp)
            Spacer(modifier = Modifier.padding(vertical = 16.dp))
            Text(text = result, fontSize = 30.sp)
        }
    }
}

@Composable
fun RadioButtonSet(selectedOption: String, onChange: (String) -> Unit) {
    val radioOptions = listOf("Simplified", "Normal", "Detailed")

    Column {
        radioOptions.forEach { i ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (i == selectedOption),
                    onClick = { onChange(i) }
                )
                Text(text = i)
            }
        }
    }
}

@Composable
fun CheckBoxSet(checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text("Do you agree to the analysis of your height and weight?")
        Checkbox(
            checked = checked,
            onCheckedChange = { onChange(it) },
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}