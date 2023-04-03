package com.example.musclepluscompose

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome Back!",
            color = MaterialTheme.colors.primary,
            fontSize = MaterialTheme.typography.h4.fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 200.dp)
        )
        Text(
            text = "No Pain, no Gain!",
            color = MaterialTheme.colors.primary,
            fontSize = MaterialTheme.typography.h5.fontSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Workout Completed this week: 4",
            color = MaterialTheme.colors.primary,
            fontSize = MaterialTheme.typography.h6.fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 100.dp)
        )
        Text(
            text = "Total time trained this week: 04h23",
            color = MaterialTheme.colors.primary,
            fontSize = MaterialTheme.typography.h6.fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}