package com.example.musclepluscompose

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.musclepluscompose.ui.theme.Purple500


@Composable
fun Chart(
    data: Map<Float, String>,
    max_value: Int
) {

    var expanded by remember { mutableStateOf(false) }
    var textFiledSize by remember { mutableStateOf(Size.Zero) }
    var selectedItem by remember { mutableStateOf("") }
    val list = listOf("Push up", "Sit up", "Squat")

    var expanded1 by remember { mutableStateOf(false) }
    var textFiledSize1 by remember { mutableStateOf(Size.Zero) }
    var selectedItem1 by remember { mutableStateOf("") }
    val list1 = listOf("Week", "Month", "Year")

    val icon1 = if (expanded1) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }

    val context = LocalContext.current
    // BarGraph Dimensions
    val barGraphHeight by remember { mutableStateOf(200.dp) }
    val barGraphWidth by remember { mutableStateOf(20.dp) }
    // Scale Dimensions
    val scaleYAxisWidth by remember { mutableStateOf(50.dp) }
    val scaleLineWidth by remember { mutableStateOf(2.dp) }

    Column(Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(20.dp, 10.dp)) {
            OutlinedTextField(
                value = selectedItem,
                onValueChange = { selectedItem = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        //This value is used to assign to the DropDown the same width
                        textFiledSize = coordinates.size.toSize()
                    },
                label = { Text("Choose exercise") },
                trailingIcon = {
                    Icon(icon, "contentDescription",
                        Modifier.clickable { expanded = !expanded })
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textFiledSize.width.toDp() })
            ) {
                list.forEach { label ->
                    DropdownMenuItem(onClick = {
                        selectedItem = label
                        expanded = false
                    }) {
                        Text(text = label)
                    }
                }

            }
        }

        Column(modifier = Modifier.padding(20.dp, 0.dp)) {
            OutlinedTextField(
                value = selectedItem1,
                onValueChange = { selectedItem1 = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        //This value is used to assign to the DropDown the same width
                        textFiledSize1 = coordinates.size.toSize()
                    },
                label = { Text("Time period") },
                trailingIcon = {
                    Icon(icon1, "contentDescription",
                        Modifier.clickable { expanded1 = !expanded1 })
                }
            )
            DropdownMenu(
                expanded = expanded1,
                onDismissRequest = { expanded1 = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textFiledSize1.width.toDp() })
            ) {
                list1.forEach { label ->
                    DropdownMenuItem(onClick = {
                        selectedItem1 = label
                        expanded1 = false
                    }) {
                        Text(text = label)
                    }
                }

            }
            
        }

        Column(
            modifier = Modifier
                .padding(10.dp, 30.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {



            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(barGraphHeight),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start
            ) {
                // scale Y-Axis
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(scaleYAxisWidth),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(text = max_value.toString())
                        Spacer(modifier = Modifier.fillMaxHeight())
                    }

                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(text = (max_value / 2).toString())
                        Spacer(modifier = Modifier.fillMaxHeight(0.5f))
                    }

                }

                // Y-Axis Line
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(scaleLineWidth)
                        .background(Color.Black)
                )

                // graph
                data.forEach {
                    Box(
                        modifier = Modifier
                            .padding(start = barGraphWidth * 2f, bottom = 5.dp)
                            .clip(CircleShape)
                            .width(barGraphWidth)
                            .fillMaxHeight(it.key)
                            .background(Purple500)
                            .clickable {
                                Toast
                                    .makeText(context, it.key.toString(), Toast.LENGTH_SHORT)
                                    .show()
                            }
                    )
                }

            }

            // X-Axis Line
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(scaleLineWidth)
                    .background(Color.Black)
            )

            // Scale X-Axis
            Row(
                modifier = Modifier
                    .padding(start = scaleYAxisWidth + barGraphWidth * 2f + scaleLineWidth)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(barGraphWidth)
            ) {

                data.values.forEach {
                    Text(
                        modifier = Modifier.width((barGraphWidth * 2f)),
                        text = it,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp
                    )
                }

            }
        }
    }


}
