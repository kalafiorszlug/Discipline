package com.example.discipline.activityScreens

import android.annotation.SuppressLint
import android.content.pm.PackageManager
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
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.datastore.data.UserStore
import com.example.discipline.AppInfo
import com.example.discipline.DisciplineScreen
import com.example.discipline.OrderTasks
import com.example.discipline.R
import com.example.discipline.Reward
import com.example.discipline.SharedViewModel
import com.example.discipline.getInstalledApps
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.min

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("MutableCollectionMutableState", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController, viewModel: SharedViewModel, packageManager: PackageManager) {

    val context = LocalContext.current
    val store = UserStore(context)

    val t = store.getCredits.collectAsState(initial = "0").value
    viewModel.credits = t.toInt()

    var credit by remember { mutableStateOf(viewModel.credits) }
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
        mutableStateOf(mutableListOf(Color.Transparent, Color.Transparent, Color.Transparent, Color.Transparent, Color.Transparent))
    }

    val todoTextStyles by remember {
        mutableStateOf(mutableListOf(TextStyle(color = Color.Black), TextStyle(color = Color.Black), TextStyle(color = Color.Black), TextStyle(color = Color.Black), TextStyle(color = Color.Black)))
    }

    val buttonsClicked by remember {
        mutableStateOf(mutableListOf(false, false, false, false, false))
    }

    val save = viewModel.tasksThroughoutTheWeek[viewModel.dayOfTheWeek - 1]

    val currentDate = remember { Calendar.getInstance().time }

    val formattedDate = remember {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(currentDate)
    }

    val creditCounter by animateIntAsState(
        targetValue = credit,
        animationSpec = tween(
            durationMillis = 700,
            easing = FastOutSlowInEasing
        )
    )

    var appList by remember { mutableStateOf(emptyList<AppInfo>()) }
    LaunchedEffect(Unit) {
        appList = getInstalledApps(packageManager)
    }

    for (reward in viewModel.rewards){
        viewModel.rewardsIcons += null
        reward.errorMessage = ""
    }

    for (i in viewModel.rewards.indices){
        for (app in appList){
            if (viewModel.rewards[i].appName == app.appName){
                viewModel.rewardsIcons[i] = app.icon
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = viewModel.backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Scaffold (
            topBar = {
                TopAppBar(
                    backgroundColor = viewModel.backgroundColor,
                    title = {
                        Column (
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Text(
                                modifier = Modifier.padding(1.dp),
                                text = "Hello, ${viewModel.userName}!",
                                color = viewModel.fontColor,
                                fontSize = 17.sp,
                            )

                            Text(
                                text = formattedDate,
                                fontSize = 10.sp,
                                color = viewModel.fontColor,
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.navigate(route = DisciplineScreen.SettingsScreen.name)
                        }) {
                            Icon(
                                Icons.Rounded.Settings,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(50.dp)
                                    .padding(12.dp)
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                navController.navigate(route = DisciplineScreen.StatScreen.name)
                            }
                        ) {
                            Icon(
                                Icons.Rounded.AccountCircle,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(60.dp)
                                    .padding(10.dp)
                            )
                        }
                    }
                )
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = viewModel.backgroundColor),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(40f)
                            .padding(
                                start = 15.dp,
                                top = 7.dp,
                                end = 15.dp,
                                bottom = 7.dp
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Credit: ${creditCounter}",
                            fontSize = 30.sp,
                            style = MaterialTheme.typography.h2,
                            color = viewModel.fontColor
                        )
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
                                .border(
                                    1.dp,
                                    color = viewModel.lines,
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .padding(3.dp)
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Bottom,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (viewModel.rewards != emptyList<Reward>()){
                                    Text(
                                        modifier = Modifier
                                            .padding(1.dp),
                                        text = if (viewModel.credits < viewModel.rewards[viewModel.mainScreenRewardsIndex].cost){
                                            "You need ${viewModel.rewards[viewModel.mainScreenRewardsIndex].cost - viewModel.credits} more credits to enjoy it"
                                        } else {
                                            "Perhaps take a break?"
                                        },
                                        style = MaterialTheme.typography.h2,
                                        color = viewModel.fontColor,
                                        fontSize = 15.sp
                                    )

                                    Spacer(modifier = Modifier.weight(1f))

                                    if (viewModel.rewardsIcons.size > 0){
                                        if (viewModel.rewardsIcons[viewModel.mainScreenRewardsIndex] != null){
                                            // Ikona nagrody
                                            Image(
                                                modifier = Modifier
                                                    .size(55.dp)
                                                    .padding(3.dp),
                                                painter = rememberDrawablePainter(drawable = viewModel.rewardsIcons[viewModel.mainScreenRewardsIndex]),
                                                contentDescription = null
                                            )
                                        }
                                    }

                                    // Tytuł nagrody
                                    Text(
                                        modifier = Modifier
                                            .padding(3.dp),
                                        text = viewModel.rewards[viewModel.mainScreenRewardsIndex].title,
                                        style = MaterialTheme.typography.h2,
                                        color = viewModel.fontColor
                                    )
                                } else {
                                    Spacer(modifier = Modifier.weight(1f))

                                    Text(
                                        modifier = Modifier
                                            .padding(3.dp),
                                        text = "No rewards to display",
                                        style = MaterialTheme.typography.h2,
                                        color = viewModel.fontColor
                                    )
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Column(modifier = Modifier
                                    .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .padding(2.dp)
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
                                .border(
                                    1.dp,
                                    color = viewModel.lines,
                                    shape = RoundedCornerShape(14.dp)
                                )
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

                                    if (tasksTitles.size >= 1) {
                                        buttonsClicked[0] = false
                                        todoTextStyles[0] = TextStyle(color = viewModel.fontColor)
                                        repeat(min(5, tasksTitles.size)) {
                                            Column() {

                                                Row (
                                                    verticalAlignment = Alignment.CenterVertically
                                                ){

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

                                                    Column {
                                                        Row(
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .height(25.dp),
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {

                                                            Spacer(modifier = Modifier.width(3.dp))

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

                                                            Spacer(modifier = Modifier.width(10.dp))

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

                                                            Spacer(modifier = Modifier.weight(1f))

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
                                                            Spacer(modifier = Modifier.width(12.dp))

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
        )
    }
}