package com.example.discipline

import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import com.example.discipline.ui.theme.Purple500
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskScreen(navController: NavHostController, viewModel: SharedViewModel){

    var credit by remember { mutableStateOf(viewModel.credits) }
    var taskCreating by remember { mutableStateOf(false) }
    var deadlineChoosing by remember { mutableStateOf(false) }
    var tasksTitleFieldState by remember { mutableStateOf("") }
    var rewardsPriceFieldState by remember { mutableStateOf("") }
    var blurRadius by remember { mutableStateOf(0) }
    val tasksTitles = mutableListOf("Masturbacja", "Prześladowanie kobiet", "Ludobójstwo", "Lizanie chodnika", "Libacja alkoholowa")
    val tasksPayoff = mutableListOf(15, 20, 30, 25, 20)
    val numberOfTasks by remember { mutableStateOf(tasksTitles.size) }

    val buttonsColors by remember {
        mutableStateOf(mutableListOf(Color.White, Color.White, Color.White, Color.White, Color.White))
    }

    val todoTextStyles by remember {
        mutableStateOf(mutableListOf(TextStyle(color = Color.Black), TextStyle(color = Color.Black), TextStyle(color = Color.Black), TextStyle(color = Color.Black), TextStyle(color = Color.Black)))
    }

    val buttonsClicked by remember {
        mutableStateOf(mutableListOf(false, false, false, false, false))
    }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var selectedDateText by remember { mutableStateOf("") }

    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
        }, year, month, dayOfMonth
    )

    datePicker.datePicker.minDate = calendar.timeInMillis

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
                verticalArrangement = Arrangement.Top
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                repeat(numberOfTasks){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(25.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Spacer(modifier = Modifier.width(20.dp))

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
                                buttonsClicked[it] = true
                            }
                        ) {}

                        if (buttonsClicked[it]) {
                            todoTextStyles[it] = LocalTextStyle.current.copy(textDecoration = TextDecoration.LineThrough)
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        AnimatedVisibility(
                            visible = !buttonsClicked[it],
                        ){
                            Text(
                                text = tasksTitles[it],
                                fontSize = 20.sp,
                                style = todoTextStyles[it],
                                color = Color.Black
                            )
                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        AnimatedVisibility(
                            visible = !buttonsClicked[it]
                        ){
                            Text(modifier = Modifier
                                    .background(
                                        color = colorResource(R.color.light_green),
                                        shape = RoundedCornerShape(size = 20.dp)),
                                text = "ㅤ${tasksPayoff[it]}pㅤ",
                                fontSize = 20.sp,
                                style = MaterialTheme.typography.body1,
                                color = Color.Black
                            )
                        }

                    }

                    Spacer(modifier = Modifier.height(10.dp))
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
                    taskCreating = true
                    blurRadius = 15
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .padding(2.dp)
                    .align(Alignment.Center)
            ) {
                Text(text = "+ Add a task", style = MaterialTheme.typography.h1, color = Color.Black)
            }
        }

        if (taskCreating){

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
                        .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(16.dp)),
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
                            value = tasksTitleFieldState,
                            onValueChange = { tasksTitleFieldState = it },
                            label = { Text("Define your goal.") },
                            placeholder = { Text("Enter task's title.") },
                            shape = RoundedCornerShape(30.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = Color.Gray,
                                unfocusedLabelColor = Color.Gray,
                                focusedBorderColor = Purple500,
                                focusedLabelColor = Purple500,
                                placeholderColor = Color.Gray
                            )
                        )

                        OutlinedTextField(
                            modifier = Modifier
                                .width(370.dp)
                                .padding(3.dp),
                            value = rewardsPriceFieldState,
                            onValueChange = { rewardsPriceFieldState = it },
                            label = { Text("How much will be the payoff?") },
                            placeholder = { Text("Enter a value.") },
                            shape = RoundedCornerShape(30.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = Color.Gray,
                                unfocusedLabelColor = Color.Gray,
                                focusedBorderColor = Purple500,
                                focusedLabelColor = Purple500,
                                placeholderColor = Color.Gray
                            )
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        OutlinedButton(
                            modifier = Modifier
                                .width(370.dp)
                                .padding(3.dp),
                            onClick = { deadlineChoosing = true },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                            shape = RoundedCornerShape(28.dp),
                        ) {
                            Text(
                                "Chose a deadline",
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.body1,
                                color = Color.Black
                            )
                        }

                        if (deadlineChoosing) {
                            datePicker.show()
                        }

                        Spacer(modifier = Modifier.height(3.dp))

                        OutlinedButton(
                            onClick = {
                                popupFinalOffset = 1500
                                taskCreating = false
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
                                color = Color.Black
                            )
                        }

                    }
                }
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
                Row {
                    Text(
                        text = "Credit: ",
                        fontSize = 30.sp,
                        style = MaterialTheme.typography.body1,
                        color = Color.Black
                    )

                    Text(
                        text = credit.toString(),
                        fontSize = 30.sp,
                        style = MaterialTheme.typography.body1,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

