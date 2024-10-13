package com.example.thirdapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
            //Spacer() = 공간을 떨어뜨려 주는...
            Spacer(modifier = Modifier.padding(vertical = 16.dp))  
            Text(text = "Choose the analysis style.")
            //여기에 라디오버튼셋 추가
            RadioButtonSet()
        }
    }
}

@Composable
fun RadioButtonSet() {
    //아래 3가지 리스트에 대해서...
    val radioOptions = listOf("Simplified", "Normal", "Detailed")
    //현재 선택되어 있는 것... 여기선 Normal로 초기화함
    var selectedOption by rememberSaveable { mutableStateOf(radioOptions[1]) }


    Column {
        //아래 Row 단위로 복사하면 됨..
        Row(
            //라디오버튼과 텍스트를 y축끼리 가운데정렬...
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                //selected 는 boolean임,
                //  아래 조건대로면 selectedOption이 Simplified가 되었는지 확인하고, true라면 거기에 버튼이 표시됨
                //onClick는 () -> Unit임... 특별히 아무것도 없는 파라미터를 받음...선택을 한 것을 의미
                selected = ("Simplified" == selectedOption),
                onClick = { selectedOption = "Simplified" }
                // 다시 클릭한다고 해서 취소되진 않음
            )
            Text(text = "Simplified")
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = ("Normal" == selectedOption),
                onClick = { selectedOption = "Normal" }
            )
            Text(text = "Normal")
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = ("Detailed" == selectedOption),
                onClick = { selectedOption = "Detailed" }
            )
            Text(text = "Detailed")
        }
        //Detailed 라디오버튼을 누른 순간 onClick 콜백function이 실행되고
        //  selectedOption에 "Detailed"가 들어감
        //    그래서 RadioButtonSet() 을 다시 실행시킴
        // 다시 그릴 때  selected = ("Detailed" == selectedOption) 부분들을 거쳐 true인 부분까지 내려와 실행됨
    }
}