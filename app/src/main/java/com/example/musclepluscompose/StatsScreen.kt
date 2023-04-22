package com.example.musclepluscompose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.musclepluscompose.ui.theme.MuscleBlue
import androidx.compose.ui.unit.dp
import java.sql.Date

@Composable
fun StatsScreen() {

    val data = listOf(
        Pair("12/02", 20.0),
        Pair("13/02", 22.0),
        Pair("14/02", 25.0),
        Pair("15/02", 23.0),
        Pair("16/02", 24.0),
        Pair("17/02", 25.0),
        Pair("18/02", 27.0),
)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        LineChart(data,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(CenterHorizontally))

    }
}

@Preview
@Composable
fun StatsScreenPreview() {
    StatsScreen()
}