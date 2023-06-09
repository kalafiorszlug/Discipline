package com.example.discipline


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.discipline.ui.theme.DisciplineTheme

enum class DisciplineScreen() {
    MainScreen,
    LoginScreen,
    StatScreen,
    RewardScreen,
    TaskScreen
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DisciplineTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    AppNavigator()
                }
            }
        }
    }
}

@Composable
fun AppNavigator(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = DisciplineScreen.LoginScreen.name
    ){
        composable(route = DisciplineScreen.LoginScreen.name){ LoginScreen(navController = navController)}
        composable(route = DisciplineScreen.MainScreen.name){ MainScreen(navController = navController)}
        composable(route = DisciplineScreen.StatScreen.name){ StatScreen()}
        composable(route = DisciplineScreen.RewardScreen.name){ RewardScreen()}
        composable(route = DisciplineScreen.TaskScreen.name){ TaskScreen()}
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
  Surface(color = Color.LightGray) {
      DisciplineTheme {
          AppNavigator()
      }
  }
}