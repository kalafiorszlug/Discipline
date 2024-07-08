package com.example.discipline

//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import com.example.datastore.data.UserStore
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

data class AppInfo(
    val appName: String,
    val packageName: String,
    val icon: Drawable
)

data class Reward(
    val title: String,
    val cost: Int,
    var errorMessage: String = "",
    var unlockDuration: Int?,
    var appName: String?,
    var websiteUrl: String?
)

fun getInstalledApps(packageManager: PackageManager): List<AppInfo> {
    val apps = mutableListOf<AppInfo>()
    val packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

    for (packageInfo in packages) {
        // Check if the app is launchable
        if (packageManager.getLaunchIntentForPackage(packageInfo.packageName) != null) {
            val appName = packageManager.getApplicationLabel(packageInfo).toString()
            val packageName = packageInfo.packageName
            val icon = packageManager.getApplicationIcon(packageInfo)
            apps.add(AppInfo(appName, packageName, icon))
        }
    }
    return apps
}

@SuppressLint("MutableCollectionMutableState", "UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun RewardScreen(navController: NavHostController, viewModel: SharedViewModel, packageManager: PackageManager) {

    val context = LocalContext.current
    val store = UserStore(context)

    // Zmienne odpowiadające za dostępne nagrody
    var rewardCreating by remember { mutableStateOf(false) }
    var appChoosing by remember { mutableStateOf(false) }
    var websiteChoosing by remember { mutableStateOf(false) }
    var timeChoosing by remember { mutableStateOf(false) }
    var rewardsTitleFieldState by remember { mutableStateOf("") }
    var rewardsPriceFieldState by remember { mutableStateOf("") }
    var rewardsLinkedWebsiteUrl by remember { mutableStateOf("") }
    var rewardsDurationFieldState by remember { mutableStateOf("") }
    var rewardsLinkedApp: AppInfo? by remember { mutableStateOf(null) }
    var icons by remember { mutableStateOf(viewModel.rewardsIcons) }
    var blurRadius by remember { mutableIntStateOf(0) }

    var rewards by remember {
        mutableStateOf(viewModel.rewards)
    }

    if (viewModel.rewards.size > 0){
        viewModel.mainScreenRewardsIndex = Random.nextInt(0, viewModel.rewards.size)
    }

    var displayError by remember { mutableStateOf(false) }

    var pushIterator = 0

    var enoughCredit by remember { mutableStateOf(false) }
    var priceForPurchasePopup by remember { mutableStateOf(0) }

    var popupFinalOffset by remember { mutableStateOf(2000) }
    val keyboardController = LocalSoftwareKeyboardController.current

    var appList by remember { mutableStateOf(emptyList<AppInfo>()) }
    LaunchedEffect(Unit) {
        appList = getInstalledApps(packageManager)
    }

    var deleteRewards by remember { mutableStateOf(false) }
    var deleteRewardsMessage by remember { mutableStateOf("") }

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

    if (deleteRewards){
        deleteRewardsMessage = "Tap on a reward to delete it"
    } else {
        deleteRewardsMessage = ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = viewModel.backgroundColor,
                title = {
                    Column (
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Row {
                            Text(
                                text = "Rewards",
                                color = viewModel.fontColor
                            )
                            
                            Spacer(modifier = Modifier.width(20.dp))
                        }

                        AnimatedContent(
                            targetState = deleteRewardsMessage,
                            transitionSpec = {
                                slideInHorizontally { width -> width } + fadeIn() with
                                        slideOutVertically { width -> -width } + fadeOut()
                            }
                        ){targetContent->
                            Row {
                                Text(
                                    text = targetContent,
                                    fontSize = 10.sp,
                                    style = MaterialTheme.typography.h1,
                                    color = Color.Red
                                )
                                
                                Spacer(modifier = Modifier.width(20.dp))
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(route = DisciplineScreen.MainScreen.name)
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            deleteRewards = !deleteRewards
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }
            )
        },
        content = {
            if (!appChoosing){
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
                            verticalArrangement = Arrangement.Top
                        ) {
                            if (rewards.size > 0){
                                repeat(rewards.size){

                                    // Przycisk odpowiadający danej nagrodzie
                                    OutlinedButton(
                                        onClick = {
                                            if (deleteRewards){
                                                viewModel.rewards.removeAt(it)
                                                rewards = viewModel.rewards

                                                viewModel.rewardsIcons.removeAt(it)
                                                icons = viewModel.rewardsIcons

                                                CoroutineScope(Dispatchers.IO).launch {
                                                    store.saveRewards(viewModel.rewards)
                                                }

                                                navController.navigate(route = DisciplineScreen.RewardScreen.name)

                                            } else {
                                                if (viewModel.credits >= rewards[it].cost){
                                                    blurRadius = 15
                                                    enoughCredit = true
                                                    priceForPurchasePopup = rewards[it].cost
                                                } else {
                                                    pushIterator = it
                                                    displayError = true
                                                }
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                                        shape = RoundedCornerShape(28.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(120.dp)
                                            .padding(3.dp)
                                            .border(
                                                1.dp,
                                                color = Color.Gray,
                                                shape = RoundedCornerShape(28.dp)
                                            )
                                    ) {

                                        // Jeżeli użytkownikowi brakuje punktów, wyświetl błąd
                                        if(displayError){
                                            rewards[pushIterator].errorMessage = "You can't afford this."
                                            displayError = false
                                        }

                                        // Kolumna trzymająca nazwę, ikonę oraz cenę nagrody
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {

                                            Spacer(modifier = Modifier.weight(1f))

                                            if (icons.size > 0){
                                                if (icons[it] != null){
                                                    // Ikona nagrody
                                                    Image(
                                                        modifier = Modifier
                                                            .size(45.dp)
                                                            .padding(3.dp),
                                                        painter = rememberDrawablePainter(drawable = icons[it]),
                                                        contentDescription = null
                                                    )
                                                }
                                            }

                                            Spacer(modifier = Modifier.width(10.dp))

                                            // Tytuł nagrody
                                            Text(
                                                modifier = Modifier
                                                    .padding(3.dp),
                                                text = rewards[it].title,
                                                style = MaterialTheme.typography.h2,
                                                color = viewModel.fontColor
                                            )

                                            Spacer(modifier = Modifier.weight(1f))

                                            // Cena nagrody
                                            Text(modifier = Modifier
                                                .background(
                                                    color = colorResource(R.color.light_green),
                                                    shape = RoundedCornerShape(size = 30.dp)
                                                ),
                                                text = "ㅤprice: ${rewards[it].cost}pㅤ", style = MaterialTheme.typography.h1, color = Color.Black
                                            )

                                            Spacer(modifier = Modifier.width(10.dp))

                                            // Animacja wyświetlania się błedu
                                            AnimatedContent(
                                                targetState = rewards[it].errorMessage,
                                                transitionSpec = {
                                                    slideInVertically{ width -> width } + fadeIn() with
                                                            slideOutVertically { width -> -width } + fadeOut()
                                                }
                                            ){targetContent->
                                                Text(
                                                    text = targetContent,
                                                    fontSize = 10.sp,
                                                    style = MaterialTheme.typography.h1,
                                                    color = Color.Red
                                                )
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                }
                            } else {
                                Text(
                                    modifier = Modifier
                                        .padding(15.dp),
                                    text = "No rewards to display",
                                    style = MaterialTheme.typography.h2,
                                    color = viewModel.fontColor
                                )
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
                                    .border(
                                        width = 2.dp,
                                        color = viewModel.lines,
                                        shape = RoundedCornerShape(16.dp)
                                    ),
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
                                            CoroutineScope(Dispatchers.IO).launch {
                                                store.saveCredits(viewModel.credits.toString())
                                            }
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
                                    .size(500.dp)
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
                                    Spacer(modifier = Modifier.weight(1f))

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
                                            textColor = viewModel.fontColor,
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
                                            textColor = viewModel.fontColor,
                                            unfocusedBorderColor = viewModel.focusableDefaultColor,
                                            unfocusedLabelColor = viewModel.focusableDefaultColor,
                                            focusedBorderColor = viewModel.focusableColor,
                                            focusedLabelColor = viewModel.focusableColor,
                                            placeholderColor = viewModel.focusableDefaultColor
                                        )
                                    )

                                    Spacer(modifier = Modifier.weight(1f))

                                    Text(
                                        text = "*optional*",
                                        fontSize = 10.sp,
                                        style = MaterialTheme.typography.h1,
                                        color = viewModel.fontColor
                                    )

                                    // Przycisk wyboru aplikacji do odblokowania po kupienia nagrodu
                                    OutlinedButton(
                                        onClick = {
                                            appChoosing = true
                                            timeChoosing = true
                                        },
                                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                                        shape = RoundedCornerShape(28.dp),
                                    ) {
                                        Text(
                                            "Link with an app",
                                            fontSize = 16.sp,
                                            style = MaterialTheme.typography.body1,
                                            color = viewModel.fontColor
                                        )
                                    }

                                    OutlinedButton(
                                        onClick = {
                                            websiteChoosing = true
                                            timeChoosing = true
                                        },
                                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                                        shape = RoundedCornerShape(28.dp),
                                    ) {
                                        Text(
                                            "Link with a website",
                                            fontSize = 16.sp,
                                            style = MaterialTheme.typography.body1,
                                            color = viewModel.fontColor
                                        )
                                    }

                                    if (websiteChoosing){
                                        OutlinedTextField(
                                            modifier = Modifier
                                                .width(370.dp)
                                                .padding(3.dp),
                                            value = rewardsLinkedWebsiteUrl,
                                            onValueChange = { rewardsLinkedWebsiteUrl = it },
                                            label = { Text("Put website's URL here:") },
                                            placeholder = { Text("eg.: youtube.com") },
                                            shape = RoundedCornerShape(30.dp),
                                            singleLine = true,
                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                                textColor = viewModel.fontColor,
                                                unfocusedBorderColor = viewModel.focusableDefaultColor,
                                                unfocusedLabelColor = viewModel.focusableDefaultColor,
                                                focusedBorderColor = viewModel.focusableColor,
                                                focusedLabelColor = viewModel.focusableColor,
                                                placeholderColor = viewModel.focusableDefaultColor
                                            )
                                        )
                                    }

                                    // Jeżeli kliknięto przycisk wyboru aplikacji do odblokowania, wyświetl
                                    // ekran odpowiadający temu oraz wybór czasu na ile chce się aplikację odblokować
                                    if (timeChoosing) {

                                        Spacer(modifier = Modifier.height(3.dp))

                                        // Pole tekstowe z wyborem czasu na któy aplikacja będzie odblkowana
                                        OutlinedTextField(
                                            modifier = Modifier
                                                .width(370.dp)
                                                .padding(3.dp),
                                            value = rewardsDurationFieldState,
                                            onValueChange = { rewardsDurationFieldState = it },
                                            label = { Text("Time the app/website will be unlocked for:") },
                                            placeholder = { Text("Enter duration in hours.") },
                                            shape = RoundedCornerShape(30.dp),
                                            singleLine = true,
                                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                                textColor = viewModel.fontColor,
                                                unfocusedBorderColor = viewModel.focusableDefaultColor,
                                                unfocusedLabelColor = viewModel.focusableDefaultColor,
                                                focusedBorderColor = viewModel.focusableColor,
                                                focusedLabelColor = viewModel.focusableColor,
                                                placeholderColor = viewModel.focusableDefaultColor
                                            )
                                        )
                                    }

                                    Spacer(modifier = Modifier.weight(1f))

                                    // Przycisk zatwierdzający tworzenie nowej nagrody
                                    OutlinedButton(
                                        onClick = {
                                            viewModel.rewards += Reward(
                                                cost = rewardsPriceFieldState.toInt(),
                                                title = rewardsTitleFieldState,
                                                unlockDuration = if (rewardsDurationFieldState != ""){
                                                    rewardsDurationFieldState.toInt()
                                                } else {
                                                    null
                                                },
                                                appName = rewardsLinkedApp?.appName,
                                                websiteUrl = rewardsLinkedWebsiteUrl
                                            )
                                            rewards = viewModel.rewards

                                            if (rewardsLinkedApp != null){
                                                viewModel.rewardsIcons += null
                                                viewModel.rewardsIcons[rewards.size - 1] = rewardsLinkedApp!!.icon
                                            } else {
                                                viewModel.rewardsIcons += null
                                            }

                                            icons = viewModel.rewardsIcons

                                            CoroutineScope(Dispatchers.IO).launch {
                                                store.saveRewards(viewModel.rewards)
                                            }

                                            rewardsPriceFieldState = ""
                                            rewardsTitleFieldState = ""
                                            rewardsDurationFieldState = ""
                                            websiteChoosing = false
                                            rewardsLinkedApp = null

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

                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }

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
                                    text = "Credit: ${creditCounter}",
                                    fontSize = 30.sp,
                                    style = MaterialTheme.typography.h2,
                                    color = viewModel.fontColor
                                )
                            }
                        }
                    }
                }
            } else {
                LazyColumn {
                    items(items = appList) { app ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    rewardsLinkedApp = app
                                    appChoosing = false
                                }
                        ) {
                            Image(
                                bitmap = app.icon.toBitmap().asImageBitmap(),
                                contentDescription = app.appName,
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(text = app.appName)
                                Text(text = app.packageName)
                            }
                        }
                    }
                }
            }
        }
    )
}


