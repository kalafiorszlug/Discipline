package com.example.discipline

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun RewardScreen(viewModel: SharedViewModel) {

    // Zmienne odpowiadające za dostępne nagrody
    var rewardCreating by remember { mutableStateOf(false) }
    var appOrWebsiteBlocking by remember { mutableStateOf(false) }
    var rewardsTitleFieldState by remember { mutableStateOf("") }
    var rewardsPriceFieldState by remember { mutableStateOf("") }
    var rewardsDurationFieldState by remember { mutableStateOf("") }
    var blurRadius by remember { mutableStateOf(0) }
    var images = mutableListOf(R.drawable.yt_icon, R.drawable.ig_icon, R.drawable.snap_icon, R.drawable.twitter_icon, R.drawable.tiktok_icon)
    var titles = mutableListOf("watching YouTube", "Instagram scrolling", "unlocking Snapchat", "unlocking Twitter", "watching TikToks")
    var prices = mutableListOf(150, 115, 120, 100, 200)
    var error by remember {
        mutableStateOf(mutableListOf("", "", "", "", ""))
    }

    var numberOfRewards by remember { mutableStateOf(titles.size) }

    var displayError by remember { mutableStateOf(false) }

    var pushIterator = 0

    var enoughCredit by remember { mutableStateOf(false) }
    var priceForPurchasePopup by remember { mutableStateOf(0) }

    var popupFinalOffset by remember { mutableStateOf(2000) }
    val keyboardController = LocalSoftwareKeyboardController.current

    // JEBANA ANIMACJA POPAPU
    val popupOffset by animateIntAsState(
        targetValue = popupFinalOffset,
        animationSpec = tween(
            durationMillis = 100,
            easing = FastOutSlowInEasing
        )
    )

    val creditCounter by animateIntAsState(
        targetValue = viewModel.credits,
        animationSpec = tween(
            durationMillis = 700,
            easing = FastOutSlowInEasing
        )
    )

    // Kolumna trzymająca cały ekran
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
                .background(color = viewModel.backgroundColor)
                .blur(blurRadius.dp)

        ) {
            // Kolumna trzymająca dostępne nagrody
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Wyświetlanie nagród
                repeat(numberOfRewards){

                    // Przycisk odpowiadający danej nagrodzie
                    OutlinedButton(
                        onClick = {
                            if (viewModel.credits >= prices[it]){
                                blurRadius = 15
                                enoughCredit = true
                                priceForPurchasePopup = prices[it]
                            } else {
                                pushIterator = it
                                displayError = true
                            }
                                  },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        shape = RoundedCornerShape(28.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(170.dp)
                            .padding(3.dp)
                            .border(1.dp, color = Gray, shape = RoundedCornerShape(28.dp))
                    ) {

                        // Jeżeli użytkownikowi brakuje punktów, wyświetl błąd
                        if(displayError){
                            error[pushIterator] = "You can't afford this."
                            displayError = false
                        }

                        // Kolumna trzymająca nazwę, ikonę oraz cenę nagrody
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            // Ikona nagrody
                            Image(
                                modifier = Modifier
                                    .size(75.dp)
                                    .weight(1f),
                                painter = painterResource(images[it]),
                                contentDescription = null
                            )

                            // Tytuł nagrody
                            Text(text = titles[it], style = MaterialTheme.typography.h2, color = viewModel.fontColor)

                            Spacer(modifier = Modifier.width(2.dp))

                            // Cena nagrody
                            Text(modifier = Modifier
                                .background(
                                    color = colorResource(R.color.light_green),
                                    shape = RoundedCornerShape(size = 30.dp)),
                                    text = "ㅤprice: ${prices[it]}pㅤ", style = MaterialTheme.typography.h1, color = Black)

                            // Animacja wyświetlania się błedu
                            AnimatedContent(
                                targetState = error[it],
                                transitionSpec = {
                                    slideInHorizontally { width -> width } + fadeIn() with
                                            slideOutVertically { width -> -width } + fadeOut()
                                }
                            ){targetContent->
                                Text(
                                    text = targetContent,
                                    fontSize = 10.sp,
                                    style = MaterialTheme.typography.h1,
                                    color = Red
                                )
                            }
                        }
                    }
                }
            }
        }

        // Jeżeli masz wystarczjąco kredytów na nagrodę, wyświetl ekran potwierdzenia
        if (enoughCredit){

            popupFinalOffset = 0

            // Popup trzymający ekran potwierdzenia
            Popup(
                offset = IntOffset(0, popupOffset),
                alignment = Alignment.Center,
                properties = PopupProperties(focusable = true)
            ) {
                Box(
                    modifier = Modifier
                        .background(color = viewModel.backgroundColor)
                        .size(400.dp)
                        .border(width = 2.dp, color = viewModel.lines, shape = RoundedCornerShape(16.dp)),
                ) {

                    // Kolumna trzymająca przycisk potwierdzenia oraz wszystkie napisy
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        // Napis pytający czy na pewno chcesz kupić nagrodę
                        Text(modifier = Modifier
                                .padding(30.dp),
                            text = "Are you sure you want to buy this reward?",
                            style = MaterialTheme.typography.h1,
                            color = viewModel.fontColor, fontSize = 25.sp)

                        // Napis wyświetlający z jaką ilością kredytów zostaniesz po zakupie nagrody
                        Text(modifier = Modifier
                                .padding(30.dp),
                            text = "After the purchase you will be left with ${viewModel.credits - priceForPurchasePopup} points.",
                            style = MaterialTheme.typography.body1,
                            color = viewModel.fontColor, fontSize = 20.sp)

                        // Przycisk potwerdzający kupno nagrody
                        OutlinedButton(
                            onClick = {
                                popupFinalOffset = 1500
                                viewModel.credits -= priceForPurchasePopup
                                viewModel.creditsSpentAllTime += priceForPurchasePopup
                                enoughCredit = false
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
                                "Purchase",
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.h1,
                                color = viewModel.fontColor
                            )
                        }
                    }
                }
            }
        }

        // Kontener trzymający przycisk z dodawaniem nowej nagrody
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = viewModel.backgroundColor))
        {
            OutlinedButton(
                onClick = {
                    rewardCreating = true
                    blurRadius = 15
                          },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .padding(2.dp)
                    .align(Alignment.Center)
            ) {
                Text(text = "+ Add a reward", style = MaterialTheme.typography.h1, color = viewModel.fontColor)
            }
        }

        // Jeżeli kliknięto przycisk, wyświetl ekran tworzenia nagrody
        if (rewardCreating){

            popupFinalOffset = 0

                // Popup trzymający ekran tworzenia nowej nagrody
            Popup(
                offset = IntOffset(0, popupOffset),
                alignment = Alignment.Center,
                properties = PopupProperties(focusable = true)
            ) {
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

                        // Pole tekstowe nazywające nagrodę
                        OutlinedTextField(
                            modifier = Modifier
                                .width(370.dp)
                                .padding(3.dp),
                            value = rewardsTitleFieldState,
                            onValueChange = { rewardsTitleFieldState = it },
                            label = { Text("How will you reward yourself?") },
                            placeholder = { Text("Enter reward's title.") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Text),
                            keyboardActions = KeyboardActions(
                                onDone = {keyboardController?.hide()}),
                            shape = RoundedCornerShape(30.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = viewModel.focusableDefaultColor,
                                unfocusedLabelColor = viewModel.focusableDefaultColor,
                                focusedBorderColor = viewModel.focusableColor,
                                focusedLabelColor = viewModel.focusableColor,
                                placeholderColor = viewModel.focusableDefaultColor
                            )
                        )

                        // Pole tekstowe z kosztem nagrody
                        OutlinedTextField(
                            modifier = Modifier
                                .width(370.dp)
                                .padding(3.dp),
                            value = rewardsPriceFieldState,
                            onValueChange = { rewardsPriceFieldState = it },
                            label = { Text("What will be the cost?") },
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

                        Text(
                            text = "*optional*",
                            fontSize = 10.sp,
                            style = MaterialTheme.typography.h1,
                            color = viewModel.fontColor
                        )

                        // Przycisk wyboru aplikacji do odblokowania po kupienia nagrodu
                        OutlinedButton(
                            onClick = { appOrWebsiteBlocking = true },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                            shape = RoundedCornerShape(28.dp),
                        ) {
                            Text(
                                "Chose an app/website to be the reward",
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.body1,
                                color = viewModel.fontColor
                            )
                        }

                        // Jeżeli kliknięto przycisk wyboru aplikacji do odblokowania, wyświetl
                        // ekran odpowiadający temu oraz wybór czasu na ile chce się aplikację odblokować
                        if (appOrWebsiteBlocking) {
                            Spacer(modifier = Modifier.height(3.dp))

                            // Pole tekstowe z wyborem czasu na któy aplikacja będzie odblkowana
                            OutlinedTextField(
                                modifier = Modifier
                                    .width(200.dp)
                                    .padding(3.dp),
                                value = rewardsDurationFieldState,
                                onValueChange = { rewardsDurationFieldState = it },
                                label = { Text("Time the app/website will be unlocked for:") },
                                placeholder = { Text("Enter duration in hours.") },
                                shape = RoundedCornerShape(30.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    unfocusedBorderColor = viewModel.focusableDefaultColor,
                                    unfocusedLabelColor = viewModel.focusableDefaultColor,
                                    focusedBorderColor = viewModel.focusableColor,
                                    focusedLabelColor = viewModel.focusableColor,
                                    placeholderColor = viewModel.focusableDefaultColor
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(3.dp))

                        // Przycisk zatwierdzający tworzenie nowej nagrody
                        OutlinedButton(
                            onClick = {
                                popupFinalOffset = 1500
                                rewardCreating = false
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
                                color = viewModel.fontColor
                            )
                        }
                    }
                }
            }
        }

        Divider(color = viewModel.lines, thickness = 2.dp)

        // Kontener trzymający aktualną ilość posiadanych kredytów
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
