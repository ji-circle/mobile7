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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.thirdapp.ui.theme.ThirdAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThirdAppTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var height by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }
    //RadioButtonSet() 에서 이동
    var selectedOption by rememberSaveable { mutableStateOf("Normal") }
    //CheckBoxSet() 에서 이동
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
            //상태 호이스팅으로 변경
            RadioButtonSet(selectedOption = selectedOption, onChange = { selectedOption = it })

            Spacer(modifier = Modifier.padding(vertical = 16.dp))
            CheckBoxSet(checked = checked, onChange = { checked = it })
        }
    }
}

@Composable
fun RadioButtonSet(selectedOption: String, onChange: (String) -> Unit) {
    val radioOptions = listOf("Simplified", "Normal", "Detailed")
//    var selectedOption by rememberSaveable { mutableStateOf(radioOptions[1]) }

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

//        //foreach 사용하지 않은 경우
//        Row(
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            RadioButton(
//                selected = ("Simplified" == selectedOption),
////                onClick = { selectedOption = "Simplified" }
//                onClick = { onChange("Simplified") }
//                //onChange는 위에서 구현할것임!
//            )
//            Text(text = "Simplified")
//        }
//
//        Row(
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            RadioButton(
//                selected = ("Normal" == selectedOption),
////                onClick = { selectedOption = "Normal" }
//                onClick = { onChange("Normal") }
//            )
//            Text(text = "Normal")
//        }
//
//        Row(
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            RadioButton(
//                selected = ("Detailed" == selectedOption),
////                onClick = { selectedOption = "Detailed" }
//                onClick = { onChange("Detailed") }
//            )
//            Text(text = "Detailed")
//        }
    }
}

@Composable
fun CheckBoxSet(checked: Boolean, onChange: (Boolean) -> Unit) {
//    var checked by rememberSaveable { mutableStateOf(false) }
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