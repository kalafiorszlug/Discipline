package com.example.discipline

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

// link do konceptu zeby nie trzeba bylo tego ciagle szukac
// https://cdn.discordapp.com/attachments/674290787705421876/1091435114409500692/koncept.png=

@Composable
fun MainScreen(navController: NavHostController) {
    var credit by remember { mutableStateOf(0) }
    var buttonColor1 by remember { mutableStateOf(Color.White) }
    var buttonColor2 by remember { mutableStateOf(Color.White) }
    var buttonColor3 by remember { mutableStateOf(Color.White) }
    var buttonColor4 by remember { mutableStateOf(Color.White) }
    var buttonColor5 by remember { mutableStateOf(Color.White) }
    var isClicked1 by remember { mutableStateOf(false) }
    var todoTextStyle1 by remember { mutableStateOf(TextStyle(color = Color.Black)) }
    var isClicked2 by remember { mutableStateOf(false) }
    var todoTextStyle2 by remember { mutableStateOf(TextStyle(color = Color.Black)) }
    var isClicked3 by remember { mutableStateOf(false) }
    var todoTextStyle3 by remember { mutableStateOf(TextStyle(color = Color.Black)) }
    var isClicked4 by remember { mutableStateOf(false) }
    var todoTextStyle4 by remember { mutableStateOf(TextStyle(color = Color.Black)) }
    var isClicked5 by remember { mutableStateOf(false) }
    var todoTextStyle5 by remember { mutableStateOf(TextStyle(color = Color.Black)) }

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
                .weight(150f)
                .align(Alignment.CenterHorizontally)
        )
        {

            Button(
                onClick = { navController.navigate(route = DisciplineScreen.StatScreen.name) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
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
                                color = colorResource(R.color.light_green),
                                shape = RoundedCornerShape(size = 32.dp)
                            )
                            .weight(1.5f),
                        text = stringResource(R.string.mainscreen_statistics),
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.h1
                    )
                }
            }
        }

        Divider(color = Color.Gray, thickness = 2.dp)

        //rewards
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(150f)
        ) {

            Button(
                onClick = { navController.navigate(route = DisciplineScreen.RewardScreen.name) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
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
                                .size(75.dp)
                                .weight(1f),
                            painter = painterResource(R.drawable.ig_icon),
                            contentDescription = null
                        )

                        Image(
                            modifier = Modifier
                                .size(75.dp)
                                .weight(1f),
                            painter = painterResource(R.drawable.yt_icon),
                            contentDescription = null
                        )

                        Image(
                            modifier = Modifier
                                .size(75.dp)
                                .weight(1f),
                            painter = painterResource(R.drawable.snap_icon),
                            contentDescription = null
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        modifier = Modifier
                            .background(
                                color = colorResource(R.color.light_green),
                                shape = RoundedCornerShape(size = 30.dp)
                            )
                            .weight(2f),
                        text = stringResource(R.string.mainscreen_rewards),
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.h1
                    )
                }

            }
        }

        Divider(color = Color.Gray, thickness = 2.dp)

        //tasks
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(270f)
        ) {

            Button(
                modifier = Modifier.fillMaxSize(),
                onClick = { navController.navigate(route = DisciplineScreen.TaskScreen.name) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .weight(8f),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                modifier = Modifier.size(15.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor1),
                                shape = CircleShape,
                                onClick = {
                                    credit += 15
                                    isClicked1 = true
                                }
                            ) {
                            }

                            if (isClicked1) {
                                buttonColor1 = Color.Black
                                todoTextStyle1 =
                                    LocalTextStyle.current.copy(textDecoration = TextDecoration.LineThrough)
                            }

                            Spacer(modifier = Modifier.width(20.dp))

                            Text(
                                text = "Walking a dog",
                                fontSize = 20.sp,
                                style = todoTextStyle1
                            )

                            Spacer(modifier = Modifier.width(20.dp))

                            Text(
                                text = "15p",
                                fontSize = 20.sp,
                                style = MaterialTheme.typography.body1
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                modifier = Modifier.size(15.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor2),
                                shape = CircleShape,
                                onClick = {
                                    credit += 30
                                    isClicked2 = true
                                }
                            ) {

                            }

                            if (isClicked2) {
                                buttonColor2 = Color.Black
                                todoTextStyle2 =
                                    LocalTextStyle.current.copy(textDecoration = TextDecoration.LineThrough)
                            }

                            Spacer(modifier = Modifier.width(20.dp))

                            Text(
                                text = "Working out",
                                fontSize = 20.sp,
                                style = todoTextStyle2
                            )

                            Spacer(modifier = Modifier.width(20.dp))

                            Text(
                                text = "30p",
                                fontSize = 20.sp,
                                style = MaterialTheme.typography.body1
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                modifier = Modifier.size(15.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor3),
                                shape = CircleShape,
                                onClick = {
                                    credit += 20
                                    isClicked3 = true
                                }
                            ) {

                            }

                            if (isClicked3) {
                                buttonColor3 = Color.Black
                                todoTextStyle3 =
                                    LocalTextStyle.current.copy(textDecoration = TextDecoration.LineThrough)
                            }

                            Spacer(modifier = Modifier.width(20.dp))

                            Text(
                                text = "Tidying livingroom",
                                fontSize = 20.sp,
                                style = todoTextStyle3
                            )

                            Spacer(modifier = Modifier.width(20.dp))

                            Text(
                                text = "20p",
                                fontSize = 20.sp,
                                style = MaterialTheme.typography.body1
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                modifier = Modifier
                                    .size(15.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor4),
                                shape = CircleShape,
                                onClick = {
                                    credit += 15
                                    isClicked4 = true
                                }
                            ) {

                            }

                            if (isClicked4) {
                                buttonColor4 = Color.Black
                                todoTextStyle4 =
                                    LocalTextStyle.current.copy(textDecoration = TextDecoration.LineThrough)
                            }

                            Spacer(modifier = Modifier.width(20.dp))

                            Text(
                                text = "Eating a healthy dinner",
                                fontSize = 20.sp,
                                style = todoTextStyle4
                            )

                            Spacer(modifier = Modifier.width(20.dp))

                            Text(
                                text = "15p",
                                fontSize = 20.sp,
                                style = MaterialTheme.typography.body1
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedButton(
                                modifier = Modifier.size(15.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor5),
                                shape = CircleShape,
                                onClick = {
                                    credit += 15
                                    isClicked5 = true
                                }
                            ) {
                            }

                            if (isClicked5) {
                                buttonColor5 = Color.Black
                                todoTextStyle5 =
                                    LocalTextStyle.current.copy(textDecoration = TextDecoration.LineThrough)
                            }

                            Spacer(modifier = Modifier.width(20.dp))

                            Text(
                                text = "Consuming pornography",
                                fontSize = 20.sp,
                                style = todoTextStyle5
                            )

                            Spacer(modifier = Modifier.width(20.dp))

                            Text(
                                text = "15p",
                                fontSize = 20.sp,
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .background(
                                    color = colorResource(R.color.light_green),
                                    shape = RoundedCornerShape(size = 30.dp)
                                ),
                            text = stringResource(R.string.mainscreen_tasks),
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.h1
                        )
                    }
                }
            }
                }

        Divider(color = Color.Gray, thickness = 2.dp)

        //credit
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(50f)
                .background(color = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
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