package com.example.musclepluscompose

data class WorkoutTrackerExerciseList(
    val id: Int,
    val name: String,
    var exerciseItems: List<WorkoutTrackerExerciseItem>
)
