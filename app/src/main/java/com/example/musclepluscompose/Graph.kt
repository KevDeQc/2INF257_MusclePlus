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
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import kotlin.math.round
import kotlin.math.roundToInt


@Composable
fun LineChart(
    data: List<Pair<String, Double>> = emptyList(),
    modifier: Modifier = Modifier
) {
    val spacing = 100f
    val graphColor = Color.Blue
    val transparentGraphColor = remember { graphColor.copy(alpha = 0.5f) }
    val upperValue = remember { (data.maxOfOrNull { it.second }?.plus(1))?.roundToInt() ?: 0 }
    val lowerValue = remember { (data.minOfOrNull { it.second }?.toInt() ?: 0) }
    val density = LocalDensity.current

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

    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

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

        Spacer(
            Modifier
                .fillMaxWidth()
                .weight(1f))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "Push up over time",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontFamily = FontFamily.Serif)
        }

        Spacer(
            Modifier
                .fillMaxWidth()
                .weight(0.5f))

        Canvas(modifier = modifier.padding(0.dp, 0.dp, 0.dp, 20.dp)) {
            val spacePerHour = (size.width - spacing) / data.size
            (data.indices).forEach { i ->
                val hour = data[i].first
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        hour.toString(),
                        spacing + i * spacePerHour,
                        size.height,
                        textPaint
                    )
                }
            }

            val priceStep = (upperValue - lowerValue) / 5f
            (0..5).forEach { i ->
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        round(lowerValue + priceStep * i).toInt().toString(),
                        30f,
                        size.height - spacing - i * size.height / 5f,
                        textPaint
                    )
                }
            }

            val strokePath = Path().apply {
                val height = size.height
                data.indices.forEach { i ->
                    val info = data[i]
                    val ratio = (info.second - lowerValue) / (upperValue - lowerValue)

                    val x1 = spacing + i * spacePerHour
                    val y1 = height - spacing - (ratio * height).toFloat()

                    if (i == 0) { moveTo(x1, y1) }
                    lineTo(x1, y1)
                }
            }

            drawPath(
                path = strokePath,
                color = graphColor,
                style = Stroke(
                    width = 2.dp.toPx(),
                    cap = StrokeCap.Round
                )
            )

            val fillPath = strokePath.asAndroidPath().asComposePath().apply {
                lineTo(size.width - spacePerHour, size.height - spacing)
                lineTo(spacing, size.height - spacing)
                close()
            }

            drawPath(
                path = fillPath,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        transparentGraphColor,
                        Color.Transparent
                    ),
                    endY = size.height - spacing
                )
            )

        }

    }

}


