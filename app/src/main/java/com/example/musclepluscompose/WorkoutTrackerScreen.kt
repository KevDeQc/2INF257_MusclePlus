package com.example.musclepluscompose

import android.content.Intent
import android.graphics.Color
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WorkoutTrackerScreen() {

    var elapsedTimeInSeconds by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    fun formatElapsedTime(elapsedTimeInSeconds: Int): String {
        val minutes = elapsedTimeInSeconds / 60
        val seconds = elapsedTimeInSeconds % 60
        return "%02d:%02d".format(minutes, seconds)
    }

    DisposableEffect(scope) {
        onDispose {
            scope.cancel()
        }
    }

    LaunchedEffect(true) {
        while (true) {
            delay(1000)
            elapsedTimeInSeconds += 1
        }
    }


    // Debugging
    var exerciseList by remember { mutableStateOf(mutableListOf(
        WorkoutTrackerExerciseList(
            id = 1,
            name = "Bench Press",
            exerciseItems = mutableListOf(
                WorkoutTrackerExerciseItem(weight = 100, rep = 10),
                WorkoutTrackerExerciseItem(weight = 110, rep = 8),
                WorkoutTrackerExerciseItem(weight = 120, rep = 6)
            )
        ),
        WorkoutTrackerExerciseList(
            id = 2,
            name = "Squat",
            exerciseItems = mutableListOf(
                WorkoutTrackerExerciseItem(weight = 120, rep = 10),
                WorkoutTrackerExerciseItem(weight = 130, rep = 8),
                WorkoutTrackerExerciseItem(weight = 140, rep = 6)
            )
        )
    )) }

    fun setExerciseList(newList: MutableList<WorkoutTrackerExerciseList>) {
        exerciseList = newList
    }

    fun addItem(indexExercise: Int, weight: Int, rep: Int) {
        val exercise = exerciseList[indexExercise]
        val newExerciseItems = exercise.exerciseItems.toMutableList()
        newExerciseItems.add(WorkoutTrackerExerciseItem(weight = weight, rep = rep))
        val newExerciseList = exerciseList.toMutableList()
        newExerciseList[indexExercise] = exercise.copy(exerciseItems = newExerciseItems)
        setExerciseList(newExerciseList)
    }

    //////////////

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .size(
            width = 300.dp,
            height = 2000.dp
        )) {
        item {
            Text(
                text = "Timer: ${formatElapsedTime(elapsedTimeInSeconds)}",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
        }
        items(exerciseList.size) { indexExercise ->
            val exercise = exerciseList[indexExercise]
            Text(
                exercise.name,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            LazyColumn(modifier = Modifier.heightIn(max = 1000.dp)) {
                items(exercise.exerciseItems.size){ indexSet ->
                    val sets = exercise.exerciseItems[indexSet]
                    Row {
                        TextField(
                            value = sets.rep.takeIf { it > 0 }?.toString() ?: "",
                            onValueChange = { newValue ->
                                val newSets = sets.copy(rep = newValue.takeIf { it.isNotEmpty() }?.toIntOrNull() ?: 0)
                                val newExerciseItems = exercise.exerciseItems.toMutableList()
                                newExerciseItems[indexSet] = newSets
                                val newExerciseList = exerciseList.toMutableList()
                                newExerciseList[indexExercise] = exercise.copy(exerciseItems = newExerciseItems)
                                setExerciseList(newExerciseList)
                            },
                            label = { Text("Rep") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(Modifier.width(8.dp))
                        TextField(
                            value = sets.weight.takeIf { it > 0 }?.toString() ?: "",
                            onValueChange = { newValue ->
                                val newSets = sets.copy(weight = newValue.takeIf { it.isNotEmpty() }?.toIntOrNull() ?: 0)
                                val newExerciseItems = exercise.exerciseItems.toMutableList()
                                newExerciseItems[indexSet] = newSets
                                val newExerciseList = exerciseList.toMutableList()
                                newExerciseList[indexExercise] = exercise.copy(exerciseItems = newExerciseItems)
                                setExerciseList(newExerciseList)
                            },
                            label = { Text("Weight") },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Button(onClick = {
                addItem(indexExercise, weight = 0, rep = 0)
            }) {
                Text("Add Set")
            }
        }
        item {
            Text(
                text = "Commentaire",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        item {
            TextField(
                value = "",
                onValueChange = { /* handle value change */ },
                label = { Text("Commentaire") },
            )
        }
    }
}
