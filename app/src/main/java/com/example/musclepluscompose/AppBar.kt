package com.example.musclepluscompose

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.musclepluscompose.ui.theme.MuscleBlue

@Composable
fun AppBar(
    onNavigationIconClick: () -> Unit
) {
   TopAppBar(
       title = {
           Text(text = "Muscle Plus")
       },
       //backgroundColor = MaterialTheme.colors.primary,
       backgroundColor = MuscleBlue,
       contentColor = MaterialTheme.colors.onPrimary, // White
       navigationIcon = {
           IconButton(onClick = onNavigationIconClick) {
               Icon(imageVector = Icons.Default.Menu, contentDescription = "Toggle drawer")
           }
       }
   )
}