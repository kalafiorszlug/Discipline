package com.example.discipline.registering.configuration

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.discipline.SharedViewModel

@Composable
fun ConfigurationScreen1(navController: NavController, viewModel: SharedViewModel) {

    var firstButtonSelected = 0
    var secondButtonSelected = 0
    var thirdButtonSelected = 0

    var firstButtonBgColor = Color.Black
    var secondButtonBgColor = Color.Black
    var thirdButtonBgColor = Color.Black

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .border(
                    border = BorderStroke(2.dp, color = Color.White),
                    shape = RoundedCornerShape(15.dp)
                )
                .height(210.dp)
                .width(300.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Pierwsze pole wyboru
            Button(
                onClick = {
                          if (firstButtonSelected == 0) {
                              firstButtonSelected = 1
                              firstButtonBgColor = Color.Gray
                          } else {
                              firstButtonSelected = 0
                              firstButtonBgColor = Color.Black
                          }

                          },
                modifier = Modifier
                    .height(70.dp)
                    .width(300.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = firstButtonBgColor)
            ) {
                Text(text = "Element listy nr 1")
            }

            // Drugie pole wyboru
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .height(70.dp)
                    .width(300.dp)
            ) {
                Text(text = "Element listy nr 2")
            }

            // Trzecie pole wyboru
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .height(70.dp)
                    .width(300.dp)
            ) {
                Text(text = "Element listy nr 3")
            }

        }

    }

}