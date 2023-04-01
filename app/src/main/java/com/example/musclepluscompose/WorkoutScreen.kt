package com.example.musclepluscompose


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.musclepluscompose.data.AppViewModel
import com.example.musclepluscompose.data.Exercise
import com.example.musclepluscompose.data.Workout


@Composable
fun WorkoutScreen(viewModel: AppViewModel) {
    val allWorkouts by viewModel.allWorkout.collectAsState(emptyList())
    val allExercises by viewModel.allExercise.collectAsState(emptyList())
    var isEditing by remember { mutableStateOf(false) }

    var selectedWorkout by remember { mutableStateOf<Workout?>(null) }

    if (isEditing) {

        val workout = selectedWorkout
        if (workout != null) {
            EditWorkoutScreen(
                workout = workout,
                onDismiss = { isEditing = false },
                onSave = { modifiedWorkout ->
                    viewModel.upsertWorkout(modifiedWorkout)
                    isEditing = false
                },
            allExercises = allExercises, viewModel)
        } else {
            val emptyList = mutableListOf<Exercise>()
            EditWorkoutScreen(workout = Workout("", "", emptyList),
                onDismiss = { isEditing = false },
                onSave = { modifiedWorkout ->
                    viewModel.upsertWorkout(modifiedWorkout)
                    isEditing = false
                },
            allExercises = allExercises,
                viewModel
            )
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(allWorkouts) { workout ->
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .horizontalScroll(rememberScrollState())
                            .border(2.dp, Color.LightGray)
                            .clickable {
                                isEditing = true
                                selectedWorkout = workout
                            },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                workout.name,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold
                            )
                            Text(workout.desc)
                        }
                        IconButton(onClick = { viewModel.deleteWorkout(workout) }) {
                            Icon(
                                modifier = Modifier.fillMaxSize(),
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "delete"
                            )
                        }
                    }

                }
            }

            FloatingActionButton(
                modifier = Modifier
                    .padding(all = 16.dp)
                    .align(alignment = Alignment.BottomEnd),
                onClick = {
                    isEditing = true
                    selectedWorkout = null
                }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
            }
        }
    }
}

@Composable
fun EditWorkoutScreen(
    workout: Workout,
    onDismiss: () -> Unit,
    onSave: (Workout) -> Unit,
    allExercises: List<Exercise>,
    viewModel: AppViewModel
) {
    var name by remember { mutableStateOf(TextFieldValue(workout.name)) }
    var desc by remember { mutableStateOf(TextFieldValue(workout.desc)) }
    var isAddingExercise by remember { mutableStateOf(false) }
    var exerciseInWorkout by remember { mutableStateOf(listOf<Exercise>()) }

    exerciseInWorkout = workout.exercise


    if(isAddingExercise){
        //ChooseExercise(onDismiss = {isAddingExercise = false}, allExercises, currentExercise)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "Exercises:")
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(allExercises) { exercise ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = exercise.name)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = exercise.desc)
                        IconButton(onClick = { workout.exercise.add(exercise)
                            isAddingExercise = false }) {
                            Icon(
                                modifier = Modifier.fillMaxSize(),
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Add"
                            )
                        }
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = { isAddingExercise = false}, ) {
                    Text(text = "Cancel")
                }
            }

        }
    }
    else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Edit Workout",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            Text(
                text = "Workout name",
                modifier = Modifier.padding(vertical = 5.dp)
            )
            TextField(
                value = name,
                onValueChange = { newName -> name = newName },
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Workout description",
                modifier = Modifier.padding(vertical = 5.dp)
            )
            TextField(
                value = desc,
                onValueChange = { newDesc -> desc = newDesc },
                modifier = Modifier.fillMaxWidth()
            )

            Text(text = "Exercises:")
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                val mutableExerciseList = exerciseInWorkout.toMutableList()
                items(mutableExerciseList) { exercise ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = exercise.name)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = exercise.desc)

                        IconButton(onClick = { mutableExerciseList.remove(exercise)
                        exerciseInWorkout = mutableExerciseList.toList()}) {
                            Icon(
                                modifier = Modifier.fillMaxSize(),
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "delete"
                            )
                        }
                    }
                }
            }

            Button(onClick = {
                isAddingExercise = true
            }) {
                Text(text = "Add exercise")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.padding(end = 16.dp)
                ) {
                    Text("Cancel")
                }
                Button(
                    onClick = {
                        onSave(workout.copy(name = name.text, desc = desc.text, exercise = exerciseInWorkout.toMutableList()))
                    }
                ) {
                    Text("Save")
                }
            }
        }
    }
}

@Composable
fun ChooseExercise1(
    onDismiss: () -> Unit,
    allExercises: List<Exercise>,
    selectedExercises: MutableList<Exercise>
) {
    var searchText by remember { mutableStateOf("") }
    val filteredExercises = allExercises.filter {
        it.name.contains(searchText, ignoreCase = true) ||
                it.desc.contains(searchText, ignoreCase = true)
    }



    Dialog(
        onDismissRequest = onDismiss,
        content = {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    label = { Text("Search") },
                    modifier = Modifier.fillMaxWidth()
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredExercises) { exercise ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = selectedExercises.contains(exercise),
                                onCheckedChange = {
                                    if (it) {
                                        selectedExercises.add(exercise)
                                    } else {
                                        selectedExercises.remove(exercise)
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(text = exercise.name)
                                Text(text = exercise.desc)
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = onDismiss, modifier = Modifier.padding(end = 8.dp)) {
                        Text(text = "Cancel")
                    }
                    Button(
                        onClick = onDismiss,
                        enabled = selectedExercises.isNotEmpty()
                    ) {
                        Text(text = "Add")
                    }
                }
            }
        }
    )
}

