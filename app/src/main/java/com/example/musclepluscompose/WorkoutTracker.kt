package com.example.musclepluscompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.musclepluscompose.data.AppDatabase
import com.example.musclepluscompose.data.workoutModel.WorkoutViewModel

class WorkoutTracker : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "workout.db"
        ).build()
    }

    private val workoutViewModel by viewModels<WorkoutViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>) : T{
                    return WorkoutViewModel(db.dao) as T
                }
            }
        }
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutTrackerScreen()
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                    Button(onClick = { FinishWorkout() }) {
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