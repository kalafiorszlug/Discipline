package com.example.myapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.discipline.SharedViewModel
import com.example.discipline.ui.theme.DisciplineTheme

class MessageActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DisciplineTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MessageScreen(viewModel = SharedViewModel())
                }
            }
        }
    }
}

@Composable
fun MessageScreen(viewModel: SharedViewModel) {
    val context = LocalContext.current

    val intent = Intent(Intent.ACTION_MAIN)
    var goHome by remember { mutableStateOf(false) }

    if (goHome){
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        ContextCompat.startActivity(context, intent, null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(viewModel.backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // TODO * Miejsce na logo
        Text(
            modifier = Modifier.padding(20.dp),
            text = "Blocked by Discipline",
            style = androidx.compose.material.MaterialTheme.typography.h1,
            color = viewModel.fontColor,
            fontSize = 50.sp
        )

        OutlinedButton(
            onClick = {
                      goHome = true
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .padding(2.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "go back", style = androidx.compose.material.MaterialTheme.typography.h1, color = viewModel.fontColor)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DisciplineTheme {
        MessageScreen(viewModel = SharedViewModel())
    }
}