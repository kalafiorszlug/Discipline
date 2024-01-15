package com.example.discipline

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun InfoScreen(navController: NavController, viewModel: SharedViewModel){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = viewModel.backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = "OUR APP",
            style = MaterialTheme.typography.h1,
            color = viewModel.fontColor
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(35.dp),
            text = stringResource(R.string.app_info),
            style = MaterialTheme.typography.body2,
            color = viewModel.fontColor
        )

        Spacer(modifier = Modifier.height(15.dp))

        OutlinedButton(
            onClick = { navController.navigate(
                if (viewModel.registering){
                    DisciplineScreen.Config1.name
                } else {
                    DisciplineScreen.LoginScreen.name
                }
            ) },
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
            border = BorderStroke(1.dp, color = viewModel.lines),
            modifier = Modifier
                .width(100.dp)
        ) {
            Text(
                text = "COOL",
                style = MaterialTheme.typography.h1,
                color = viewModel.fontColor
            )
        }
    }
}