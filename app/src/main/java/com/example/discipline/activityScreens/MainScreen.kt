package com.example.discipline

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
        mutableStateOf(mutableListOf(Color.Transparent, Color.Transparent, Color.Transparent, Color.Transparent, Color.Transparent))
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
            .background(color = viewModel.backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // KREDYTY, USTAWIENIA
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(35f)
                .background(color = Color.Transparent),
            contentAlignment = Alignment.CenterStart,
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            // TODO Dlaczego tu jest kolumna??
            // Wszystkie inne elementy sa osobno gdzie jeden jest w rzedzie a drugi tez w kolumnie
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {

                Icon(
                    Icons.Rounded.Settings,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(onClick = {navController.navigate(route = DisciplineScreen.SettingsScreen.name)})
                        .size(50.dp)
                        .padding(12.dp)
                )

            }

            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){

                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "${viewModel.userName}'s credit: $creditCounter",
                        fontSize = 22.sp,
                        letterSpacing = 0.sp,
                        style = MaterialTheme.typography.body1,
                        color = viewModel.fontColor
                    )
                }
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){

                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {

                    Icon(
                        Icons.Rounded.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {  }
                            .size(60.dp)
                            .padding(10.dp)
                    )

                }
            }
        }

        Divider(color = viewModel.lines, thickness = 0.dp, modifier = Modifier.shadow(10.dp))

        // STATYSTYKI
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(130f)
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
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, color = viewModel.lines, shape = RoundedCornerShape(14.dp))
                    .padding(3.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(8f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        modifier = Modifier
                            .size(155.dp)
                            .weight(5f),
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
                .weight(130f)
                .padding(
                    start = 15.dp,
                    top = 7.dp,
                    end = 15.dp,
                    bottom = 7.dp
                )
        ) {

            OutlinedButton(
                onClick = { navController.navigate(route = DisciplineScreen.RewardScreen.name) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, color = viewModel.lines, shape = RoundedCornerShape(14.dp))
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
                                .size(70.dp)
                                .weight(1f),
                            painter = painterResource(R.drawable.ig_icon),
                            contentDescription = null
                        )

                        Image(
                            modifier = Modifier
                                .size(70.dp)
                                .weight(1f),
                            painter = painterResource(R.drawable.yt_icon),
                            contentDescription = null
                        )

                        Image(
                            modifier = Modifier
                                .size(70.dp)
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
                .weight(300f)
                .padding(
                    start = 15.dp,
                    top = 7.dp,
                    end = 15.dp,
                    bottom = 7.dp
                )
        ) {

            OutlinedButton(
                onClick = { navController.navigate(route = DisciplineScreen.TaskScreen.name) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, color = viewModel.lines, shape = RoundedCornerShape(14.dp))
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
                                            .border(
                                                1.dp,
                                                color = viewModel.lines,
                                                shape = CircleShape
                                            )
                                            .size(15.dp),
                                        colors = ButtonDefaults.buttonColors(backgroundColor = buttonsColors[it]),
                                        shape = CircleShape,
                                        onClick = {
                                            viewModel.credits += tasksPayoff[it]
                                            credit = viewModel.credits
                                            buttonsColors[it] = viewModel.todoButtonActivatedColor

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
                                        buttonsColors[it] = Color.Transparent
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
                                            color = viewModel.fontColor
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
                                            color = viewModel.fontColor
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
                                        color = viewModel.fontColor
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
    }
}