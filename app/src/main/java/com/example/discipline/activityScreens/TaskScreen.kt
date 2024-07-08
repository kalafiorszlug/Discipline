@file:Suppress("UNUSED_EXPRESSION")

package com.example.discipline

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries,UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries,UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.datastore.data.UserStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

data class Task(
    val title: String,
    val payoff: Int,
    val deadlineString: String,
    val deadlineInt: Int,
    val listBelonging: String
)

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null,
    val badgeCount: Int? = null
)

data class MenuItem(
    val title: String,
    var icon: ImageVector
)

fun OrderTasks(viewModel: SharedViewModel, parameter: String){
    val taskObjects = mutableListOf<Task>()
    for (i in viewModel.tasksTitles.indices) {
        taskObjects += Task(
            title = viewModel.tasksTitles[i],
            payoff = viewModel.tasksPayoff[i],
            deadlineString = viewModel.tasksDeadlines[i],
            deadlineInt = (
                    viewModel.tasksDeadlines[i].split("/")[0].toInt() +
                            viewModel.tasksDeadlines[i].split("/")[0].toInt() * 30 +
                            viewModel.tasksDeadlines[i].split("/")[0].toInt() * 365
                    ),
            listBelonging = viewModel.tasksListBelonging[i]
        )
    }
    val taskObjectsSorted = if (parameter == "deadlines"){
        taskObjects.sortedBy { it.deadlineInt }
    } else {
        taskObjects.sortedBy { it.payoff }
    }

    for (i in viewModel.tasksTitles.indices) {
        viewModel.tasksTitles[i] = taskObjectsSorted[i].title
        viewModel.tasksPayoff[i] = taskObjectsSorted[i].payoff
        viewModel.tasksDeadlines[i] = taskObjectsSorted[i].deadlineString
        viewModel.tasksListBelonging[i] = taskObjectsSorted[i].listBelonging
    }
}

