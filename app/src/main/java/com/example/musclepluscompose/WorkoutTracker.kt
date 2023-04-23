package com.example.musclepluscompose

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.format.DateUtils.formatElapsedTime
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.musclepluscompose.data.AppDatabase
import com.example.musclepluscompose.ui.theme.MuscleBlue
import java.util.concurrent.TimeUnit

class WorkoutTracker : ComponentActivity() {

    // Remember the elapsed time as a state variable
    val elapsedTime = mutableStateOf("00:00")

    // Declare the broadcast receiver
    private val elapsedTimeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == TimerService.ACTION_UPDATE_ELAPSED_TIME) {
                // Update the elapsed time state variable
                val elapsedTimeMillis = intent.getLongExtra(TimerService.EXTRA_ELAPSED_TIME, 0L)
                elapsedTime.value = formatElapsedTime(elapsedTimeMillis)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val serviceIntent = Intent(LocalContext.current, TimerService::class.java)

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Elapsed Time: ${elapsedTime.value}",
                    style = MaterialTheme.typography.h4
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier.weight(1f)) {
                    WorkoutTrackerScreen()
                }
                Button(
                    onClick = {

                        // Stop the TimerService
                        stopService(serviceIntent)
                        finishWorkout()

                              },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MuscleBlue,
                        contentColor = MaterialTheme.colors.onPrimary // White
                    ),
                ) {
                    Text("Finish Workout")
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()

        // Start the TimerService only if it's not already running
        val serviceIntent = Intent(this, TimerService::class.java)
        if (!isServiceRunning(TimerService::class.java)) {
            ContextCompat.startForegroundService(this, serviceIntent)
        }

        // Register the broadcast receiver
        val filter = IntentFilter(TimerService.ACTION_UPDATE_ELAPSED_TIME)
        registerReceiver(elapsedTimeReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()

        // Unregister the broadcast receiver when the activity is finished
        unregisterReceiver(elapsedTimeReceiver)
    }

    private fun finishWorkout() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun formatElapsedTime(timeMillis: Long): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(timeMillis)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(timeMillis) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
}
