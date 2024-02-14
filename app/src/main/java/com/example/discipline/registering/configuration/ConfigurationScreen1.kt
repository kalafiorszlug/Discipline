package com.example.discipline.registering.configuration

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.discipline.SharedViewModel

@Composable
fun ConfigurationScreen1(navController: NavController, viewModel: SharedViewModel) {

    //val items = listOf("pierwszy tekst", "drugi tekst", "trzeci tekst")
    //var selectedItem by remember{ mutableStateOf(" ")}

    viewModel.registering = false

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



        }

    }

}