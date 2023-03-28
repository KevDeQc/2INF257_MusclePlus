package com.example.musclepluscompose


import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
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
    viewModel.insertWorkout(Workout(5, "test5", "voici un test"))
    viewModel.insertWorkout(Workout(6, "test6", "voici un test"))
    viewModel.insertWorkout(Workout(7, "test7", "voici un test"))
    viewModel.insertWorkout(Workout(8, "test8", "voici un test"))
    viewModel.insertWorkout(Workout(9, "test9", "voici un test"))
    viewModel.insertWorkout(Workout(10, "test10", "voici un test"))
    viewModel.insertWorkout(Workout(11, "tes11", "voici un test"))
    viewModel.insertWorkout(Workout(12, "test12", "voici un test"))

    Box(modifier = Modifier
        .fillMaxSize(),
    ){
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ){
            items(workouts){workout ->
                WorkoutItem(workout)
                //Text("${workout.name}: ${workout.desc}")
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .padding(all = 16.dp)
                .align(alignment = Alignment.BottomEnd),
            onClick = { viewModel.insertWorkout(Workout(13, "test13", "voici un test"))}) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
        }
    }

}

@Composable
fun WorkoutItem(workout : Workout){

    Row(
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(rememberScrollState())
            .border(2.dp, Color.LightGray)
    ) {
        Icon(modifier = Modifier.align(alignment = Alignment.CenterVertically), imageVector = Icons.Filled.Add, contentDescription = "add")
        Column() {
            Text(workout.name, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
            Text(workout.desc)
        }

    }

}