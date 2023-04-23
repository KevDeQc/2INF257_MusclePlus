package com.example.musclepluscompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.musclepluscompose.ui.theme.MusclePlusComposeTheme
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.musclepluscompose.data.AppDatabase
import com.example.musclepluscompose.data.AppViewModel


class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    private val viewModel by viewModels<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MusclePlusComposeTheme {

                navController = rememberNavController()


                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()


                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                             AppBar(
                                 onNavigationIconClick = {
                                     scope.launch { scaffoldState.drawerState.open() }
                                 }
                             )
                    },
                    drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
                    drawerContent = {
                        DrawerHeader()
                        DrawerBody(
                            items = listOf(
                                MenuItem(
                                    id = "home",
                                    title = "Home",
                                    contentDescription = "Go to home screen",
                                    icon = Icons.Default.Home
                                ),
                                MenuItem(
                                    id = "exercise",
                                    title = "Exercise",
                                    contentDescription = "Go to exercise screen",
                                    icon = Icons.Default.Star
                                ),
                                MenuItem(
                                    id = "workout",
                                    title = "Workout",
                                    contentDescription = "Go to workout screen",
                                    icon = Icons.Default.Favorite
                                ),
                                MenuItem(
                                    id = "stats",
                                    title = "Stats",
                                    contentDescription = "Go to stats screen",
                                    icon = Icons.Default.Info
                                ),
                                MenuItem(
                                    id = "startWorkout",
                                    title = "Start Workout",
                                    contentDescription = "Go to start workout activity",
                                    icon = Icons.Default.PlayArrow
                                )
                            ) ,
                            onItemClick = {
                                scope.launch { scaffoldState.drawerState.close() }
                                when(it.id){
                                    "home" -> navController.navigate(route = Screen.Home.route)
                                    "exercise" -> navController.navigate(route = Screen.Exercise.route)
                                    "workout" -> navController.navigate(route = Screen.Workout.route)
                                    "stats" -> navController.navigate(route = Screen.Stats.route)
                                    "startWorkout" -> startWorkoutActivity()
                                }
                            })
                    }
                ) {

                    contentPadding ->
                    Box(modifier = Modifier.fillMaxSize()){
                        SetupNavGraph(navController = navController, viewModel)
                    }

                }
            }
        }
    }

    private fun startWorkoutActivity() {
        Intent(this, WorkoutTracker::class.java).also {
            startActivity(it)
        }
    }
}
