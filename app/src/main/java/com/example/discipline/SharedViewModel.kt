package com.example.discipline

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import java.util.*

class SharedViewModel: ViewModel() {

    var credits = 0
    var creditsAllTime = 0
    var creditsSpentAllTime = 0

    val calendar = Calendar.getInstance()
    val dayOfTheWeek = (calendar[Calendar.DAY_OF_WEEK])
    val tasksThroughoutTheWeek = mutableListOf(0, 0, 0, 0, 0, 0, 0)

    var tasksTitles = mutableListOf("writing essay", "working out", "meditating", "studying", "programming", "doing homework", "running a marathon", "uranium consumption")
    var tasksPayoff = mutableListOf(15, 20, 30, 25, 20, 25, 10, 15)
    var tasksDeadlines = mutableListOf("27.9.2023", "4.9.2023", "-", "-", "-", "30.9.2023", "11.10.2023", "-")

    var userName = "name"

    var streak = 0
    var bestStreak = 0

    var completedTasks = 0

    var registering = false

    //kolorystyka
    var theme = "dark"
    var backgroundColor = Color.DarkGray
    var fontColor = Color.White
    var lines = Color.White
    var todoButtonColor = Color.DarkGray
    var todoButtonActivatedColor = Color.White
    var focusableDefaultColor = Color.LightGray
    var focusableColor = Color.White
}