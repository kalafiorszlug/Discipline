package com.example.discipline

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun InfoScreen(navController: NavController){
    Column(modifier = Modifier
        .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = "OUR APP",
            style = MaterialTheme.typography.h1
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(35.dp),
            text = stringResource(R.string.app_info),
            style = MaterialTheme.typography.body2,
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = { navController.navigate(route = DisciplineScreen.LoginScreen.name) },
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .width(80.dp)
        ) {

            Text(
                text = "COOL",
                style = MaterialTheme.typography.h1
            )

        }
    }
}