package com.example.discipline.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.discipline.DisciplineScreen
import com.example.discipline.SharedViewModel

@Composable
fun RegisterScreen(navController: NavController, viewModel: SharedViewModel) {

    var nicknameFieldState by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Tekst witający
        Text(
            text = "WELCOME",
            style = MaterialTheme.typography.h1
        )

        // Tekst pytający
        Text(
            text = "How should we call you...?",
            style = MaterialTheme.typography.body2,
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

        )

        // Zapisywanie nicku
        viewModel.userName = nicknameFieldState

        Spacer(modifier = Modifier.height(10.dp))

        // Przycisk do przejscia do nastepnego kroku rejestracji
        Button(
            onClick = { navController.navigate(route = DisciplineScreen.RegisterScreen2.name) },
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .width(80.dp)
        ) {

            Text(
                text = "NEXT",
                style = MaterialTheme.typography.h1
            )

        }

    }

}