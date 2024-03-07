package com.example.discipline

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.datastore.data.UserStore

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(navController: NavHostController, viewModel: SharedViewModel) {

    // Zmienne
    var loginFieldState by remember { mutableStateOf(TextFieldValue("")) }
    var passwordFieldState by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val context = LocalContext.current
    val store = UserStore(context)
    val theme = store.getTheme.collectAsState(initial = "")
    viewModel.userName = store.getUsername.collectAsState(initial = "").value

    if (theme.value == "dark"){
        viewModel.backgroundColor = Color.DarkGray
        viewModel.fontColor = Color.White
        viewModel.lines = Color.White
        viewModel.todoButtonActivatedColor = Color.White
        viewModel.focusableDefaultColor = Color.LightGray
        viewModel.focusableColor = Color.White
    }

    if (theme.value == "light"){
        viewModel.backgroundColor = Color.White
        viewModel.fontColor = Color.Black
        viewModel.lines = Color.LightGray
        viewModel.todoButtonActivatedColor = Color.Black
        viewModel.focusableDefaultColor = Color.DarkGray
        viewModel.focusableColor = Color.Black
    }

    if (theme.value == "minimalist"){
        viewModel.backgroundColor = Color.Black
        viewModel.fontColor = Color.White
        viewModel.lines = Color.White
        viewModel.todoButtonActivatedColor = Color.White
        viewModel.focusableDefaultColor = Color.White
        viewModel.focusableColor = Color.White
    }

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