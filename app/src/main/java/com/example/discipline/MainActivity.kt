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
fun LoginScreen() {

    var loginFieldState by remember { mutableStateOf("") }
    var passwordFieldState by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.simple_logo),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            modifier = Modifier
                .width(370.dp),
            value = loginFieldState,
            onValueChange = { loginFieldState = it },
            label = { Text("Email:") },
            placeholder = { Text(text = "SamiecSigmaProgress123@hustler.cum") }
        )

        TextField(
            modifier = Modifier
                .width(370.dp),
            value = passwordFieldState,
            onValueChange = { passwordFieldState = it },
            label = { Text("Password:") },
            placeholder = { Text(text = "DailyGrind6969") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {/*TODO*/ },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .width(350.dp)
        ) {
            Text(text = stringResource(com.example.discipline.R.string.login_with_psswd))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {/*TODO*/},
            shape = RoundedCornerShape(40.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            modifier = Modifier
                .width(350.dp)
        ) {
            Box(){
                Row() {
                    Column(
                        modifier = Modifier
                            .height(20.dp),
                        verticalArrangement = Arrangement.Center){
                        Image(
                            painter = painterResource(R.drawable.google_icon),
                            contentDescription = null
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(text = stringResource(com.example.discipline.R.string.login_with_google))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {/*TODO*/},
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .width(350.dp)
        ) {
            Text(text = stringResource(com.example.discipline.R.string.sign_up))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  Surface(color = Color.LightGray) {
      DisciplineTheme() {
          LoginScreen()
      }
  }
}