package com.example.discipline

import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.datastore.data.UserStore

@Composable
fun LoadingScreen(navController: NavHostController, viewModel: SharedViewModel, packageManager: PackageManager) {

    val context = LocalContext.current
    val store = UserStore(context)

    //wczytywanie wartości licznika kredytów zapisanej jako String (jeśli proces będzie niemożliwy, linijka zwraca 0)
    val t = store.getCredits.collectAsState(initial = "0").value
    //przypisanie wartości odpowiedniej zmiennej poprzez zamianę wczytanej wartości na Integer
    viewModel.credits = t.toInt()

    //wypisywanie wartości zmiennej przechowującej wartość licznika
    Log.d("chuj", viewModel.credits.toString())
    //Wynik powyższego działania zawsze wynosi 0 - jakby żadne dane nie zostały zapisane

    //wczytanie zapisaniej listy zadań
    val integers by store.getTasksPayoff.collectAsState(initial = emptyList())
    //usuwanie jedynego elementu znajdującego się na liście domyślnie
    viewModel.tasksPayoff.clear()
    //dodanie do odpowiedniej listy wczytanych danych
    viewModel.tasksPayoff.addAll(integers)
    //itd.

    LaunchedEffect(Unit) {
        store.getTasksTitles.collect { list ->
            viewModel.tasksTitles = list.toMutableStateList()
        }
    }

    LaunchedEffect(Unit) {
        store.getTasksDeadlines.collect { list2 ->
            viewModel.tasksDeadlines = list2.toMutableList()
        }

    }

    LaunchedEffect(Unit) {
        store.getTasksLists.collect { list3 ->
            viewModel.tasksLists = list3.toMutableList()
        }

    }

    LaunchedEffect(Unit) {
        store.getTasksListBelonging.collect { list4 ->
            viewModel.tasksListBelonging = list4.toMutableList()
        }

    }

    LaunchedEffect(Unit) {
        store.getTasksPayoff.collect { list5 ->
            viewModel.tasksPayoff = list5.toMutableList()
        }

    }

    LaunchedEffect(Unit) {
        store.getRewards.collect { list6 ->
            viewModel.rewards = list6.toMutableList()
        }

    }

    var appList by remember { mutableStateOf(emptyList<AppInfo>()) }
    LaunchedEffect(Unit) {
        appList = getInstalledApps(packageManager)
    }

    viewModel.tasksOrderCriteria = store.getTasksOrderCirteria.collectAsState(initial = null).value

    val theme = store.getTheme.collectAsState(initial = "")
    Log.d("MOTYW", theme.value)
    viewModel.userName = store.getUsername.collectAsState(initial = "kalafior_szlug").value

    if (theme.value == "dark"){
        viewModel.backgroundColor = Color.DarkGray
        viewModel.fontColor = Color.White
        viewModel.lines = Color.White
        viewModel.todoButtonActivatedColor = Color.White
        viewModel.focusableDefaultColor = Color.LightGray
        viewModel.focusableColor = Color.White
    }

    if (theme.value == "light"){
        viewModel.backgroundColor = Color.White
        viewModel.fontColor = Color.Black
        viewModel.lines = Color.LightGray
        viewModel.todoButtonActivatedColor = Color.Black
        viewModel.focusableDefaultColor = Color.DarkGray
        viewModel.focusableColor = Color.Black
    }

    if (theme.value == "minimalist"){
        viewModel.backgroundColor = Color.Black
        viewModel.fontColor = Color.White
        viewModel.lines = Color.White
        viewModel.todoButtonActivatedColor = Color.White
        viewModel.focusableDefaultColor = Color.White
        viewModel.focusableColor = Color.White
    }

    viewModel.isUserLoggedIn = store.getLoginInformation.collectAsState(initial = "no").value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(viewModel.backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = "Discipline",
            style = MaterialTheme.typography.h1,
            color = viewModel.fontColor,
            fontSize = 50.sp
        )

        if (viewModel.isUserLoggedIn == ""){
            Text(
                modifier = Modifier.padding(20.dp),
                text = "Hi, our app needs a permission to block other android programs. Please, enable Discipline to monitor and interact with other apps.",
                fontSize = 20.sp,
                style = MaterialTheme.typography.h2,
                color = viewModel.fontColor
            )

            OutlinedButton(
                onClick = {
                    val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                    ContextCompat.startActivity(context, intent, null)

                    navController.navigate(route = DisciplineScreen.LoginScreen.name)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .padding(2.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = "go to settings", style = MaterialTheme.typography.h1, color = viewModel.fontColor)
            }
        }
    }
    navController.navigate(route = DisciplineScreen.MainScreen.name)
}