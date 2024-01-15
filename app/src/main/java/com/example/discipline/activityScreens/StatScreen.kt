package com.example.discipline

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.lang.Integer.max

// Funkcja odpowiadająca za wykres
@Composable
fun RowScope.Bar(
    value: Int,
    color: Color,
    maxHeight: Dp
) {

    val itemHeight = remember(value) { value * maxHeight.value / 100 }

    Spacer(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .height(itemHeight.dp)
            .weight(1f)
            .background(color)
    )
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun StatScreen(navController: NavController, viewModel: SharedViewModel) {

    var streakExplanation by remember { mutableStateOf(false) }
    var creditsExplanation by remember { mutableStateOf(false) }
    var tasksExplanation by remember { mutableStateOf(false) }

    // Zmienne pomagające przy tworzeniu wykresu
    val tasksThroughoutTheWeek by remember {
        mutableStateOf(viewModel.tasksThroughoutTheWeek)
    }
    val daysOfTheWeek = listOf("sun", "mon", "tue", "wed", "thu", "fri", "sat")
    var numberOfCharts = 1
    val defaultMaxHeight = 200
    val borderColor = viewModel.lines
    val density = LocalDensity.current
    val strokeWidth = with(density) { 2.dp.toPx() }
    val constant = defaultMaxHeight/max(tasksThroughoutTheWeek.max(), 1)

    val localDensity = LocalDensity.current
    var xAxisWidth by remember {
        mutableStateOf(0.dp)
    }
    var textWidth by remember {
        mutableStateOf(0.dp)
    }

    // Kolumna trzymająca cały ekran
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(viewModel.backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        // Wykres reprezentujący statystyki
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.Center
        ) {

            repeat(numberOfCharts){

                Spacer(modifier = Modifier.width(15.dp))

                Box(
                    modifier = Modifier
                        .background(viewModel.backgroundColor)
                        .padding(4.dp)
                        .size(width = 370.dp, height = 230.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column() {
                        Row(
                            modifier = Modifier.then(
                                Modifier
                                    .fillMaxWidth()
                                    .height(defaultMaxHeight.dp)
                                    .onGloballyPositioned { coordinates ->
                                        xAxisWidth =
                                            with(localDensity) { coordinates.size.width.toDp() }
                                    }
                                    .drawBehind {
                                        // draw X-Axis
                                        drawLine(
                                            color = borderColor,
                                            start = Offset(0f, size.height),
                                            end = Offset(size.width, size.height),
                                            strokeWidth = strokeWidth
                                        )
                                    }
                            ),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            repeat(viewModel.dayOfTheWeek){
                                Spacer(
                                    modifier = Modifier
                                        .padding(horizontal = 5.dp, vertical = 1.dp)
                                        .height((tasksThroughoutTheWeek[it] * constant).dp)
                                        .weight(1f)
                                        .background(colorResource(R.color.light_green))
                                )
                            }
                        }

                        var y = (xAxisWidth - (viewModel.dayOfTheWeek * 5).dp) / viewModel.dayOfTheWeek

                        var x = 5.dp + (y - textWidth) / 2

                        Spacer(modifier = Modifier.height(5.dp))

                        Row() {
                            repeat(viewModel.dayOfTheWeek){
                                Spacer(modifier = Modifier.width(x))

                                Text(modifier = Modifier
                                    .onGloballyPositioned { coordinates ->
                                        textWidth =
                                            with(localDensity) { coordinates.size.width.toDp() }
                                    },
                                    text = daysOfTheWeek[it],
                                    color = viewModel.fontColor)

                                Spacer(modifier = Modifier.width(x))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.width(15.dp))

            }
        }
        
        Spacer(modifier = Modifier.height(50.dp))

        Text(text = "Statistics", fontSize = 30.sp, style = MaterialTheme.typography.h1, color = viewModel.fontColor)
        
        Spacer(modifier = Modifier.height(10.dp))

        // Tekst z passą zadań i wytłumaczeniem passy
        TextButton(
            onClick = {
                streakExplanation = !streakExplanation
            },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Transparent,
                contentColor = viewModel.fontColor
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

        // Tekst tłumaczący passę
        AnimatedVisibility(
            visible = streakExplanation
        ) {
            Text(text = "Number of days in a row which you have completed tasks in", fontSize = 14.sp, style = MaterialTheme.typography.h3, color = viewModel.fontColor)
        }

        // Tekst z ilością kredytów
        TextButton(
            onClick = {
                creditsExplanation = !creditsExplanation
            },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Transparent,
                contentColor = viewModel.fontColor
            ),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .width(250.dp)
                .height(38.dp)
        ){
            Text(
                text = "Total credits collected: ${viewModel.creditsAllTime}",
                fontSize = 20.sp,
                style = MaterialTheme.typography.body1,
                color = viewModel.fontColor
            )
        }

        // Tekst tłumaczący kredyty
        AnimatedVisibility(
            visible = creditsExplanation
        ) {
            Text(text = "Number of points that allow you claiming rewards", fontSize = 14.sp, style = MaterialTheme.typography.h3, color = viewModel.fontColor)
        }

        // Tekst z ilością wykonanych zadań
        TextButton(
            onClick = {
                tasksExplanation = !tasksExplanation
            },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Transparent,
                contentColor = viewModel.fontColor
            ),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .width(250.dp)
                .height(38.dp)
        ){
            Text(
                text = "Total tasks completed: ${viewModel.completedTasks}",
                fontSize = 20.sp,
                style = MaterialTheme.typography.body1,
                color = viewModel.fontColor
            )
        }

        // Tekst tłumaczący statystykę wykonanych zadań
        AnimatedVisibility(
            visible = tasksExplanation
        ) {
            Text(text = "Number of tasks completed through out your whole journey", fontSize = 14.sp, style = MaterialTheme.typography.h3, color = viewModel.fontColor)
        }

        // Teksty z rekordami statystyk w aplikacji
        Text(text = "Best all time streak : ${viewModel.bestStreak} days", fontSize = 16.sp, style = MaterialTheme.typography.body2, color = viewModel.fontColor)
        Text(text = "Total credits spent: ${viewModel.creditsSpentAllTime}", fontSize = 16.sp, style = MaterialTheme.typography.body2, color = viewModel.fontColor)

    }
}


