package com.example.musclepluscompose


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musclepluscompose.data.AppViewModel
import com.example.musclepluscompose.data.Workout
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun WorkoutScreen(viewModel: AppViewModel) {
    val workouts by viewModel.allWorkout.collectAsState(emptyList())
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
                })
        } else {
            EditWorkoutScreen(workout = Workout("", ""),
                onDismiss = { isEditing = false },
                onSave = { modifiedWorkout ->
                    viewModel.upsertWorkout(modifiedWorkout)
                    isEditing = false
                })
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
                items(workouts) { workout ->
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
    onSave: (Workout) -> Unit
) {
    var name by remember { mutableStateOf(TextFieldValue(workout.name)) }
    var desc by remember { mutableStateOf(TextFieldValue(workout.desc)) }

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
                    onSave(workout.copy(name = name.text, desc = desc.text))
                }
            ) {
                Text("Save")
            }
        }
    }
}

