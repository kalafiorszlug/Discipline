package com.example.discipline

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// link do konceptu zeby nie trzeba bylo tego ciagle szukac
// https://cdn.discordapp.com/attachments/674290787705421876/1091435114409500692/koncept.png

@Composable
fun MainScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        //stats
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .align(Alignment.CenterHorizontally)
        )
        {

            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {

                    Image(
                        modifier = Modifier
                            .size(200.dp)
                            .weight(8f),
                        painter = painterResource(R.drawable.graph_icon),
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        modifier = Modifier
                            .background(
                                color = Color.Green,
                                shape = RoundedCornerShape(size = 30.dp)
                            )
                            .weight(2f),
                        text = stringResource(R.string.statistics),
                        fontSize = 15.sp,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }

        Divider(color = Color.Gray, thickness = 2.dp)

        //rewards
        Box(
            modifier = Modifier

                .fillMaxWidth()
                .height(150.dp)
        ) {

            Button(
                onClick = {/*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        modifier = Modifier
                            .weight(8f)
                    ) {
                        Image(
                            modifier = Modifier
                                .size(200.dp)
                                .weight(1f),
                            painter = painterResource(R.drawable.ig_icon),
                            contentDescription = null
                        )

                        Image(
                            modifier = Modifier
                                .size(200.dp)
                                .weight(1f),
                            painter = painterResource(R.drawable.yt_icon),
                            contentDescription = null
                        )

                        Image(
                            modifier = Modifier
                                .size(120.dp)
                                .weight(1f),
                            painter = painterResource(R.drawable.snap_icon),
                            contentDescription = null
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        modifier = Modifier
                            .background(
                                color = Color.Green,
                                shape = RoundedCornerShape(size = 30.dp)
                            )
                            .weight(2f),
                        text = "Rewards",
                        fontSize = 15.sp,
                        style = MaterialTheme.typography.body1
                    )
                }

            }
        }

        Divider(color = Color.Gray, thickness = 2.dp)

        //tasks
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(285.dp)
        ) {

            Button(
                modifier = Modifier.
                fillMaxSize(),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)) {
                Column(
                    modifier = Modifier
                    .fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .weight(8f),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center) {
                        Text(text = "- Napadańje kobjet - 20p", fontSize = 20.sp, style = MaterialTheme.typography.body1)

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(text = "- Mastórbacjowanie śe - 15p", fontSize = 20.sp, style = MaterialTheme.typography.body1)

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(text = "- Oglądańje małyh dźieći - 30p", fontSize = 20.sp, style = MaterialTheme.typography.body1)

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(text = "- Lizańje hodnika - 10p", fontSize = 20.sp, style = MaterialTheme.typography.body1)

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(text = "- Wdyhańie stup staruha (marek marucha) - 15p", fontSize = 20.sp, style = MaterialTheme.typography.body1)
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            modifier = Modifier
                                .background(color = Color.Green, shape = RoundedCornerShape(size = 30.dp)),
                            text = "Tasks",
                            fontSize = 15.sp,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }

        Divider(color = Color.Gray, thickness = 2.dp)

        //credit
        Box(
            modifier = Modifier
            .fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center){
                Text(text = "Credit: 500p", fontSize = 30.sp, style = MaterialTheme.typography.body1)
            }
        }
    }
}