package com.example.discipline

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import java.util.*

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun TaskScreen(viewModel: SharedViewModel){

    val keyboardController = LocalSoftwareKeyboardController.current

    var credit by remember { mutableStateOf(viewModel.credits) }
    var taskCreating by remember { mutableStateOf(false) }
    var deadlineChoosing by remember { mutableStateOf(false) }
    var tasksTitleFieldState by remember { mutableStateOf("") }
    var tasksPayoffFieldState by remember { mutableStateOf("") }
    var blurRadius by remember { mutableStateOf(0) }

    var tasksTitles by remember {
        mutableStateOf(viewModel.tasksTitles)
    }
    var tasksPayoff by remember {
        mutableStateOf(viewModel.tasksPayoff)
    }

    val buttonsColors by remember {
        mutableStateOf(mutableListOf(Color.Transparent))
    }

    val todoTextStyles by remember {
        mutableStateOf(mutableListOf(TextStyle(color = Color.Black)))
    }

    val buttonsClicked by remember {
        mutableStateOf(mutableListOf(false))
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

    // Animacja popapu (kurwa jebac to gowno pierdolone)
    val popupOffset by animateIntAsState(
        targetValue = popupFinalOffset,
        animationSpec = tween(
            durationMillis = 100,
            easing = FastOutSlowInEasing
        )
    )

    // Animacja kredytów
    val creditCounter by animateIntAsState(
        targetValue = credit,
        animationSpec = tween(
            durationMillis = 700,
            easing = FastOutSlowInEasing
        )
    )

    // Warunek dorabiania właściwości przycisków do nowo dodanych zadań
    if (tasksTitles.size > buttonsColors.size){
        repeat(tasksTitles.size - buttonsColors.size){
            buttonsColors += viewModel.lines
            todoTextStyles += TextStyle(color = viewModel.fontColor)
            buttonsClicked += false
        }
    }

    // Kolumna trzymająca cały ekran
    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(color = viewModel.backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

    ) {

        // Kontener trzymający wszystkie zadania
        // Adnotacja urzytkownika kalafior_szlug: Używaj jezyka polskiego, nieudaczniku.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(9f)
                .background(color = viewModel.backgroundColor)
                .blur(blurRadius.dp)

        ) {

            // Kolumna wyświetlająca dostępne zadania
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                if (tasksTitles.size >= 1){
                    buttonsClicked[0] = false
                    todoTextStyles[0] = TextStyle(color = viewModel.fontColor)
                    repeat(tasksTitles.size){
                        Column() {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(25.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Spacer(modifier = Modifier.width(20.dp))

                                // Przycisk do odznaczenia wykonanego zadania
                                OutlinedButton(
                                    modifier = Modifier
                                        .border(1.dp, color = viewModel.lines, shape = CircleShape)
                                        .size(15.dp),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = buttonsColors[it]),
                                    shape = CircleShape,
                                    onClick = {

                                        // Zmienianie wszystkich zmiennych po wykonaniu zadania
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

                                // Animacja zmieniająca tytuły dostępnych zadań
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
                                        style = todoTextStyles[it],
                                        color = viewModel.fontColor
                                    )
                                }

                                Spacer(modifier = Modifier.width(20.dp))

                                // Animacja zmieniająca punkty za wykonanie zadania
                                AnimatedContent(
                                    targetState = "ㅤ${tasksPayoff[it]}pㅤ",
                                    transitionSpec = {
                                        slideInVertically { height -> height } + fadeIn() with
                                                slideOutVertically { height -> -height } + fadeOut()
                                    }
                                ){targetContent->
                                    Text(
                                        text = targetContent,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Normal,
                                        style = MaterialTheme.typography.body2,
                                        color = viewModel.fontColor
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(5.dp))

                            // Rząd trzymający ostateczny termin wykonania zadania
                            Row() {
                                Spacer(modifier = Modifier.width(55.dp))

                                AnimatedContent(
                                    targetState = "ㅤdeadline: ${viewModel.tasksDeadlines[it]}ㅤ",
                                    transitionSpec = {
                                        slideInVertically { height -> height } + fadeIn() with
                                                slideOutVertically { height -> -height } + fadeOut()
                                    }
                                ){targetContent->
                                    Text(
                                        modifier = Modifier
                                            .background(
                                                color = colorResource(R.color.light_green),
                                                shape = RoundedCornerShape(size = 30.dp)
                                            ),
                                        text = targetContent,
                                        fontSize = 10.sp,
                                        style = MaterialTheme.typography.h1,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    // Spełnienie warunku wszystkich wykonanych zadań
                } else {
                    buttonsClicked
                    Spacer(modifier = Modifier.width(50.dp))

                    // Jeżeli wszystkie zadania zostały wykonane, wyświetl tekst zaznaczający to
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
        }

        // Kontener trzymający przycisk tworzenia nowego zadania
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = viewModel.backgroundColor))
        {

            // Przycisk odpowiadający tworzeniu nowego zadania
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
                Text(text = "+ Add a task", style = MaterialTheme.typography.h1, color = viewModel.fontColor)
            }
        }

        // Jeżeli kliknięto przycisk, wyświetl ekran odpowiadający tworzeniu nowego zadania
        if (taskCreating){

            popupFinalOffset = 0

            Popup(
                offset = IntOffset(0, popupOffset),
                alignment = Alignment.Center,
                properties = PopupProperties(focusable = true)
            ) {

                // Kontener trzymający ekran tworzenia nowego zadaniu
                Box(
                    modifier = Modifier
                        .background(color = viewModel.backgroundColor)
                        .size(400.dp)
                        .border(width = 2.dp, color = viewModel.lines, shape = RoundedCornerShape(16.dp)),
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        // Pole tekstowe z nazwą nowego zadania
                        OutlinedTextField(
                            modifier = Modifier
                                .width(370.dp)
                                .padding(3.dp),
                            value = tasksTitleFieldState,
                            onValueChange = { tasksTitleFieldState = it },
                            label = { Text("Define your goal.") },
                            placeholder = { Text("Enter task's title.") },
                            shape = RoundedCornerShape(30.dp),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = {keyboardController?.hide()}),
                            singleLine = true,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = viewModel.focusableDefaultColor,
                                unfocusedLabelColor = viewModel.focusableDefaultColor,
                                focusedBorderColor = viewModel.focusableColor,
                                focusedLabelColor = viewModel.focusableColor,
                                placeholderColor = viewModel.focusableDefaultColor
                            )
                        )

                        // Pole tekstowe z nagrodą za wykonanie zadania
                        OutlinedTextField(
                            modifier = Modifier
                                .width(370.dp)
                                .padding(3.dp),
                            value = tasksPayoffFieldState,
                            onValueChange = { tasksPayoffFieldState = it },
                            label = { Text("How much will be the payoff?") },
                            placeholder = { Text("Enter a value.") },
                            shape = RoundedCornerShape(30.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = viewModel.focusableDefaultColor,
                                unfocusedLabelColor = viewModel.focusableDefaultColor,
                                focusedBorderColor = viewModel.focusableColor,
                                focusedLabelColor = viewModel.focusableColor,
                                placeholderColor = viewModel.focusableDefaultColor
                            )
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        // Przycisk z wyborem treminu wykonania zadania
                        OutlinedButton(
                            modifier = Modifier
                                .width(370.dp)
                                .border(1.dp, color = viewModel.lines, shape = CircleShape)
                                .padding(3.dp),
                            onClick = { deadlineChoosing = true },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                            shape = RoundedCornerShape(28.dp),
                        ) {
                            Text(
                                "Chose a deadline",
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.body1,
                                color = viewModel.fontColor
                            )
                        }

                        // Jeżeli kliknięto przycisk, wyświetl kalendarz aby wybrać termin wykonania zadania
                        if (deadlineChoosing) {
                            datePicker.show()
                            deadlineChoosing = false
                        }

                        Spacer(modifier = Modifier.height(3.dp))

                        // Przycisk zatwierdzający tworzenie nowego zadania
                        OutlinedButton(
                            onClick = {
                                if (tasksTitleFieldState != ""){
                                    viewModel.tasksTitles += tasksTitleFieldState
                                }

                                if (tasksTitleFieldState != ""){
                                    viewModel.tasksPayoff += tasksPayoffFieldState.toInt()
                                }

                                if (tasksTitleFieldState != ""){
                                    viewModel.tasksDeadlines += selectedDateText
                                }

                                tasksTitleFieldState = ""
                                tasksPayoffFieldState = ""
                                selectedDateText = ""
                                popupFinalOffset = 1500
                                blurRadius = 0
                                taskCreating = false
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
                                color = viewModel.fontColor
                            )
                        }

                    }
                }
            }
        }

        Divider(color = Color.Gray, thickness = 2.dp)

        // Kontener z aktualną ilością kredytów
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = viewModel.backgroundColor)
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
                        text = "Credit: $creditCounter",
                        fontSize = 30.sp,
                        style = MaterialTheme.typography.body1,
                        color = viewModel.fontColor
                    )
                }
            }
        }
    }
}



