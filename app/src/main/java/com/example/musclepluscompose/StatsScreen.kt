package com.example.musclepluscompose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import com.example.musclepluscompose.ui.theme.MuscleBlue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.musclepluscompose.data.AppViewModel
import com.example.musclepluscompose.data.Exercise_Done
import com.example.musclepluscompose.data.Workout_Done

@Composable
fun StatsScreen(viewModel: AppViewModel) {

    val listExercises by viewModel.allExercise.collectAsState(emptyList())
    var expanded by remember { mutableStateOf(false) }
    var textFiledSize by remember { mutableStateOf(Size.Zero) }
    var selectedItem by remember { mutableStateOf("") }

    var expanded1 by remember { mutableStateOf(false) }
    var textFiledSize1 by remember { mutableStateOf(Size.Zero) }
    var selectedItem1 by remember { mutableStateOf("") }
    val listTimeLapse = listOf("Week", "1 Month", "3 month", "6 month", "Year")

    val icon1 = if (expanded1) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }


    //viewModel.upsertWorkout_Done(Workout_Done(1, java.util.Date(20230426), "yo"))
    //viewModel.upsertWorkout_Done(Workout_Done(1, java.util.Date(20230427), "yo"))
    //viewModel.upsertWorkout_Done(Workout_Done(1, java.util.Date(20230428), "yo"))
    //viewModel.upsertWorkout_Done(Workout_Done(1, java.util.Date(20230429), "yo"))

    //viewModel.upsertExercise_Done(Exercise_Done(1, 10, 100, 1))
    //viewModel.upsertExercise_Done(Exercise_Done(1, 10, 120, 2))
    //viewModel.upsertExercise_Done(Exercise_Done(1, 12, 130, 3))
    //viewModel.upsertExercise_Done(Exercise_Done(1, 15, 140, 4))

    val exercises = listOf<Exercise_Done>(
        Exercise_Done(1, 10, 100, 1),
        Exercise_Done(1, 10, 120, 1),
        Exercise_Done(1, 12, 130, 1),
        Exercise_Done(1, 15, 140, 1)

    )

    val workouts = listOf<Workout_Done>(
        Workout_Done(1, java.util.Date(20230426), "yo"),
        Workout_Done(1, java.util.Date(20230427), "yo"),
        Workout_Done(1, java.util.Date(20230428), "yo"),
        Workout_Done(1, java.util.Date(20230429), "yo")

    )

    val data = listOf(
        Pair("12/02", 20.0),
        Pair("13/02", 22.0),
        Pair("14/02", 25.0),
        Pair("15/02", 23.0),
        Pair("16/02", 24.0),
        Pair("17/02", 25.0),
        Pair("18/02", 27.0),
)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Column(modifier = Modifier.padding(20.dp, 10.dp)) {
            OutlinedTextField(
                value = selectedItem,
                onValueChange = { selectedItem = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        //This value is used to assign to the DropDown the same width
                        textFiledSize = coordinates.size.toSize()
                    },
                label = { Text("Choose exercise") },
                trailingIcon = {
                    Icon(icon, "contentDescription",
                        Modifier.clickable { expanded = !expanded })
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textFiledSize.width.toDp() })
            ) {
                listExercises.forEach { label ->
                    DropdownMenuItem(onClick = {
                        selectedItem = label.name
                        expanded = false
                    }) {
                        Text(text = label.name)
                    }
                }

            }
        }

        Column(modifier = Modifier.padding(20.dp, 0.dp)) {
            OutlinedTextField(
                value = selectedItem1,
                onValueChange = { selectedItem1 = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        //This value is used to assign to the DropDown the same width
                        textFiledSize1 = coordinates.size.toSize()
                    },
                label = { Text("Time period") },
                trailingIcon = {
                    Icon(icon1, "contentDescription",
                        Modifier.clickable { expanded1 = !expanded1 })
                }
            )
            DropdownMenu(
                expanded = expanded1,
                onDismissRequest = { expanded1 = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textFiledSize1.width.toDp() })
            ) {
                listTimeLapse.forEach { label ->
                    DropdownMenuItem(onClick = {
                        selectedItem1 = label
                        expanded1 = false
                    }) {
                        Text(text = label)
                    }
                }

            }

        }

        LineChart(data,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(CenterHorizontally))

    }
}

@Preview
@Composable
fun StatsScreenPreview() {
    //StatsScreen()
}