package com.example.discipline

import androidx.lifecycle.ViewModel
import java.util.*

class SharedViewModel: ViewModel() {

    var credits = 0
    var creditsAllTime = 0
    var creditsSpentAllTime = 0

    val calendar = Calendar.getInstance()
    val dayOfTheWeek = (calendar[Calendar.DAY_OF_WEEK])
    val tasksThroughoutTheWeek = mutableListOf(0, 0, 0, 0, 0, 0, 0)

    var tasksTitles = mutableListOf("Masturbacja", "Prześladowanie kobiet", "Ludobójstwo", "Lizanie chodnika", "Libacja alkoholowa", "Nazistowski salut", "Komunistyczny wiec", "Konsumpcja Uranu")
    var tasksPayoff = mutableListOf(15, 20, 30, 25, 20, 25, 10, 15)
    var tasksDeadlines = mutableListOf("27/8/2023", "1.1.2024", "-", "-", "-", "30.9.2023", "11.10.2023", "-")
    var numberOfTasks = 5

    var userName = ""

    var streak = 0
    var bestStreak = 0

    var completedTasks = 0

}