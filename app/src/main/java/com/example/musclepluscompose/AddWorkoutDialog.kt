package com.example.musclepluscompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musclepluscompose.data.workoutModel.WorkoutEvent
import com.example.musclepluscompose.data.workoutModel.WorkoutState

@Composable
fun AddWorkoutDialog(
    state: WorkoutState,
    onEvent: (WorkoutEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {
            onEvent(WorkoutEvent.HideWorkout)
        },
        title = { Text(text = "Add workout") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                TextField(
                    value = state.name,
                    onValueChange = {
                        onEvent(WorkoutEvent.SetWorkoutName(it))
                    },
                    placeholder = {
                        Text(text = "Workout name")
                    }
                )
            }
        },
        buttons = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = {
                    onEvent(WorkoutEvent.SaveWorkout)
                }) {
                    Text(text = "Save")
                }
            }
       }
    )
}