package com.example.musclepluscompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.musclepluscompose.data.workoutModel.WorkoutEvent
import com.example.musclepluscompose.data.workoutModel.WorkoutState

@Composable
fun EditWorkout(
    state: WorkoutState,
    onEvent: (WorkoutEvent) -> Unit,
    modifier: Modifier = Modifier
){
    Box(modifier = modifier
        .fillMaxSize()
        .background(Color.White)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {

            Text(text = "test")

            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Button(onClick = { onEvent(WorkoutEvent.HideWorkout)}) {
                    Text(text = "cancel")
                }
                Button(onClick = {onEvent(WorkoutEvent.SaveWorkout)}) {
                    Text(text = "save")
                }
            }



        }
    }

}