package com.example.musclepluscompose


import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musclepluscompose.data.AppViewModel
import com.example.musclepluscompose.data.Workout


@Composable
fun WorkoutScreen(viewModel: AppViewModel)
{
    val workouts by viewModel.allWorkout.collectAsState(emptyList())
    viewModel.insertWorkout(Workout(1, "test1", "voici un test"))
    viewModel.insertWorkout(Workout(2, "test2", "voici un test"))
    viewModel.insertWorkout(Workout(3, "test3", "voici un test"))
    viewModel.insertWorkout(Workout(4, "test4", "voici un test"))

    Column() {

        Text("Workouts:")
        LazyColumn{
            items(workouts){workout ->
                Text("${workout.name}: ${workout.desc}")
            }
        }
    }

}