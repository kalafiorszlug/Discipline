package com.example.discipline

import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {

    var credits = 0
    var creditsAllTime = 0
    var creditsSpentAllTime = 0

    var userName = ""

    var streak = 0
    var bestStreak = 0

    var completedTasks = 0

}