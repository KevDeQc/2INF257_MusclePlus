package com.example.musclepluscompose.data.exerciseModel

import com.example.musclepluscompose.data.Exercise
import com.example.musclepluscompose.data.SortType

data class ExerciseState(
    val exercise: List<Exercise> = emptyList(),
    val name : String = "",
    val isAddingExercise: Boolean = false,
    val sortType: SortType = SortType.NAME
)
