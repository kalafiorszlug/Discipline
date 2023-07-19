package com.example.discipline

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun RegisterScreen2(navController: NavController) {

    var loginFieldState by remember { mutableStateOf("") }
    var passwordFieldState by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Hi ",
            style = MaterialTheme.typography.h1
        )

        Text(
            text = "Please input your login information",
            style = MaterialTheme.typography.body2
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

        )

        // Pole hasła
        OutlinedTextField(

            modifier = Modifier
                .width(370.dp),

            value = passwordFieldState,
            onValueChange = {passwordFieldState = it },
            label = { Text(text = "Password: ") },
            shape = RoundedCornerShape(30.dp),

        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { /* TODO */ },
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .width(100.dp)
        ) {
            Text(
                text = "SIGN UP",
                style = MaterialTheme.typography.h1
            )
        }
    }
}