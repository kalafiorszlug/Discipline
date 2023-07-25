package com.example.discipline

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import com.example.discipline.ui.theme.Purple500

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RewardScreen(navController: NavHostController, viewModel: SharedViewModel) {

    var credit by remember { mutableStateOf(viewModel.credit) }
    var rewardCreating by remember { mutableStateOf(false) }
    var appOrWebsiteBlocking by remember { mutableStateOf(false) }
    var rewardsTitleFieldState by remember { mutableStateOf("") }
    var rewardsPriceFieldState by remember { mutableStateOf("") }
    var rewardsDurationFieldState by remember { mutableStateOf("") }
    var blurRadius by remember { mutableStateOf(0) }
    val images = listOf(R.drawable.yt_icon, R.drawable.ig_icon, R.drawable.snap_icon, R.drawable.twitter_icon, R.drawable.tiktok_icon)
    val titles = listOf("watching YouTube", "Instagram scrolling", "unlocking Snapchat", "unlocking Twitter", "watching TikToks like a retard")
    val prices = listOf(150, 115, 120, 100, 200)
    val numberOfRewards by remember { mutableStateOf(titles.size) }

    var popupFinalOffset by remember { mutableStateOf(2000) }

    val popupOffset by animateIntAsState(
        targetValue = popupFinalOffset,
        animationSpec = tween(
            durationMillis = 100,
            easing = FastOutSlowInEasing
        )
    )

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
                .blur(blurRadius.dp)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                repeat(numberOfRewards){
                    OutlinedButton(
                        onClick = {
                            viewModel.credit -= prices[it]
                            credit = viewModel.credit
                                  },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                        shape = RoundedCornerShape(28.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(145.dp)
                            .padding(3.dp)
                            .border(1.dp, color = Gray, shape = RoundedCornerShape(28.dp))
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

                            Text(text = titles[it], style = MaterialTheme.typography.body1, color = Black)

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(modifier = Modifier
                                .background(
                                    color = colorResource(R.color.light_green),
                                    shape = RoundedCornerShape(size = 30.dp)),
                                    text = "ㅤprice: ${prices[it]}pㅤ", style = MaterialTheme.typography.h1, color = Black)
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White))
        {
            OutlinedButton(
                onClick = {
                    rewardCreating = true
                    blurRadius = 15
                          },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .padding(2.dp)
                    .align(Alignment.Center)
            ) {
                Text(text = "+ Add a reward", style = MaterialTheme.typography.h1, color = Black)
            }
        }

        if (rewardCreating){

            popupFinalOffset = 0

            Popup(
                offset = IntOffset(0, popupOffset),
                alignment = Alignment.Center,
                properties = PopupProperties(focusable = true)
            ) {
                Box(
                    modifier = Modifier
                        .background(color = Color.White)
                        .size(400.dp)
                        .border(width = 2.dp, color = Gray, shape = RoundedCornerShape(16.dp)),
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        OutlinedTextField(
                            modifier = Modifier
                                .width(370.dp)
                                .padding(3.dp),
                            value = rewardsTitleFieldState,
                            onValueChange = { rewardsTitleFieldState = it },
                            label = { Text("How will you reward yourself?") },
                            placeholder = { Text("Enter reward's title.") },
                            shape = RoundedCornerShape(30.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = Gray,
                                unfocusedLabelColor = Gray,
                                focusedBorderColor = Purple500,
                                focusedLabelColor = Purple500,
                                placeholderColor = Gray
                            )
                        )

                        OutlinedTextField(
                            modifier = Modifier
                                .width(370.dp)
                                .padding(3.dp),
                            value = rewardsPriceFieldState,
                            onValueChange = { rewardsPriceFieldState = it },
                            label = { Text("What will be the cost?") },
                            placeholder = { Text("Enter a value.") },
                            shape = RoundedCornerShape(30.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = Gray,
                                unfocusedLabelColor = Gray,
                                focusedBorderColor = Purple500,
                                focusedLabelColor = Purple500,
                                placeholderColor = Gray
                            )
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = "*optional*",
                            fontSize = 10.sp,
                            style = MaterialTheme.typography.h1,
                            color = Black
                        )

                        OutlinedButton(
                            onClick = { appOrWebsiteBlocking = true },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                            shape = RoundedCornerShape(28.dp),
                        ) {
                            Text(
                                "Chose an app/website to be the reward",
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.body1,
                                color = Black
                            )
                        }

                        if (appOrWebsiteBlocking) {
                            Spacer(modifier = Modifier.height(3.dp))

                            OutlinedTextField(
                                modifier = Modifier
                                    .width(200.dp)
                                    .padding(3.dp),
                                value = rewardsDurationFieldState,
                                onValueChange = { rewardsDurationFieldState = it },
                                label = { Text("Time the app/website will be unlocked for:") },
                                placeholder = { Text("Enter duration in hours.") },
                                shape = RoundedCornerShape(30.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    unfocusedBorderColor = Gray,
                                    unfocusedLabelColor = Gray,
                                    focusedBorderColor = Purple500,
                                    focusedLabelColor = Purple500,
                                    placeholderColor = Gray
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(3.dp))

                        OutlinedButton(
                            onClick = {
                                popupFinalOffset = 1500
                                rewardCreating = false
                                blurRadius = 0
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = colorResource(
                                    R.color.light_green
                                )
                            ),
                            shape = RoundedCornerShape(28.dp),
                        ) {
                            Text(
                                "Done!",
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.h1,
                                color = Black
                            )
                        }

                    }
                }
            }
        }

        Divider(color = Gray, thickness = 2.dp)

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
                Row {
                    Text(
                        text = "Credit: ",
                        fontSize = 30.sp,
                        style = MaterialTheme.typography.body1,
                        color = Black
                    )

                    Text(
                        text = credit.toString(),
                        fontSize = 30.sp,
                        style = MaterialTheme.typography.body1,
                        color = Black
                    )
                }
            }
        }
    }
}

