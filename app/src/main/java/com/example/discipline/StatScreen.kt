package com.example.discipline

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun StatScreen(navController: NavController, viewModel: SharedViewModel) {

    var streakExplanation by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "Statistics", fontSize = 30.sp, style = MaterialTheme.typography.h1)
        
        Spacer(modifier = Modifier.height(50.dp))

        TextButton(
            onClick = {
                streakExplanation = !streakExplanation
            },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Transparent,
                contentColor = Color.Black
            ),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .width(175.dp)
                .height(38.dp)
        ){
            Text(
                text = "Current streak: ${viewModel.streak}",
                fontSize = 20.sp,
                style = MaterialTheme.typography.body1
            )
        }

        AnimatedVisibility(
            visible = streakExplanation
        ) {
            Text(text = "Number of days in a row which you have completed tasks in", fontSize = 14.sp, style = MaterialTheme.typography.h3)
        }

        Text(text = "Total credits collected: ${viewModel.creditsAllTime}", fontSize = 20.sp, style = MaterialTheme.typography.body1)
        Text(text = "Total tasks completed: ${viewModel.completedTasks}", fontSize = 20.sp, style = MaterialTheme.typography.body1)

        Text(text = "Best all time streak : ${viewModel.bestStreak} days", fontSize = 16.sp, style = MaterialTheme.typography.body2)
        Text(text = "Total credits spent: ${viewModel.creditsSpentAllTime}", fontSize = 16.sp, style = MaterialTheme.typography.body2)

    }
}

