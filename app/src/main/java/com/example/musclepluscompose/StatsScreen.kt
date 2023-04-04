package com.example.musclepluscompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import java.sql.Date

@Composable
fun StatsScreen() {

    Chart(data = mapOf(
        Pair(0.3f, "23/02"),
        Pair(0.4f, "24/02"),
        Pair(0.45f, "25/02"),
        Pair(0.5f, "26/02"),
        Pair(0.52f, "27/02"),

    ), max_value = 100)
    /*
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "Stats",
            color = MaterialTheme.colors.primary,
            fontSize = MaterialTheme.typography.h3.fontSize,
            fontWeight = FontWeight.Bold
        )
    }
     */
}

@Preview
@Composable
fun StatsScreenPreview() {
    StatsScreen()
}