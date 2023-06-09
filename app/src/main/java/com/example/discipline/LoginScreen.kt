package com.example.discipline

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(navController: NavHostController) {

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
            placeholder = { Text(text = stringResource(R.string.example_email)) }
        )

        TextField(
            modifier = Modifier
                .width(370.dp),
            value = passwordFieldState,
            onValueChange = { passwordFieldState = it },
            label = { Text("Password:") },
            placeholder = { Text(text = stringResource(R.string.example_password)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {navController.navigate(route = DisciplineScreen.MainScreen.name)},
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .width(350.dp)
        ) {
            Text(text = stringResource(com.example.discipline.R.string.login_with_password), style = MaterialTheme.typography.body1)
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

                    Text(text = stringResource(com.example.discipline.R.string.login_with_google), style = MaterialTheme.typography.body1)
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
            Text(text = stringResource(com.example.discipline.R.string.sign_up), style = MaterialTheme.typography.body1)
        }
    }
}