package com.example.discipline


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        horizontalAlignment = Alignment.CenterHorizontally) {

        //stats
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .align(Alignment.CenterHorizontally)){
            Button(onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)) {
                Column(modifier = Modifier
                    .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(modifier = Modifier
                        .size(200.dp)
                        .weight(8f),
                        painter = painterResource(R.drawable.graph_icon),
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(modifier = Modifier.weight(2f), text = "Check statistics", fontSize = 15.sp)
                    }
                }
            }

        Divider(color = Color.Gray, thickness = 2.dp)

        //rewards
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)){

        }

        Divider(color = Color.Gray, thickness = 2.dp)

        //tasks
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(285.dp)){

        }

        Divider(color = Color.Gray, thickness = 2.dp)

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