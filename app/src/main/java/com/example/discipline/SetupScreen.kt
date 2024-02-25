package com.example.discipline

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.datastore.data.UserStore


@Composable
fun SetupScreen(navController: NavHostController, viewModel: SharedViewModel) {
    val context = LocalContext.current
    val store = UserStore(context)
    val tokenText = store.getAccessToken.collectAsState(initial = "")
    var setupDone = false

    if (tokenText.value == "dark"){
        viewModel.backgroundColor = Color.DarkGray
        viewModel.fontColor = Color.White
        viewModel.lines = Color.White
        viewModel.todoButtonActivatedColor = Color.White
        viewModel.focusableDefaultColor = Color.LightGray
        viewModel.focusableColor = Color.White
        setupDone = true
    }

    if (tokenText.value == "light"){
        viewModel.backgroundColor = Color.White
        viewModel.fontColor = Color.Black
        viewModel.lines = Color.LightGray
        viewModel.todoButtonActivatedColor = Color.Black
        viewModel.focusableDefaultColor = Color.DarkGray
        viewModel.focusableColor = Color.Black
        setupDone = true
    }

    if (tokenText.value == "minimalist"){
        viewModel.backgroundColor = Color.Black
        viewModel.fontColor = Color.White
        viewModel.lines = Color.White
        viewModel.todoButtonActivatedColor = Color.White
        viewModel.focusableDefaultColor = Color.White
        viewModel.focusableColor = Color.White
        setupDone = true
    }

    Column {
        Button(onClick = { navController.navigate(route = DisciplineScreen.LoginScreen.name) }) {
            Text("kljasdf")
        }
    }
}