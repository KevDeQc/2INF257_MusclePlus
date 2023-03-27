package com.example.musclepluscompose.data.workoutModel

import com.example.musclepluscompose.data.SortType
import com.example.musclepluscompose.data.Workout

data class WorkoutState(
    val workouts: List<Workout> = emptyList(),
    val name : String = "",
    val isAddingWorkout: Boolean = false,
    val sortType: SortType = SortType.NAME
)
