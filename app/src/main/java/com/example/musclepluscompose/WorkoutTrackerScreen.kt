package com.example.musclepluscompose

import android.graphics.Color
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WorkoutTrackerScreen() {

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
        items(exerciseList.size) { indexExercise ->
            val exercise = exerciseList[indexExercise]
            Text(exercise.name)

            LazyColumn(modifier = Modifier.heightIn(max = 200.dp)) {
                items(exercise.exerciseItems.size){ indexSet ->
                    val sets = exercise.exerciseItems[indexSet]
                    Row {
                        TextField(
                            value = sets.rep.toString(),
                            onValueChange = { /* TODO */ },
                            label = { Text("Rep") },
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(Modifier.width(8.dp))
                        TextField(
                            value = sets.weight.toString(),
                            onValueChange = { /* TODO */ },
                            label = { Text("Weight") },
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
    }
}
