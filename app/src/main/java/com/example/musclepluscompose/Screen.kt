package com.example.musclepluscompose

sealed class Screen( val route: String){
    object Home: Screen(route = "home_screen")
    object Exercise: Screen(route = "exercise_screen")
    object Workout: Screen(route = "workout_screen")
    object Stats: Screen(route = "stats_screen")
    object SplashScreen: Screen(route = "splash_screen")
}
