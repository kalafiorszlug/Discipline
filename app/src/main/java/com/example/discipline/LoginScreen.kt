package com.example.discipline

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(navController: NavHostController, viewModel: SharedViewModel) {

    // Zmienne
    var loginFieldState by remember { mutableStateOf(TextFieldValue("")) }
    var passwordFieldState by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Kontener całego ekranu
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = viewModel.backgroundColor))
    {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End)
        {

            Icon(
                Icons.Rounded.Info,
                contentDescription = null,
                modifier = Modifier
                    .clickable{navController.navigate(route = DisciplineScreen.InfoScreen.name)}
                    .padding(10.dp)
            )

        }


        // Kolumna trzymająca logo i wszystkie przyciski oraz pola tekstowe
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // TODO * Miejsce na logo
            Text(
                text = "Discipline",
                style = MaterialTheme.typography.h1,
                color = viewModel.fontColor,
                fontSize = 80.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Pole tekstowe na maila
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
                    unfocusedBorderColor = viewModel.focusableDefaultColor,
                    unfocusedLabelColor = viewModel.focusableDefaultColor,
                    focusedBorderColor = viewModel.focusableColor,
                    focusedLabelColor = viewModel.focusableColor,
                    placeholderColor = viewModel.focusableDefaultColor
                )
            )

            // Pole na hasło
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
                    unfocusedBorderColor = viewModel.focusableDefaultColor,
                    unfocusedLabelColor = viewModel.focusableDefaultColor,
                    focusedBorderColor = viewModel.focusableColor,
                    focusedLabelColor = viewModel.focusableColor,
                    placeholderColor = viewModel.focusableDefaultColor
                )
            )

            Spacer(modifier = Modifier.height(25.dp))

            // Przycisk logowania
            OutlinedButton(
                modifier = Modifier
                    .width(320.dp),

                onClick = {
                    navController.navigate(route = DisciplineScreen.MainScreen.name)
                          },

                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),

                border = BorderStroke(1.dp, color = viewModel.lines),
                shape = RoundedCornerShape(28.dp),

            ) {
                Text(text = stringResource(R.string.login_with_password), style = MaterialTheme.typography.body1, color = viewModel.fontColor)
            }

            // Spacer(modifier = Modifier.height(5.dp))

            // Przycisk logowania przy użyciu konta google
            OutlinedButton(
                onClick = {/*TODO*/},
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                border = BorderStroke(1.dp, color = viewModel.lines),
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

                        Text(text = stringResource(R.string.login_with_google), style = MaterialTheme.typography.body1, color = viewModel.fontColor)
                    }
                }
            }

            // Spacer(modifier = Modifier.height(10.dp))

            // Przycisk rejestracji
            OutlinedButton(
                onClick = {navController.navigate(route = DisciplineScreen.RegisterScreen.name)},
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                border = BorderStroke(1.dp, color = viewModel.lines),
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .width(320.dp)
            ) {
                Text(text = stringResource(R.string.sign_up), style = MaterialTheme.typography.body1, color = viewModel.fontColor)
            }
        }
    }
}