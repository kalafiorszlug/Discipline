package com.example.discipline.registering

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.discipline.DisciplineScreen
import com.example.discipline.SharedViewModel

@Composable
fun AppInfo(navController: NavController, viewModel: SharedViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Lorem Ipsum is simply dummy text of the printing and" +
                    " typesetting industry. Lorem Ipsum has been the industry's" +
                    " standard dummy text ever since the 1500s, when an unknown" +
                    " printer took a galley of type and scrambled it to make a type" +
                    " specimen book. It has survived not only five centuries, but also" +
                    " the leap into electronic typesetting, remaining essentially" +
                    " unchanged. It was popularised in the 1960s with the release of" +
                    " Letraset sheets containing Lorem Ipsum passages, and more recently" +
                    " with desktop publishing software like Aldus PageMaker including" +
                    " versions of Lorem Ipsum.",
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .width(300.dp),

            )

        Button(
            onClick = { navController.navigate(route = DisciplineScreen.Config1.name) },
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