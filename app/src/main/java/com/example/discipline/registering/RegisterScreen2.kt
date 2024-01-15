package com.example.discipline

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

/*
@Composable
private fun CustomTextField(
    modifier: Modifier = Modifier,
    paddingLeadingIconEnd: Dp = 0.dp,
    paddingTrailingIconStart: Dp = 0.dp,
    leadingIcon: (@Composable() () -> Unit)? = null,
    trailingIcon: (@Composable() () -> Unit)? = null
) {
    var state by remember { mutableStateOf("") } //= savedInstanceState(saver = TextFieldValue.Saver) { TextFieldValue() }

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        if (leadingIcon != null) {
            leadingIcon()
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = paddingLeadingIconEnd, end = paddingTrailingIconStart)
        ) {
            TextField(
                value = state,
                onValueChange = { state = it }
            )
            if (state.isEmpty()) {
                Text(
                    text = "Placeholder"
                )
            }
        }
        if (trailingIcon != null) {
            trailingIcon()
        }
    }
} */

@Composable
fun RegisterScreen2(navController: NavController, viewModel: SharedViewModel) {

    var loginFieldState by remember { mutableStateOf("") }
    var passwordFieldState by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = viewModel.backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Hi ${viewModel.userName}!",
            style = MaterialTheme.typography.h1,
            color = viewModel.fontColor
        )

        Text(
            text = "Please input your login information",
            style = MaterialTheme.typography.body2,
            color = viewModel.fontColor
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Pole loginu
        OutlinedTextField(

            modifier = Modifier
                .width(370.dp),

            value = loginFieldState,
            onValueChange = {loginFieldState = it },
            label = { Text(text = "Email: ") },
            shape = RoundedCornerShape(30.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = viewModel.focusableDefaultColor,
                unfocusedLabelColor = viewModel.focusableDefaultColor,
                focusedBorderColor = viewModel.focusableColor,
                focusedLabelColor = viewModel.focusableColor,
                placeholderColor = viewModel.focusableDefaultColor
            )

        )

        // Pole hasła
        OutlinedTextField(

            modifier = Modifier
                .width(370.dp),

            value = passwordFieldState,
            onValueChange = {passwordFieldState = it },
            label = { Text(text = "Password: ") },
            shape = RoundedCornerShape(30.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = viewModel.focusableDefaultColor,
                unfocusedLabelColor = viewModel.focusableDefaultColor,
                focusedBorderColor = viewModel.focusableColor,
                focusedLabelColor = viewModel.focusableColor,
                placeholderColor = viewModel.focusableDefaultColor
            )

        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = { navController.navigate(route = DisciplineScreen.InfoScreen.name) },
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            border = BorderStroke(1.dp, color = viewModel.lines),
            modifier = Modifier
                .width(100.dp)
        ) {
            Text(
                text = "SIGN UP",
                style = MaterialTheme.typography.h1,
                color = viewModel.fontColor
            )
        }
    }
}