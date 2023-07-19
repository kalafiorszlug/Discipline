package com.example.discipline

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RewardScreen() {
    var credit by remember { mutableStateOf(0) }
    var numberOfRewards by remember { mutableStateOf(5) }
    val images = listOf(R.drawable.yt_icon, R.drawable.ig_icon, R.drawable.snap_icon, R.drawable.twitter_icon, R.drawable.tiktok_icon)
    val titles = listOf("watching YouTube", "Instagram scrolling", "unlocking Snapchat", "unlocking Twitter", "watching TikToks like a retard")
    val prices = listOf(150, 115, 120, 100, 200)

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(9f)
                .background(color = Color.White)
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                repeat(numberOfRewards){
                    OutlinedButton(
                        onClick = {/*TODO*/ },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                        shape = RoundedCornerShape(28.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(145.dp)
                            .padding(3.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(75.dp)
                                    .weight(1f),
                                painter = painterResource(images[it]),
                                contentDescription = null
                            )

                            Text(text = titles[it], style = MaterialTheme.typography.body1)
                            
                            Spacer(modifier = Modifier.height(2.dp))

                            Text(modifier = Modifier
                                .background(
                                    color = colorResource(R.color.light_green),
                                    shape = RoundedCornerShape(size = 30.dp)),
                                    text = "ㅤprice: ${prices[it]}pㅤ", style = MaterialTheme.typography.h1)
                        }
                    }
                }
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)){
            OutlinedButton(
                onClick = {/*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .padding(2.dp)
                    .align(Alignment.Center)
            ) {
                Text(text = "+ Add a reward", style = MaterialTheme.typography.h1)
            }
        }

        Divider(color = Color.Gray, thickness = 2.dp)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                Row() {
                    Text(
                        text = "Credit: ",
                        fontSize = 30.sp,
                        style = MaterialTheme.typography.body1
                    )

                    Text(
                        text = credit.toString(),
                        fontSize = 30.sp,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}

