package com.example.musclepluscompose.data.workoutModel

import com.example.musclepluscompose.data.SortType
import com.example.musclepluscompose.data.Workout

sealed interface WorkoutEvent{
    object SaveWorkout: WorkoutEvent
    data class SetWorkoutName(val name: String): WorkoutEvent

    object ShowWorkout: WorkoutEvent
    object HideWorkout: WorkoutEvent

    data class SortWorkouts(val sortType: SortType): WorkoutEvent
    data class DeleteWorkout(val workout: Workout): WorkoutEvent
}