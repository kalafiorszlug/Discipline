package com.example.discipline

import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import java.util.Calendar

class SharedViewModel: ViewModel() {

    var credits = 0
    var creditsAllTime = 0
    var creditsSpentAllTime = 0

    val calendar = Calendar.getInstance()
    val dayOfTheWeek = (calendar[Calendar.DAY_OF_WEEK])
    val tasksThroughoutTheWeek = mutableListOf(0, 0, 0, 0, 0, 0, 0)

    var tasksTitles = mutableStateListOf("")
    var tasksPayoff = mutableListOf(0)
    var tasksDeadlines = mutableListOf("-")
    var tasksListBelonging = mutableListOf("")

    var tasksLists = mutableListOf(NavigationItem(title = "Tasks", badgeCount = tasksTitles.size))
    var tasksOrderCriteria: String? = null

    var userName = "name"

    var streak = 0
    var bestStreak = 0

    var completedTasks = 0

    var registering = false

    var currentSetting = "theme"

    var rewards = mutableListOf<Reward>()
    var rewardsIcons = mutableListOf<Drawable?>()
    var mainScreenRewardsIndex = 0

    var isUserLoggedIn = "no"

    //kolorystyka
    var backgroundColor = Color.DarkGray
    var fontColor = Color.White
    var lines = Color.White
    var todoButtonActivatedColor = Color.White
    var focusableDefaultColor = Color.LightGray
    var focusableColor = Color.White
}