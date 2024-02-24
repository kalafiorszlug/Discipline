package com.example.discipline

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.datastore.data.UserStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SettingsScreen(navController: NavHostController, viewModel: SharedViewModel) {

    var themeSwitch by remember { mutableStateOf(false) }
    var backgroundColor by remember { mutableStateOf(viewModel.backgroundColor) }
    var fontColor by remember { mutableStateOf(viewModel.fontColor) }
    var lines by remember { mutableStateOf(viewModel.lines) }
    var todoButtonActivatedColor by remember { mutableStateOf(viewModel.todoButtonActivatedColor) }
    var focusableDefaultColor by remember { mutableStateOf(viewModel.focusableDefaultColor) }
    var focusableColor by remember { mutableStateOf(viewModel.focusableColor) }

    // pierdolnik do pokazywania obecnej sekcji w ustawieniach
    var currentSetting by remember { mutableStateOf(viewModel.currentSetting) }

    val temporaryBackgroundColor: Color by animateColorAsState(if (themeSwitch) viewModel.backgroundColor else backgroundColor,
        animationSpec = tween(500, easing = LinearEasing)
    )

    val temporaryFontColor: Color by animateColorAsState(if (themeSwitch) viewModel.fontColor else fontColor,
        animationSpec = tween(500, easing = LinearEasing)
    )

    val temporaryLinesColor: Color by animateColorAsState(if (themeSwitch) viewModel.lines else lines,
        animationSpec = tween(500, easing = LinearEasing)
    )

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val tokenValue = remember {
        mutableStateOf("")
    }
    val store = UserStore(context)
    val tokenText = store.getAccessToken.collectAsState(initial = "")

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = temporaryBackgroundColor),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

        //Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .clickable(onClick = { navController.navigate(route = DisciplineScreen.MainScreen.name) })
                    .size(50.dp)
                    .padding(12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Settings ${tokenText}",
                    style = MaterialTheme.typography.h1,
                    color = temporaryFontColor,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = currentSetting,
                    style = MaterialTheme.typography.h1,
                    color = temporaryFontColor,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center){

            OutlinedButton(
                onClick = {
                    backgroundColor = Color.DarkGray
                    viewModel.backgroundColor = backgroundColor

                    fontColor = Color.White
                    viewModel.fontColor = fontColor

                    lines = Color.White
                    viewModel.lines = lines

                    todoButtonActivatedColor = Color.White
                    viewModel.todoButtonActivatedColor = todoButtonActivatedColor

                    focusableDefaultColor = Color.LightGray
                    viewModel.focusableDefaultColor = focusableDefaultColor

                    focusableColor = Color.White
                    viewModel.focusableColor = focusableColor

                    tokenValue.value = "dark"

                    CoroutineScope(Dispatchers.IO).launch {
                        store.saveToken(tokenValue.value)
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                border = BorderStroke(1.dp, color = temporaryLinesColor),
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .width(100.dp)
            ) {
                Text(text = "dark", style = MaterialTheme.typography.body1, color = temporaryFontColor)
            }

            Spacer(modifier = Modifier.width(20.dp))

            OutlinedButton(
                onClick = {
                    backgroundColor = Color.White
                    viewModel.backgroundColor = backgroundColor

                    fontColor = Color.Black
                    viewModel.fontColor = fontColor

                    lines = Color.LightGray
                    viewModel.lines = lines

                    todoButtonActivatedColor = Color.Black
                    viewModel.todoButtonActivatedColor = todoButtonActivatedColor

                    focusableDefaultColor = Color.DarkGray
                    viewModel.focusableDefaultColor = focusableDefaultColor

                    focusableColor = Color.Black
                    viewModel.focusableColor = focusableColor

                    tokenValue.value = "light"

                    CoroutineScope(Dispatchers.IO).launch {
                        store.saveToken(tokenValue.value)
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                border = BorderStroke(1.dp, color = temporaryLinesColor),
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .width(100.dp)
            ) {
                Text(text = "light", style = MaterialTheme.typography.body1, color = temporaryFontColor)
            }

            Spacer(modifier = Modifier.width(20.dp))

            OutlinedButton(
                onClick = {
                    backgroundColor = Color.Black
                    viewModel.backgroundColor = backgroundColor

                    fontColor = Color.White
                    viewModel.fontColor = fontColor

                    lines = Color.White
                    viewModel.lines = lines

                    todoButtonActivatedColor = Color.White
                    viewModel.todoButtonActivatedColor = todoButtonActivatedColor

                    focusableDefaultColor = Color.White
                    viewModel.focusableDefaultColor = focusableDefaultColor

                    focusableColor = Color.White
                    viewModel.focusableColor = focusableColor
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                border = BorderStroke(1.dp, color = temporaryLinesColor),
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .width(120.dp)
            ) {
                Text(text = "minimalist", style = MaterialTheme.typography.body1, color = temporaryFontColor)
            }
        }
    }
}
