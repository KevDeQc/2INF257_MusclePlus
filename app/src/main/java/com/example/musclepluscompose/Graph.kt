package com.example.musclepluscompose

import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import com.example.musclepluscompose.data.Exercise
import kotlin.math.round
import kotlin.math.roundToInt
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import java.text.SimpleDateFormat
import java.util.*


fun FormatDate(date: Date): String {
    val simpleDate = SimpleDateFormat("dd/M")
    return simpleDate.format(date)
}

@Composable
fun LineChartWithScaling(dataPoints: List<DataPoint>) {
    // Find the min and max values for the x and y axes
    val xMin = dataPoints.minByOrNull { it.x }?.x ?: 0f
    val xMax = dataPoints.maxByOrNull { it.x }?.x ?: 0f
    val yMin = dataPoints.minByOrNull { it.y }?.y ?: 0f
    val yMax = dataPoints.maxByOrNull { it.y }?.y ?: 0f

    val graphColor = Color.Blue
    val transparentGraphColor = remember { graphColor.copy(alpha = 0.5f) }

    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    Surface(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Line Chart",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .height(200.dp)
            ) {
                // Calculate the x and y scales based on the min and max values
                val xScale = size.width / (xMax - xMin)
                var yScale = size.height / (yMax - yMin)
                if(yScale > 1) yScale = 1f


                // Define a blue paint for the line
                val linePaint = Paint().apply {
                    strokeWidth = 2f
                    color = Color.Blue.toArgb()
                }


                // Draw the x and y axes
                drawLine(
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    color = Color.Black,
                    strokeWidth = 2f
                )
                drawLine(
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    color = Color.Black,
                    strokeWidth = 2f
                )

                // Draw the data points as a line chart
                val path = Path()
                dataPoints.forEachIndexed { index, point ->
                    val x = (point.x - xMin) * xScale
                    val y = (point.y - yMin) * yScale
                    if (index == 0) {
                        drawCircle(
                            color = Color.Blue,
                            radius = 4.dp.toPx(),
                            center = Offset(x, size.height - y)
                        )
                        path.moveTo(x, size.height - y)
                    } else {
                        val prevPoint = dataPoints[index - 1]
                        val prevX = (prevPoint.x - xMin) * xScale
                        val prevY = (prevPoint.y - yMin) * yScale
                        path.lineTo(x, size.height - y)
                        drawLine(
                            start = Offset(prevX, size.height - prevY),
                            end = Offset(x, size.height - y),
                            color = Color.Blue,
                            strokeWidth = 2f,
                            cap = StrokeCap.Round
                        )
                        drawCircle(
                            color = Color.Blue,
                            radius = 4.dp.toPx(),
                            center = Offset(x, size.height - y)
                        )
                    }
                }

                val fillPath = path.asAndroidPath().asComposePath().apply {
                    lineTo(size.width, size.height - yScale)
                    lineTo(yScale, size.height - yScale)
                    close()
                }


                drawPath(
                    path = fillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            transparentGraphColor,
                            Color.Transparent
                        ),
                        endY = size.height - yScale
                    )
                )

                //remove y duplicate
                val copyList = dataPoints.distinctBy { it.y }

                //draw y axis labels
                copyList.forEach{ point ->

                    val x1 = -10.dp.toPx()
                    val y1 = ((point.y - yMin) * -yScale) + size.height
                    drawContext.canvas.nativeCanvas.apply {

                        drawText(
                            point.y.roundToInt().toString(),
                            x1,
                            y1,
                            textPaint
                        )
                    }
                }

                // Draw the x-axis labels
                dataPoints.forEach { point ->
                    val x = (point.x - xMin) * -xScale + size.width
                    val y = size.height + 16.dp.toPx()

                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            FormatDate(point.date),
                            x,
                            y,
                            textPaint
                        )
                    }
                }
            }
        }
    }
}



