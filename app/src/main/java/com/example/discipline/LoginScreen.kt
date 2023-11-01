package com.example.discipline

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.discipline.ui.theme.Purple500

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(navController: NavHostController) {

    var loginFieldState by remember { mutableStateOf(TextFieldValue("")) }
    var passwordFieldState by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White))
    {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Discipline",
                style = MaterialTheme.typography.h1,
                fontSize = 80.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier
                    .width(370.dp),
                value = loginFieldState,
                onValueChange = { loginFieldState = it },
                label = { Text("Email:") },
                placeholder = { Text(text = stringResource(R.string.example_email)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()}),
                shape = RoundedCornerShape(30.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray,
                    focusedBorderColor = Purple500,
                    focusedLabelColor = Purple500,
                    placeholderColor = Color.Gray
                )
            )

            OutlinedTextField(
                modifier = Modifier
                    .width(370.dp),
                value = passwordFieldState,
                onValueChange = { passwordFieldState = it },
                label = { Text("Password:") },
                placeholder = { Text(text = stringResource(R.string.example_password)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()}),
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(30.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.Gray,
                    unfocusedLabelColor = Color.Gray,
                    focusedBorderColor = Purple500,
                    focusedLabelColor = Purple500,
                    placeholderColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(25.dp))

            OutlinedButton(
                onClick = {navController.navigate(route = DisciplineScreen.MainScreen.name)},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.DarkGray,
                ),
                border = BorderStroke(1.dp, color = Color.Gray),
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .width(320.dp)
            ) {
                Text(text = stringResource(R.string.login_with_password), style = MaterialTheme.typography.body1)
            }

            // Spacer(modifier = Modifier.height(5.dp))

            OutlinedButton(
                onClick = {/*TODO*/},
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.DarkGray,
                ),
                border = BorderStroke(1.dp, color = Color.Gray),
                modifier = Modifier
                    .width(320.dp)
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

                        Text(text = stringResource(R.string.login_with_google), style = MaterialTheme.typography.body1)
                    }
                }
            }

            // Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = {navController.navigate(route = DisciplineScreen.RegisterScreen.name)},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.DarkGray,
                ),
                border = BorderStroke(1.dp, color = Color.Gray),
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .width(320.dp)
            ) {
                Text(text = stringResource(R.string.sign_up), style = MaterialTheme.typography.body1)
            }
        }
    }
}