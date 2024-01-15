package com.example.discipline.ui

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
import com.example.discipline.DisciplineScreen
import com.example.discipline.SharedViewModel

@Composable
fun RegisterScreen(navController: NavController, viewModel: SharedViewModel) {

    var nicknameFieldState by remember { mutableStateOf("") }
    viewModel.registering = true

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = viewModel.backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Tekst witający
        Text(
            text = "WELCOME",
            style = MaterialTheme.typography.h1,
            color = viewModel.fontColor
        )

        // Tekst pytający
        Text(
            text = "How should we call you...?",
            style = MaterialTheme.typography.body2,
            color = viewModel.fontColor
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Pole loginu
        OutlinedTextField(

            modifier = Modifier
                .width(370.dp),

            value = nicknameFieldState,
            onValueChange = { nicknameFieldState = it },
            label = { Text(text = "Name: ") },
            shape = RoundedCornerShape(30.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = viewModel.focusableDefaultColor,
                unfocusedLabelColor = viewModel.focusableDefaultColor,
                focusedBorderColor = viewModel.focusableColor,
                focusedLabelColor = viewModel.focusableColor,
                placeholderColor = viewModel.focusableDefaultColor
            )
        )

        // Zapisywanie nicku
        viewModel.userName = nicknameFieldState

        Spacer(modifier = Modifier.height(10.dp))

        // Przycisk do przejscia do nastepnego kroku rejestracji
        OutlinedButton(
            onClick = { navController.navigate(route = DisciplineScreen.RegisterScreen2.name) },
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            border = BorderStroke(1.dp, color = viewModel.lines),
            modifier = Modifier
                .width(80.dp)
        ) {

            Text(
                text = "NEXT",
                style = MaterialTheme.typography.h1,
                color = viewModel.fontColor
            )

        }

    }

}