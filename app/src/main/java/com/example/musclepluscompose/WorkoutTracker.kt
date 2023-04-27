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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.musclepluscompose.data.AppDatabase
import com.example.musclepluscompose.ui.theme.MuscleBlue
import java.util.concurrent.TimeUnit
import com.example.musclepluscompose.data.AppViewModel
import com.example.musclepluscompose.data.Exercise_Done
import com.example.musclepluscompose.data.Workout
import com.example.musclepluscompose.data.Workout_Done
import kotlinx.coroutines.delay
import java.util.Date

class WorkoutTracker : ComponentActivity() {

    private val viewModel by viewModels<AppViewModel>()

    // Remember the elapsed time as a state variable
    private val elapsedTime = mutableStateOf("00:00")
    private val elapsedTimeMillis = mutableStateOf(0L)

    // Declare the broadcast receiver
    private val elapsedTimeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == TimerService.ACTION_UPDATE_ELAPSED_TIME) {
                // Update the elapsed time state variable
                elapsedTimeMillis.value = intent.getLongExtra(TimerService.EXTRA_ELAPSED_TIME, 0L)
                elapsedTime.value = formatElapsedTime(elapsedTimeMillis.value)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val serviceIntent = Intent(LocalContext.current, TimerService::class.java)
            val workoutID = intent.getIntExtra("WorkoutID", 0)

            val workout: Workout = viewModel.findWorkout(workoutID)

            var commentValue: String by remember {
                mutableStateOf("")
            }

            var exerciseListValue: List<WorkoutTrackerExerciseList> by remember {
                mutableStateOf(emptyList())
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Elapsed Time: ${elapsedTime.value}",
                    style = MaterialTheme.typography.h4
                )
                Text(text = "Workout in Progress: ${workout.name}")
                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier.weight(1f)) {
                    WorkoutTrackerScreen(workout, updateWorkoutTrackerData = { comment, exerciseList ->
                        commentValue = comment
                        exerciseListValue = exerciseList
                    })
                }
                Button(
                    onClick = {

                        // Stop the TimerService
                        stopService(serviceIntent)
                        println(commentValue)
                        finishWorkout(commentValue, exerciseListValue, workoutID, elapsedTimeMillis.value)

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

    private fun finishWorkout(comment: String, exerciseList: List<WorkoutTrackerExerciseList>, workoutID: Int, elapsedTimeMillis: Long) {

        val workoutDoneId = viewModel.upsertWorkout_Done(Workout_Done(workout_id = workoutID, date = Date(), comment = comment, time = elapsedTimeMillis))

        //val workoutDoneId = viewModel.getLastWorkoutDone()

        exerciseList.forEach(){
            var exId = it.id
            
            it.exerciseItems.forEach(){
                viewModel.upsertExercise_Done(Exercise_Done(exercise_id = exId, rep = it.rep, weight = it.weight, workout_done_id = workoutDoneId))
            }
        }

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
