package com.example.musclepluscompose.data.exerciseModel

import com.example.musclepluscompose.data.Exercise
import com.example.musclepluscompose.data.SortType

sealed interface ExerciseEvent{
    object SaveExercise: ExerciseEvent
    data class SetExerciseName(val name: String): ExerciseEvent

    object ShowExercise: ExerciseEvent
    object HideExercise: ExerciseEvent

    data class SortExercise(val sortType: SortType): ExerciseEvent
    data class DeleteExercise(val exercise: Exercise): ExerciseEvent
}