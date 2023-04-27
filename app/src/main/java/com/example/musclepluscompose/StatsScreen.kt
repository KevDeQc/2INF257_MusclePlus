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
import com.example.musclepluscompose.data.*
import java.time.Instant
import java.util.Calendar
import java.util.Date

data class DataPoint(val x: Float, val y: Float)

/*
private fun ConvertData(viewModel: AppViewModel, exerciseList: List<Exercise_Done>, timeScopeInDay : Int) : List<DataPoint>{

    //get all exerciseDone in  a time scope

    var resultList : MutableList<DataPoint>

}

 */

@Composable
fun StatsScreen(viewModel: AppViewModel) {

    val listExercises by viewModel.allExercise.collectAsState(emptyList())
    var selectedExercise by remember { mutableStateOf<Exercise?>(null)}

    var expanded by remember { mutableStateOf(false) }
    var textFiledSize by remember { mutableStateOf(Size.Zero) }
    var selectedItem by remember { mutableStateOf("") }

    var expanded1 by remember { mutableStateOf(false) }
    var textFiledSize1 by remember { mutableStateOf(Size.Zero) }
    //var selectedItem1 by remember { mutableStateOf("") }
    val listTimeLapse = listOf("Week", "1 Month", "3 month", "6 month", "Year")
    var selectedTimeLapse by remember { mutableStateOf("") }

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

    val exercise1 = Exercise("Test", "ceci est un test", 1)
    viewModel.upsertExercise(exercise1)
    viewModel.upsertWorkout(Workout("WorkoutTest", "ceci est un test", mutableListOf(exercise1, exercise1, exercise1), 1))
    viewModel.upsertWorkout_Done(Workout_Done(1, , "no comment", 1))
    viewModel.upsertExercise_Done(Exercise_Done(1, 10, 100, 1))

    viewModel.upsertWorkout_Done(Workout_Done(1, Date(), "no comment", 2))
    viewModel.upsertExercise_Done(Exercise_Done(1, 10, 100, 2))

    viewModel.upsertWorkout_Done(Workout_Done(1, Date((Date.from(Instant.now()).time - 432000)), "no comment", 2))
    viewModel.upsertExercise_Done(Exercise_Done(1, 10, 100, 3))

    viewModel.upsertWorkout_Done(Workout_Done(1, Date((Date.from(Instant.now()).time - 864000)), "no comment", 2))
    viewModel.upsertExercise_Done(Exercise_Done(1, 10, 100, 4))


    val test = viewModel.getAllExerciseDoneInTimeById(1, 7)

    val dataPoints = listOf(
        DataPoint(0f, 50f),
        DataPoint(1f, 70f),
        DataPoint(2f, 60f),
        DataPoint(3f, 80f),
        DataPoint(4f, 90f),
        DataPoint(5f, 75f),
        DataPoint(6f, 85f),
        DataPoint(7f, 65f),
        DataPoint(8f, 80f),
        DataPoint(20f, 70f)
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
                        selectedExercise = label
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
                value = selectedTimeLapse,
                onValueChange = { selectedTimeLapse = it },
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
                        selectedTimeLapse = label
                        expanded1 = false
                    }) {
                        Text(text = label)
                    }
                }

            }

        }
        /*

        LineChart(data, selectedExercise, 7,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(CenterHorizontally))
                
         */



        if(selectedExercise != null){
            val test = viewModel.getAllExerciseDoneInTimeById(selectedExercise!!.id, 7)
            Text(text = test[1].rep.toString())
            LineChartWithScaling(dataPoints = dataPoints)
        }
        


    }
}

@Preview
@Composable
fun StatsScreenPreview() {
    //StatsScreen()
}