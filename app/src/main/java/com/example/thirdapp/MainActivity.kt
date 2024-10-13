package com.example.thirdapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
    //상태 만들어주기
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
                //안의 텍스트필드들의 가로 패딩
                .padding(16.dp)
        ) {
            //키 입력받을 텍스트필드
            //TextField는 2개의 파라미터를 받아야 함...
            //  value = string을 받음 (나타나고 있는 input값)
            //  onValueChange = 함수를 받음 (데이터가 변경되었을 경우(=입력), 어떤 동작을 할건지)
            //    변화를 감지하려면 상태를 만들어줘야 함
            //  onvaluechange는 (string) -> Unit 임... 하나의 파라미터를 받는 함수
            //  onValueChange = { input -> height = input } ==> input을 받으면 height에 input을 입력

            // height라는 값을 텍스트필드가 보여주게 되는 거임... height가 바뀌면서 그 바뀐 값이 보여지게 됨
            // 값을 입력한 순간, 입력받은거로 뭘 할건지를 onvaluechange에서... 입력마다 onvaluechange가 호출됨
            //  height라는 상태가 변경되었기 떄문에, composable function이 다시 호출됨...
            //    그럼 height(<-remembersaveable이라 제외됨)를 제외한 나머지부분이 다시 호출됨
            //  그럼 textfield를 다시 그릴 때, 해당 바뀐 value가 반영됨
//            TextField(value = height, onValueChange = { input -> height = input })
            // 하나의 파라미터를 받는 함수... 해당 파라미터를 굳이 명시하지 않고 it 사용 가능
            //TextField(value = height, onValueChange = { height = it })

            //Outlined = 가장자리 선 생긴다
//            OutlinedTextField(value = height, onValueChange = { height = it })

            //텍스트필드가 가로로 꽉 차게 하려면
            OutlinedTextField(
                value = height,
                onValueChange = { height = it },
                //텍스트필드에 라벨을 주려면 (= 설명... 클릭 시 위로 올라감)
                label = { Text("Height") },
                modifier = Modifier.fillMaxWidth(),
                //입력 시 키보드가 숫자로 나왔으면 좋겠다면 (//근데 입력은 영어도 됨... 거르진 않나봄)
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Weight") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ThirdAppTheme {
        Greeting("Android")
    }
}