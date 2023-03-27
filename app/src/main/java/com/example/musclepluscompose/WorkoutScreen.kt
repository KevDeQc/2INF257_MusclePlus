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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musclepluscompose.data.SortType
import com.example.musclepluscompose.data.workoutModel.WorkoutEvent
import com.example.musclepluscompose.data.workoutModel.WorkoutState

@Composable
fun WorkoutScreen(
    state: WorkoutState,
    onEvent: (WorkoutEvent) -> Unit
){
    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(WorkoutEvent.ShowWorkout)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add workout"
                )
            }
        },
        //ignore this error
    ){ _ ->
        if(state.isAddingWorkout){
            //EditWorkout(state = state, onEvent = onEvent)
            AddWorkoutDialog(state = state, onEvent = onEvent)
        }
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            item { 
                Row (
                    modifier = Modifier
                        .fillMaxSize()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    SortType.values().forEach { sortType ->
                        Row (
                            modifier = Modifier
                                .clickable {
                                    onEvent(WorkoutEvent.SortWorkouts(sortType))
                                },
                            verticalAlignment = CenterVertically
                        ){
                            RadioButton(
                                selected = state.sortType == sortType,
                                onClick = {
                                    onEvent(WorkoutEvent.SortWorkouts(sortType))
                                }
                            )
                            Text(text = sortType.name)
                        }
                    }
                }
            }

            items(state.workouts){ workout ->
                Row(modifier = Modifier.fillMaxSize(),
                ) {
                    Column(modifier = Modifier.weight(1f),
                    ) {
                        Text(
                            text = workout.name,
                            fontSize = 20.sp,
                        )
                    }
                    IconButton(onClick = {
                        onEvent(WorkoutEvent.DeleteWorkout(workout))
                    }) {
                         Icon(
                             imageVector = Icons.Default.Delete,
                             contentDescription = "Delete workout"
                         )
                    }

                }
            }

        }
    }
}