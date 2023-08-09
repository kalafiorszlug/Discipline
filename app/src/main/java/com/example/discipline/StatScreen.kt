package com.example.discipline

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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

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

@Composable
fun StatScreen(navController: NavController, viewModel: SharedViewModel) {

    var streakExplanation by remember { mutableStateOf(false) }

    val barChartInputsPercent = mutableListOf(10, 20, 30)
    val defaultMaxHeight = 200
    val borderColor = Color.Black
    val density = LocalDensity.current
    val strokeWidth = with(density) { 2.dp.toPx() }
    val constant = defaultMaxHeight/barChartInputsPercent.max()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.Center
        ) {

            repeat(3){

                Spacer(modifier = Modifier.width(30.dp))

                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(4.dp)
                        .size(width = 350.dp, height = 200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.then(
                            Modifier
                                .fillMaxWidth()
                                .height(defaultMaxHeight.dp)
                                .drawBehind {
                                    // draw X-Axis
                                    drawLine(
                                        color = borderColor,
                                        start = Offset(0f, size.height),
                                        end = Offset(size.width, size.height),
                                        strokeWidth = strokeWidth
                                    )
                                    // draw Y-Axis
                                    drawLine(
                                        color = borderColor,
                                        start = Offset(0f, 0f),
                                        end = Offset(0f, size.height),
                                        strokeWidth = strokeWidth
                                    )
                                }
                        ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        repeat(barChartInputsPercent.size){

                            Spacer(
                                modifier = Modifier
                                    .padding(horizontal = 5.dp)
                                    .height((barChartInputsPercent[it] * constant).dp)
                                    .weight(1f)
                                    .background(colorResource(R.color.light_green))
                            )
                        }
                    }

                    /*

                    Canvas(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        val paddingSpace = 20.dp
                        val xValues = listOf(1, 2, 3)
                        val yValues = listOf(1, 2, 3)
                        val points = listOf(1.0, 2.0, 3.0)
                        val verticalStep = 1
                        val textPaint = Paint()
                        var coordinates = mutableListOf(PointF(0.0f, 0.0f))
                        var controlPoints1 = mutableListOf(PointF(0.0f, 0.0f))
                        var controlPoints2 = mutableListOf(PointF(0.0f, 0.0f))

                        val xAxisSpace = (size.width - paddingSpace.toPx()) / xValues.size
                        val yAxisSpace = size.height / yValues.size
                        /** placing x axis points */
                        for (i in xValues.indices) {
                            drawContext.canvas.nativeCanvas.drawText(
                                "${xValues[i]}",
                                xAxisSpace * (i + 1),
                                size.height - 30,
                                textPaint
                            )
                        }
                        /** placing y axis points */
                        for (i in yValues.indices) {
                            drawContext.canvas.nativeCanvas.drawText(
                                "${yValues[i]}",
                                paddingSpace.toPx() / 2f,
                                size.height - yAxisSpace * (i + 1),
                                textPaint
                            )
                        }

                        /** placing points */
                        for (i in points.indices) {
                            val x1 = xAxisSpace * xValues[i]
                            val y1 = size.height - (yAxisSpace * (points[i]/verticalStep.toFloat()))
                            coordinates.add(PointF(x1.toFloat(),y1.toFloat()))
                            /** drawing circles to indicate all the points */
                            drawCircle(
                                color = Color.Black,
                                radius = 10f,
                                center = Offset(x1.toFloat(),y1.toFloat())
                            )
                        }

                        for (i in 1 until coordinates.size) {
                            controlPoints1.add(PointF((coordinates[i].x + coordinates[i - 1].x) / 2, coordinates[i - 1].y))
                            controlPoints2.add(PointF((coordinates[i].x + coordinates[i - 1].x) / 2, coordinates[i].y))
                        }

                        /** drawing the path */
                        val stroke = Path().apply {
                            reset()
                            moveTo(coordinates.first().x, coordinates.first().y)
                            for (i in 0 until coordinates.size - 1) {
                                cubicTo(
                                    controlPoints1[i].x,controlPoints1[i].y,
                                    controlPoints2[i].x,controlPoints2[i].y,
                                    coordinates[i + 1].x,coordinates[i + 1].y
                                )
                            }
                        }

                        drawPath(
                            stroke,
                            color = Color.Black,
                            style = Stroke(
                                width = 5f,
                                cap = StrokeCap.Round
                            )
                        )
                    }

                     */
                }

                Spacer(modifier = Modifier.width(30.dp))

            }
        }
        
        Spacer(modifier = Modifier.height(50.dp))

        Column(modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

        }
        Text(text = "Statistics", fontSize = 30.sp, style = MaterialTheme.typography.h1)
        
        Spacer(modifier = Modifier.height(10.dp))

        TextButton(
            onClick = {
                streakExplanation = !streakExplanation
            },
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Transparent,
                contentColor = Color.Black
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

        AnimatedVisibility(
            visible = streakExplanation
        ) {
            Text(text = "Number of days in a row which you have completed tasks in", fontSize = 14.sp, style = MaterialTheme.typography.h3)
        }

        Text(text = "Total credits collected: ${viewModel.creditsAllTime}", fontSize = 20.sp, style = MaterialTheme.typography.body1)
        Text(text = "Total tasks completed: ${viewModel.completedTasks}", fontSize = 20.sp, style = MaterialTheme.typography.body1)

        Text(text = "Best all time streak : ${viewModel.bestStreak} days", fontSize = 16.sp, style = MaterialTheme.typography.body2)
        Text(text = "Total credits spent: ${viewModel.creditsSpentAllTime}", fontSize = 16.sp, style = MaterialTheme.typography.body2)

    }
}


