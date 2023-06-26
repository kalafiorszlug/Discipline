package com.example.discipline


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.discipline.ui.theme.DisciplineTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DisciplineTheme() {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    LoginScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(){
    Column(modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        //stats
        Box(modifier = Modifier
            .height(50.dp)){

            Column() {

                Row() {

                    Image( modifier = Modifier
                        .size(100.dp),
                        painter = painterResource(R.drawable.icons8_task_completed_50),
                        contentDescription = null)

                    Text(text = "Productivity")
                }

            }
        }

        //rewards
        Box(){

        }

        //tasks
        Box(){

        }

        //score
        Box(){

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  Surface(color = Color.LightGray) {
      DisciplineTheme {
          MainScreen()
      }
  }
}