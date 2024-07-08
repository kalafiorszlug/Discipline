package com.example.discipline

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import com.example.myapp.MessageActivity

class MyAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.let {
            val packageName = it.packageName?.toString()
            val eventType = it.eventType

            if (packageName == "com.google.android.youtube") {
                Log.d("Wykryto blokowaną apkę", "Bang! ")
                if (eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                    // Display a message
                    val intent = Intent(this, MessageActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onInterrupt() {
        // Handle interruption
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Toast.makeText(this, "You gave Discipline required permissions", Toast.LENGTH_SHORT).show()
    }
}