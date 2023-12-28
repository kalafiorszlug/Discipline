package com.example.discipline

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlin.math.*

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun MainScreen(navController: NavHostController, viewModel: SharedViewModel) {

    var credit by remember { mutableStateOf(viewModel.credits) }
    var tasksTitles by remember {
        mutableStateOf(viewModel.tasksTitles)
    }
    var tasksPayoff by remember {
        mutableStateOf(viewModel.tasksPayoff)
    }

    val buttonsColors by remember {
        mutableStateOf(mutableListOf(Color.White, Color.White, Color.White, Color.White, Color.White))
    }

    val todoTextStyles by remember {
        mutableStateOf(mutableListOf(TextStyle(color = Color.Black), TextStyle(color = Color.Black), TextStyle(color = Color.Black), TextStyle(color = Color.Black), TextStyle(color = Color.Black)))
    }

    val buttonsClicked by remember {
        mutableStateOf(mutableListOf(false, false, false, false, false))
    }

    var save = viewModel.tasksThroughoutTheWeek[viewModel.dayOfTheWeek - 1]

    val creditCounter by animateIntAsState(
        targetValue = credit,
        animationSpec = tween(
            durationMillis = 700,
            easing = FastOutSlowInEasing
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // STATYSTYKI
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(150f)
                .align(Alignment.CenterHorizontally)
                .padding(
                    start = 15.dp,
                    top = 7.dp,
                    end = 15.dp,
                    bottom = 7.dp
                )
        ) {

            OutlinedButton(
                onClick = { navController.navigate(route = DisciplineScreen.StatScreen.name) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(28.dp))
                    .padding(3.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(8f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        modifier = Modifier
                            .size(170.dp),
                        painter = painterResource(R.drawable.graph_icon),
                        contentDescription = null
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .background(
                                    color = colorResource(R.color.light_green),
                                    shape = RoundedCornerShape(size = 32.dp)
                                ),
                            text = stringResource(R.string.mainscreen_statistics),
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.h1,
                            color = Color.Black
                        )
                    }
                }
            }
        }

        // NAGRODY
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(150f)
                .padding(
                    start = 15.dp,
                    top = 7.dp,
                    end = 15.dp,
                    bottom = 7.dp
                )
        ) {

            OutlinedButton(
                onClick = { navController.navigate(route = DisciplineScreen.RewardScreen.name) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(28.dp))
                    .padding(3.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(35.dp))

                    Row(
                        modifier = Modifier
                            .weight(7f)
                    ) {

                        Image(
                            modifier = Modifier
                                .size(90.dp)
                                .weight(1f),
                            painter = painterResource(R.drawable.ig_icon),
                            contentDescription = null
                        )

                        Image(
                            modifier = Modifier
                                .size(90.dp)
                                .weight(1f),
                            painter = painterResource(R.drawable.yt_icon),
                            contentDescription = null
                        )

                        Image(
                            modifier = Modifier
                                .size(90.dp)
                                .weight(1f),
                            painter = painterResource(R.drawable.snap_icon),
                            contentDescription = null
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Column(modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .background(
                                    color = colorResource(R.color.light_green),
                                    shape = RoundedCornerShape(size = 30.dp)
                                ),
                            text = stringResource(R.string.mainscreen_rewards),
                            fontSize = 16.sp,
                            style = MaterialTheme.typography.h1,
                            color = Color.Black
                        )
                    }
                }
            }
        }

        // ZADANIA
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(270f)
                .padding(
                    start = 15.dp,
                    top = 7.dp,
                    end = 15.dp,
                    bottom = 7.dp
                )
        ) {

            OutlinedButton(
                onClick = { navController.navigate(route = DisciplineScreen.TaskScreen.name) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(28.dp))
                    .padding(3.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    Column(
                        modifier = Modifier
                            .weight(8f),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))

                        if (viewModel.tasksTitles.size >= 1){
                            repeat(min(tasksTitles.size, 5)){
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(25.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    OutlinedButton(
                                        modifier = Modifier
                                            .border(1.dp, color = Color.Black, shape = CircleShape)
                                            .size(15.dp),
                                        colors = ButtonDefaults.buttonColors(backgroundColor = buttonsColors[it]),
                                        shape = CircleShape,
                                        onClick = {
                                            viewModel.credits += tasksPayoff[it]
                                            credit = viewModel.credits
                                            buttonsColors[it] = Color.Black

                                            viewModel.tasksTitles.removeAt(it)
                                            tasksTitles = viewModel.tasksTitles

                                            viewModel.tasksDeadlines.removeAt(it)

                                            viewModel.creditsAllTime += tasksPayoff[it]

                                            viewModel.tasksPayoff.removeAt(it)
                                            tasksPayoff = viewModel.tasksPayoff

                                            viewModel.completedTasks += 1

                                            viewModel.tasksThroughoutTheWeek[viewModel.dayOfTheWeek - 1] = save + 1

                                            buttonsClicked[it] = true
                                        }
                                    ) {}

                                    if (buttonsClicked[it]) {
                                        todoTextStyles[it] = LocalTextStyle.current.copy(textDecoration = TextDecoration.LineThrough)
                                        buttonsClicked[it] = false
                                    } else{
                                        buttonsColors[it] = Color.White
                                        todoTextStyles[it] = LocalTextStyle.current.copy(textDecoration = TextDecoration.None)
                                    }

                                    Spacer(modifier = Modifier.width(20.dp))

                                    AnimatedContent(
                                        targetState = tasksTitles[it],
                                        transitionSpec = {
                                            slideInVertically { height -> height } + fadeIn() with
                                                    slideOutVertically { height -> -height } + fadeOut()
                                        }
                                    ){targetContent->
                                        Text(
                                            text = targetContent,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Normal,
                                            style = todoTextStyles[it],
                                            color = Color.Black
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(20.dp))

                                    AnimatedContent(
                                        targetState = "${tasksPayoff[it]}p",
                                        transitionSpec = {
                                            slideInVertically { height -> height } + fadeIn() with
                                                    slideOutVertically { height -> -height } + fadeOut()
                                        }
                                    ){targetContent->
                                        Text(
                                            text = targetContent,
                                            fontSize = 20.sp,
                                            style = MaterialTheme.typography.body2,
                                            color = Color.Black
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(20.dp))

                                }
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        } else {
                            AnimatedContent(
                                targetState = "All planned tasks done!",
                                transitionSpec = {
                                    slideInHorizontally { width -> width } + fadeIn() with
                                            slideOutHorizontally { width -> -width } + fadeOut()
                                }
                            ){targetContent->
                                Box(modifier =
                                Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center){
                                    Text(
                                        text = targetContent,
                                        fontSize = 35.sp,
                                        style = MaterialTheme.typography.h1,
                                        color = Color.Black
                                    )
                                }
                            }
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
                            style = MaterialTheme.typography.h1,
                            color = Color.Black
                        )
                    }
                }
            }
        }

        Divider(color = Color.Gray, thickness = 2.dp)

        // KREDYTY
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
            ) {

                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    Text(
                        text = "Credit: $creditCounter",
                        fontSize = 30.sp,
                        style = MaterialTheme.typography.body1,
                        color = Color.Black
                    )
                }
            }
        }
    }
}