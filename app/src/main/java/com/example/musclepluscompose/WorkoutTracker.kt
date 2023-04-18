package com.example.musclepluscompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.musclepluscompose.data.AppDatabase
import com.example.musclepluscompose.ui.theme.MuscleBlue

class WorkoutTracker : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SelectWorkoutScreen()
            //WorkoutTrackerScreen()
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                    Button(
                        onClick = { FinishWorkout() },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MuscleBlue,
                            contentColor = MaterialTheme.colors.onPrimary // White
                        ),
                    )
                    {
                        Text("Finish Workout")
                    }
                }
            }
        }
    }

    private fun FinishWorkout() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
        }
    }
}