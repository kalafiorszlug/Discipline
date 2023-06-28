package com.example.discipline

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// link do konceptu zeby nie trzeba bylo tego ciagle szukac
// https://cdn.discordapp.com/attachments/674290787705421876/1091435114409500692/koncept.png

@Composable
fun MainScreen(){

    Column(
        modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally)
    {

        //stats
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .align(Alignment.CenterHorizontally))
        {

            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
            ) {

                Column(
                    modifier = Modifier
                    .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally)
                {

                    Image(modifier = Modifier
                        .size(200.dp)
                        .weight(8f),
                        painter = painterResource(R.drawable.graph_icon),
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(modifier = Modifier.weight(2f), text = "Check statistics", fontSize = 15.sp)
                }
            }
        }

        Divider(color = Color.Gray, thickness = 2.dp)

        //rewards
        Box(modifier = Modifier

            .fillMaxWidth()
            .height(150.dp)){

        }

        Divider(color = Color.Gray, thickness = 2.dp)

        //tasks
        Box(modifier = Modifier

            .fillMaxWidth()
            .height(285.dp)){

        }

        Divider(color = Color.Gray, thickness = 2.dp)

        //score
        Box(){

        }
    }
}