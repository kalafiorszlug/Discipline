package com.example.discipline.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.datastore.data.UserStore
import com.example.discipline.DisciplineScreen
import com.example.discipline.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavController, viewModel: SharedViewModel) {

    var nicknameFieldState by remember { mutableStateOf("") }
    viewModel.registering = true

    val context = LocalContext.current
    val store = UserStore(context)

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

        Spacer(modifier = Modifier.height(10.dp))

        // Przycisk do przejscia do nastepnego kroku rejestracji
        OutlinedButton(
            onClick = {
                viewModel.userName = nicknameFieldState

                CoroutineScope(Dispatchers.IO).launch {
                    store.saveUsername(viewModel.userName)
                }

                navController.navigate(route = DisciplineScreen.RegisterScreen2.name)
                      },
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