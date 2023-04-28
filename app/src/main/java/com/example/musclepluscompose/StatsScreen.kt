package com.example.musclepluscompose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.musclepluscompose.data.*
import java.util.Date


data class DataPoint(val x: Float, val y: Float, val date : Date)

private fun ConvertToDay(date: Date){
    date.toString().format()
}

@Composable
fun StatsScreen(viewModel: AppViewModel) {


    val timeLapse  = listOf<Pair<String, Int>>(
        Pair("Week", 7),
        Pair("1 Month", 30),
        Pair("3 Month", 90),
        Pair("6 Month", 180),
        Pair("Year", 365)
    )
    val listExercises by viewModel.allExercise.collectAsState(emptyList())
    var selectedExercise by remember { mutableStateOf<Exercise?>(null)}

    var expanded by remember { mutableStateOf(false) }
    var textFiledSize by remember { mutableStateOf(Size.Zero) }
    var selectedItem by remember { mutableStateOf("") }

    var expanded1 by remember { mutableStateOf(false) }
    var textFiledSize1 by remember { mutableStateOf(Size.Zero) }
    var selectedTimeScope by remember { mutableStateOf<Int?>(null) }
    var selectedItem1 by remember { mutableStateOf("") }

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


    //debug values
    /*
    val exercise1 = Exercise("Test", "ceci est un test", 1)
    viewModel.upsertExercise(exercise1)
    viewModel.upsertWorkout(Workout("WorkoutTest", "ceci est un test", mutableListOf(exercise1, exercise1, exercise1), 1))
    viewModel.upsertWorkout_Done(Workout_Done(1, Date(), "aujourd'hui", 1))
    viewModel.upsertExercise_Done(Exercise_Done(1, 10, 100, 1))

    viewModel.upsertWorkout_Done(Workout_Done(1, Date(1682558321000), "04/27", 2))
    viewModel.upsertExercise_Done(Exercise_Done(1, 10, 100, 2))

    viewModel.upsertWorkout_Done(Workout_Done(1, Date(1682058321000), "04/21", 3))
    viewModel.upsertExercise_Done(Exercise_Done(1, 12, 100, 3))

    viewModel.upsertWorkout_Done(Workout_Done(1, Date(1680052721000), "03/28", 4))
    viewModel.upsertExercise_Done(Exercise_Done(1, 40, 100, 4))

    viewModel.upsertWorkout_Done(Workout_Done(1, Date(1674879692000), "01/27", 5))
    viewModel.upsertExercise_Done(Exercise_Done(1, 50, 100, 5))
     */


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
                    }
                    .clickable(onClick = { expanded = true }),
                label = { Text("Choose exercise") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                    backgroundColor = Color.Transparent,
                    disabledBorderColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
                    disabledLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
                ),
                trailingIcon = {
                    Icon(icon, "contentDescription",)
                },
                enabled = false
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
                value = selectedItem1,
                onValueChange = { selectedItem1 = it },
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        //This value is used to assign to the DropDown the same width
                        textFiledSize1 = coordinates.size.toSize()
                    }
                    .clickable(onClick = { expanded1 = true }),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
                    backgroundColor = Color.Transparent,
                    disabledBorderColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
                    disabledLabelColor = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium),
                ),
                label = { Text("Time period") },
                trailingIcon = { Icon(icon1, "contentDescription",)
                }
            )
            DropdownMenu(
                expanded = expanded1,
                onDismissRequest = { expanded1 = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textFiledSize1.width.toDp() })
            ) {
                timeLapse.forEach { label ->
                    DropdownMenuItem(onClick = {
                        selectedItem1 = label.first
                        expanded1 = false
                        selectedTimeScope = label.second
                    }) {
                        Text(text = label.first)
                    }
                }

            }

        }


        if(selectedExercise != null && selectedTimeScope != null){

            val test = viewModel.getAllExerciseDoneInTimeById(selectedExercise!!.id, selectedTimeScope!!)

            val data = mutableListOf<DataPoint>()

            test.forEachIndexed { index, item ->
                if(item.first.rep > 0){
                    data.add(DataPoint(index.toFloat(), item.first.rep.toFloat(), item.second))
                }
            }

            if(data.isEmpty() || data.size == 1){
                Text(
                    text = "No ${selectedExercise?.name ?: "exercise"} have been completed.",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

            }
            else{
                LineChartWithScaling(dataPoints = data)
            }
        }
    }
}

@Preview
@Composable
fun StatsScreenPreview() {
    //StatsScreen()
}