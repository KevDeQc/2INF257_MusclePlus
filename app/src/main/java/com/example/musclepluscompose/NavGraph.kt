package com.example.musclepluscompose

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(
    navController: NavHostController
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
            WorkoutScreen()
        }
        composable(
            route = Screen.Stats.route
        ){
            StatsScreen()
        }
    }
}