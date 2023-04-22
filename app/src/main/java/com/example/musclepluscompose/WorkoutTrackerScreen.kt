package com.example.musclepluscompose

import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.registerReceiver
import androidx.core.content.ContextCompat.startActivity
import com.example.musclepluscompose.data.AppViewModel
import com.example.musclepluscompose.ui.theme.MuscleBlue
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import java.util.Timer

@OptIn(ExperimentalFoundationApi::class)

@Composable
fun SelectWorkoutScreen()
{

    var isWorkingOut by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Item 1") }
    val items = listOf("Item 1", "Item 2", "Item 3", "Item 4")
    //viewModel: AppViewModel
    //val allWorkouts by viewModel.allWorkout.collectAsState(emptyList())

    if(isWorkingOut)
    {
        WorkoutTrackerScreen()
    }
    else
    {
        Column {
            Text(
                text = "Workout: $selectedItem",
                modifier = Modifier.padding(16.dp)
            )

            Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                Button(
                    onClick = { expanded = true },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MuscleBlue,
                        contentColor = MaterialTheme.colors.onPrimary // White
                    ),
                )
                {
                    Text(text = "Select a workout")
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.width(IntrinsicSize.Max)
                ) {
                    items.forEach { item ->
                        DropdownMenuItem(onClick = {
                            selectedItem = item
                            expanded = false
                        }) {
                            Text(text = item)
                        }
                    }
                }

                Row{
                    Button(
                        onClick = { isWorkingOut = true },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MuscleBlue,
                            contentColor = MaterialTheme.colors.onPrimary // White
                        ),
                    )
                    {
                        Text(text = "GO")
                    }
                }
            }
        }
    }

}

@Composable
fun WorkoutTrackerScreen() { // WE WILL MAKE IT SO IT PASS A WORKOUT AS PARAMETER TO DISPLAY EACH OF HIS EXERCICES

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

            Button(
                onClick = { addItem(indexExercise, weight = 0, rep = 0) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MuscleBlue,
                    contentColor = MaterialTheme.colors.onPrimary // White
                )
            )
            {
                Text("Add Set")
            }
        }
        item {
            Text(
                text = "Comment",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        item {
            TextField(
                value = "",
                onValueChange = { /* handle value change */ },
                label = { Text("Comment") },
                modifier = Modifier.padding(bottom = 100.dp)
            )
        }
    }
}
