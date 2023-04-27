package com.example.musclepluscompose

import androidx.compose.foundation.border
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
import com.example.musclepluscompose.data.AppViewModel
import com.example.musclepluscompose.data.Exercise
import com.example.musclepluscompose.data.Workout
import com.example.musclepluscompose.ui.theme.MuscleBlue

@Composable
fun ExerciseScreen(viewModel: AppViewModel) {
    val exercises by viewModel.allExercise.collectAsState(emptyList())
    var isEditing by remember { mutableStateOf(false) }

    var selectedExercise by remember { mutableStateOf<Exercise?>(null) }

    if (isEditing) {

        val exercise = selectedExercise
        if (exercise != null) {
            EditExerciseScreen(
                exercise = exercise,
                onDismiss = { isEditing = false },
                onSave = { modifiedExercise ->
                    viewModel.upsertExercise(modifiedExercise)
                    isEditing = false
                })
        } else {
            EditExerciseScreen(exercise = Exercise("", ""),
                onDismiss = { isEditing = false },
                onSave = { modifiedExercise ->
                    viewModel.upsertExercise(modifiedExercise)
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
                items(exercises) { exercise ->
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .horizontalScroll(rememberScrollState())
                            .border(2.dp, Color.LightGray)
                            .clickable {
                                isEditing = true
                                selectedExercise = exercise
                            },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                exercise.name,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold
                            )
                            Text(exercise.desc)
                        }
                        IconButton(onClick = { viewModel.deleteExercise(exercise) }) {
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
                    selectedExercise = null
                }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
            }
        }
    }
}

@Composable
fun EditExerciseScreen(
    exercise: Exercise,
    onDismiss: () -> Unit,
    onSave: (Exercise) -> Unit
) {
    var name by remember { mutableStateOf(TextFieldValue(exercise.name)) }
    var desc by remember { mutableStateOf(TextFieldValue(exercise.desc)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Edit Exercise",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Text(
            text = "Exercise name",
            modifier = Modifier.padding(vertical = 5.dp)
        )
        TextField(
            value = name,
            onValueChange = { newName -> name = newName },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Exercise description",
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
            Button( // Cancel btn
                onClick = onDismiss,
                modifier = Modifier.padding(end = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MuscleBlue,
                    contentColor = MaterialTheme.colors.onPrimary // White
                )
            ) {
                Text("Cancel")
            }
            Button( // Save btn
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MuscleBlue,
                    contentColor = MaterialTheme.colors.onPrimary // White
                ),
                onClick = {
                    onSave(exercise.copy(name = name.text, desc = desc.text))
                }

            ) {
                Text("Save")
            }
        }
    }
}