@SuppressLint("MutableCollectionMutableState", "UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class
)
@Composable
fun TaskScreen(viewModel: SharedViewModel){

    if (viewModel.tasksOrderCriteria != null){
        OrderTasks(viewModel,
            viewModel.tasksOrderCriteria!!
        )
    }

    val context = LocalContext.current
    val store = UserStore(context)

    var drawerItems = viewModel.tasksLists

    val menuItems = mutableListOf(
        MenuItem(
            title = "delete this list",
            icon = Icons.Default.Delete
        ),

        MenuItem(
            title = "order by deadlines",
            icon = if (viewModel.tasksOrderCriteria == "deadlines"){
                Icons.Default.Check
            } else {
                Icons.Default.Close
            }
        ),

        MenuItem(
            title = "order by payoff",
            icon = if (viewModel.tasksOrderCriteria == "payoff"){
                Icons.Default.Check
            } else {
                Icons.Default.Close
            }
        ),
    )

    val keyboardController = LocalSoftwareKeyboardController.current

    var credit by remember { mutableIntStateOf(viewModel.credits) }
    var taskCreating by remember { mutableStateOf(false) }
    var deadlineChoosing by remember { mutableStateOf(false) }
    var tasksTitleFieldState by remember { mutableStateOf("") }
    var tasksPayoffFieldState by remember { mutableStateOf("") }
    var blurRadius by remember { mutableIntStateOf(0) }

    val scope = rememberCoroutineScope()

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val drawerState = androidx.compose.material3.rememberDrawerState(initialValue = DrawerValue.Closed)

    var isMenuExpanded by remember { mutableStateOf(false) }
    var menuOffset = Offset.Zero

    var listCreating by remember { mutableStateOf(false) }
    var listsTitleFieldState by remember { mutableStateOf("") }

    var tasksTitles by remember {
        mutableStateOf(viewModel.tasksTitles)
    }

    var tasksPayoff by remember {
        mutableStateOf(viewModel.tasksPayoff)
    }

    var tasksDeadlines by remember {
        mutableStateOf(viewModel.tasksDeadlines)
    }

    var tasksListBelonging by remember {
        mutableStateOf(viewModel.tasksListBelonging)
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

    val calendar = Calendar.getInstance()

    var selectedDateText by remember { mutableStateOf("0") }

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
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet(
                    drawerContainerColor = viewModel.backgroundColor,
                    drawerContentColor = viewModel.backgroundColor
                ) {
                    Column {
                        Column () {
                            drawerItems.forEachIndexed { index, item ->
                                NavigationDrawerItem(
                                    colors = NavigationDrawerItemDefaults.colors(
                                        selectedContainerColor = viewModel.backgroundColor,
                                        unselectedContainerColor = viewModel.backgroundColor
                                    ),
                                    label = {
                                        Text(
                                            text = item.title,
                                            color = viewModel.fontColor
                                        )
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = (if (index == selectedItemIndex) {
                                                Icons.AutoMirrored.Filled.KeyboardArrowRight
                                            } else Icons.AutoMirrored.Filled.KeyboardArrowLeft),
                                            contentDescription = "icon"
                                        )
                                    },
                                    badge = {
                                        item.badgeCount?.let{
                                            Text(text = item.badgeCount.toString(), color = viewModel.fontColor)
                                        }
                                    },
                                    selected = index == selectedItemIndex,
                                    onClick = {
                                        selectedItemIndex = index
                                        scope.launch {
                                            drawerState.close()
                                        }
                                    }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        OutlinedButton(
                            onClick = {
                                listCreating = true
                                blurRadius = 15
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                            shape = RoundedCornerShape(28.dp),
                            modifier = Modifier
                                .padding(2.dp)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text(text = "+ Add a list", style = MaterialTheme.typography.h1, color = viewModel.fontColor)
                        }
                    }
                }
            },
            drawerState = drawerState,
        ) {

            Scaffold(
                topBar = {
                    TopAppBar(
                        backgroundColor = viewModel.backgroundColor,
                        title = {
                            Text(
                                text = drawerItems[selectedItemIndex].title,
                                color = viewModel.fontColor
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu"
                                )
                            }
                        },
                        actions = {
                            IconButton(
                                modifier = Modifier
                                    .pointerInteropFilter {
                                        menuOffset = Offset(it.x, it.y)
                                        false
                                    },
                                onClick = { isMenuExpanded = true }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "Dots"
                                )
                            }
                        }
                    )

                    DropdownMenu(
                        expanded = isMenuExpanded,
                        onDismissRequest = { isMenuExpanded = false },
                        offset = DpOffset((menuOffset.x - 1500).dp, (menuOffset.y - 12).dp)
                    ) {
                        menuItems.forEach { item ->
                            DropdownMenuItem(
                                onClick = {
                                    if (item.title == "delete this list") {
                                        if (selectedItemIndex != 0) {
                                            val currentIndex = selectedItemIndex
                                            selectedItemIndex -= 1
                                            viewModel.tasksLists.removeAt(currentIndex)

                                            CoroutineScope(Dispatchers.IO).launch {
                                                store.saveTasksLists(viewModel.tasksLists)
                                            }
                                        }
                                    }

                                    if (item.title == "order by deadlines") {
                                        OrderTasks(viewModel, parameter = "deadlines")
                                        viewModel.tasksOrderCriteria = "deadlines"

                                        CoroutineScope(Dispatchers.IO).launch {
                                            store.saveTasksOrderCriteria(viewModel.tasksOrderCriteria!!)
                                        }

                                        item.icon = Icons.Default.Check
                                    }

                                    if (item.title == "order by payoff") {
                                        OrderTasks(viewModel, parameter = "payoff")
                                        viewModel.tasksOrderCriteria = "payoff"

                                        CoroutineScope(Dispatchers.IO).launch {
                                            store.saveTasksOrderCriteria(viewModel.tasksOrderCriteria!!)
                                        }

                                        item.icon = Icons.Default.Check
                                    }

                                    isMenuExpanded = false
                                }
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = "Delete"
                                )

                                Text(text = item.title)
                            }
                        }
                    }
                },
                content = {
                    Column {
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

                                if (tasksTitles.size >= 1) {
                                    buttonsClicked[0] = false
                                    todoTextStyles[0] = TextStyle(color = viewModel.fontColor)
                                    repeat(tasksTitles.size) {
                                        if (drawerItems[selectedItemIndex].title == viewModel.tasksListBelonging[it] || selectedItemIndex == 0) {
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
                                                            .border(
                                                                1.dp,
                                                                color = viewModel.lines,
                                                                shape = CircleShape
                                                            )
                                                            .size(15.dp),
                                                        colors = ButtonDefaults.buttonColors(
                                                            backgroundColor = buttonsColors[it]
                                                        ),
                                                        shape = CircleShape,
                                                        onClick = {

                                                            // Zmienianie wszystkich zmiennych po wykonaniu zadania
                                                            viewModel.credits += tasksPayoff[it]
                                                            credit = viewModel.credits
                                                            buttonsColors[it] =
                                                                viewModel.todoButtonActivatedColor

                                                            viewModel.tasksTitles.removeAt(it)
                                                            tasksTitles = viewModel.tasksTitles

                                                            viewModel.tasksDeadlines.removeAt(it)
                                                            tasksDeadlines =
                                                                viewModel.tasksDeadlines

                                                            viewModel.creditsAllTime += tasksPayoff[it]

                                                            viewModel.tasksPayoff.removeAt(it)
                                                            tasksPayoff = viewModel.tasksPayoff

                                                            viewModel.tasksListBelonging.removeAt(it)
                                                            tasksListBelonging =
                                                                viewModel.tasksListBelonging

                                                            viewModel.completedTasks += 1

                                                            CoroutineScope(Dispatchers.IO).launch {
                                                                store.saveTasksTitles(viewModel.tasksTitles)
                                                                store.saveTasksPayoff(viewModel.tasksPayoff)
                                                                store.saveTasksDeadlines(viewModel.tasksDeadlines)
                                                                store.saveTasksListBelonging(
                                                                    viewModel.tasksListBelonging
                                                                )
                                                                store.saveCredits(viewModel.credits.toString())
                                                            }

                                                            if (viewModel.tasksOrderCriteria != null){
                                                                OrderTasks(viewModel,
                                                                    viewModel.tasksOrderCriteria!!
                                                                )
                                                            }

                                                            buttonsClicked[it] = true
                                                        }
                                                    ) {}

                                                    if (buttonsClicked[it]) {
                                                        todoTextStyles[it] =
                                                            LocalTextStyle.current.copy(
                                                                textDecoration = TextDecoration.LineThrough
                                                            )
                                                        buttonsClicked[it] = false
                                                    } else {
                                                        buttonsColors[it] = Color.Transparent
                                                        todoTextStyles[it] =
                                                            LocalTextStyle.current.copy(
                                                                textDecoration = TextDecoration.None
                                                            )
                                                    }

                                                    Spacer(modifier = Modifier.width(20.dp))

                                                    // Animacja zmieniająca tytuły dostępnych zadań
                                                    AnimatedContent(
                                                        targetState = tasksTitles[it],
                                                        transitionSpec = {
                                                            (slideInVertically { height -> height } + fadeIn()).togetherWith(
                                                                slideOutVertically { height -> -height } + fadeOut())
                                                        }
                                                    ) { targetContent ->
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
                                                            (slideInVertically { height -> height } + fadeIn()).togetherWith(
                                                                slideOutVertically { height -> -height } + fadeOut())
                                                        }
                                                    ) { targetContent ->
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

                                                    if (tasksDeadlines[it] != "0") {
                                                        AnimatedContent(
                                                            targetState = "ㅤdeadline: ${tasksDeadlines[it]}ㅤ",
                                                            transitionSpec = {
                                                                (slideInVertically { height -> height } + fadeIn()).togetherWith(
                                                                    slideOutVertically { height -> -height } + fadeOut())
                                                            }
                                                        ) { targetContent ->
                                                            Text(
                                                                modifier = Modifier
                                                                    .background(
                                                                        color = colorResource(R.color.light_green),
                                                                        shape = RoundedCornerShape(
                                                                            size = 30.dp
                                                                        )
                                                                    ),
                                                                text = targetContent,
                                                                fontSize = 10.sp,
                                                                style = MaterialTheme.typography.h1,
                                                                color = Color.Black
                                                            )
                                                        }
                                                    }

                                                    if (selectedItemIndex == 0) {
                                                        if (tasksDeadlines[it] != "0") {
                                                            Spacer(modifier = Modifier.width(10.dp))
                                                        }

                                                        AnimatedContent(
                                                            targetState = "ㅤ${tasksListBelonging[it]}ㅤ",
                                                            transitionSpec = {
                                                                (slideInVertically { height -> height } + fadeIn()).togetherWith(
                                                                    slideOutVertically { height -> -height } + fadeOut())
                                                            }
                                                        ) { targetContent ->
                                                            Text(
                                                                modifier = Modifier
                                                                    .background(
                                                                        color = colorResource(R.color.light_green),
                                                                        shape = RoundedCornerShape(
                                                                            size = 30.dp
                                                                        )
                                                                    ),
                                                                text = targetContent,
                                                                fontSize = 10.sp,
                                                                style = MaterialTheme.typography.h1,
                                                                color = Color.Black
                                                            )
                                                        }
                                                    }
                                                }
                                            }

                                            Spacer(modifier = Modifier.height(10.dp))
                                        }
                                    }

                                    // Spełnienie warunku wszystkich wykonanych zadań
                                } else {
                                    buttonsClicked
                                    Spacer(modifier = Modifier.width(50.dp))

                                    // Jeżeli wszystkie zadania zostały wykonane, wyświetl tekst zaznaczający to
                                    AnimatedContent(
                                        targetState = "All planned tasks done!",
                                        transitionSpec = {
                                            (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                                                slideOutHorizontally { width -> -width } + fadeOut())
                                        }
                                    ) { targetContent ->
                                        Box(
                                            modifier =
                                            Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
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
                                .background(color = viewModel.backgroundColor)
                        )
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
                                Text(
                                    text = "+ Add a task",
                                    style = MaterialTheme.typography.h1,
                                    color = viewModel.fontColor
                                )
                            }
                        }

                        // Jeżeli kliknięto przycisk, wyświetl ekran odpowiadający tworzeniu nowego zadania
                        if (taskCreating) {

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
                                        .border(
                                            width = 2.dp,
                                            color = viewModel.lines,
                                            shape = RoundedCornerShape(16.dp)
                                        ),
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
                                                onDone = { keyboardController?.hide() }),
                                            singleLine = true,
                                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                                textColor = viewModel.fontColor,
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
                                                textColor = viewModel.fontColor,
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
                                                .border(
                                                    1.dp,
                                                    color = viewModel.lines,
                                                    shape = CircleShape
                                                )
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
                                                if (tasksTitleFieldState != "") {
                                                    viewModel.tasksTitles += tasksTitleFieldState
                                                    viewModel.tasksDeadlines += selectedDateText
                                                    viewModel.tasksPayoff += tasksPayoffFieldState.toInt()
                                                    viewModel.tasksListBelonging += drawerItems[selectedItemIndex].title
                                                }

                                                CoroutineScope(Dispatchers.IO).launch {
                                                    store.saveTasksTitles(viewModel.tasksTitles)
                                                    store.saveTasksPayoff(viewModel.tasksPayoff)
                                                    store.saveTasksDeadlines(viewModel.tasksDeadlines)
                                                    store.saveTasksListBelonging(viewModel.tasksListBelonging)
                                                }

                                                if (viewModel.tasksOrderCriteria != null){
                                                    OrderTasks(viewModel,
                                                        viewModel.tasksOrderCriteria!!
                                                    )
                                                }

                                                Log.d("titles", viewModel.tasksTitles.toString())
                                                Log.d("payoffs", viewModel.tasksPayoff.toString())
                                                Log.d(
                                                    "deadlines",
                                                    viewModel.tasksDeadlines.toString()
                                                )
                                                Log.d(
                                                    "lists",
                                                    viewModel.tasksListBelonging.toString()
                                                )

                                                tasksTitleFieldState = ""
                                                tasksPayoffFieldState = ""
                                                selectedDateText = "0"
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

                        if (listCreating) {

                            popupFinalOffset = 0

                            Popup(
                                offset = IntOffset(0, popupOffset),
                                alignment = Alignment.Center,
                                properties = PopupProperties(focusable = true)
                            ) {

                                // Kontener trzymający ekran tworzenia nowej listy
                                Box(
                                    modifier = Modifier
                                        .background(color = viewModel.backgroundColor)
                                        .size(400.dp)
                                        .border(
                                            width = 2.dp,
                                            color = viewModel.lines,
                                            shape = RoundedCornerShape(16.dp)
                                        ),
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
                                            value = listsTitleFieldState,
                                            onValueChange = { listsTitleFieldState = it },
                                            label = { Text("Define your direction.") },
                                            placeholder = { Text("Enter list's title.") },
                                            shape = RoundedCornerShape(30.dp),
                                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                            keyboardActions = KeyboardActions(
                                                onDone = { keyboardController?.hide() }),
                                            singleLine = true,
                                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                                textColor = viewModel.fontColor,
                                                unfocusedBorderColor = viewModel.focusableDefaultColor,
                                                unfocusedLabelColor = viewModel.focusableDefaultColor,
                                                focusedBorderColor = viewModel.focusableColor,
                                                focusedLabelColor = viewModel.focusableColor,
                                                placeholderColor = viewModel.focusableDefaultColor
                                            )
                                        )

                                        Spacer(modifier = Modifier.height(3.dp))

                                        // Przycisk zatwierdzający tworzenie nowej listy
                                        OutlinedButton(
                                            onClick = {
                                                if (listsTitleFieldState != "") {
                                                    viewModel.tasksLists += NavigationItem(
                                                        title = listsTitleFieldState,
                                                    )

                                                    drawerItems = viewModel.tasksLists
                                                }

                                                CoroutineScope(Dispatchers.IO).launch {
                                                    store.saveTasksLists(drawerItems)
                                                }

                                                listsTitleFieldState = ""
                                                popupFinalOffset = 1500
                                                blurRadius = 0
                                                listCreating = false
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
                                        text = "Credit: ${creditCounter}",
                                        fontSize = 30.sp,
                                        style = MaterialTheme.typography.h2,
                                        color = viewModel.fontColor
                                    )
                                }
                            }
                        }
                    }
                }
            )
        }
    }
}



