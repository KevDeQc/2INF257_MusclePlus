package com.example.musclepluscompose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musclepluscompose.data.workoutModel.WorkoutEvent
import com.example.musclepluscompose.data.workoutModel.WorkoutState
import com.example.musclepluscompose.data.exerciseModel.ExerciseEvent
import com.example.musclepluscompose.data.exerciseModel.ExerciseState

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    workoutState: WorkoutState,
    workoutOnEvent : (WorkoutEvent) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(
            route = Screen.Home.route
        ){
            HomeScreen()
        }
        composable(
            route = Screen.Exercise.route
        ){
            ExerciseScreen()
        }
        composable(
            route = Screen.Workout.route
        ){
            WorkoutScreen(state = workoutState, onEvent = workoutOnEvent)
        }
        composable(
            route = Screen.Stats.route
        ){
            StatsScreen()
        }
    }
}