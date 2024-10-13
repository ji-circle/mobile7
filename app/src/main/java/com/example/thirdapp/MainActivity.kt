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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.thirdapp.ui.theme.ThirdAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThirdAppTheme {
//                MainScreen()
                val navController = rememberNavController()
                //NavHost 중 string 있는거...
                NavHost(navController = navController, startDestination = "main") {
                    composable(route = "main") {
                        MainScreen(navController = navController)
                    }
                    composable(route = "analysis") {
                        AnalysisScreen(navController = navController)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
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

            //버튼 하나 생성...
            //  버튼 하나 누르면 스택에 창이 하나 더 쌓이게
            Spacer(modifier = Modifier.padding(vertical = 16.dp))
            ElevatedButton(
                modifier = Modifier.fillMaxWidth(),
                //navController.navigate는 route:String 있는거
                onClick = { navController.navigate(route = "analysis") }
            ) {
                Text("Enter")
            }
        }
    }
}

//추가...
//build.gradle.kts에서 (project > ThirdApp > app> build.gradle.kts)
//    implementation(libs.androidx.navigation.compose) 추가했음
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Analysis Result") },
                navigationIcon = {
                    //navController.navigateUp() ==> 뒤로가기 버튼 눌렀을 때 뒤로가기 실행됨
                    //  메인화면 위에 이 화면이 스택으로 쌓인거임...
                    IconButton(onClick = {navController.navigateUp()}) {
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
                .padding(16.dp)
        ) {

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