package com.example.musclepluscompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musclepluscompose.ui.theme.MuscleBlue
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.musclepluscompose.data.AppViewModel
import com.example.musclepluscompose.data.Exercise_Done
import com.example.musclepluscompose.data.Workout_Done
import java.util.concurrent.TimeUnit

fun convertTotalWeight(list : List<Exercise_Done>) : Int{

    var result = 0
    list.forEach { result += it.weight * it.rep }
    return result
}

fun convertTotalReps(list : List<Exercise_Done>) : Int{

    var result = 0
    list.forEach { result+= it.rep}
    return result
}

private fun formatElapsedTime(timeMillis: Long): String {
    val hours = TimeUnit.MILLISECONDS.toHours(timeMillis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeMillis) % 60
    return String.format("%02d:%02d", hours, minutes)
}

fun convertTotalTime(list : List<Workout_Done>) : String{
    var result = 0L
    list.forEach { result += it.time }
    return formatElapsedTime(result)
}


@Composable
fun HomeScreen(viewModel: AppViewModel) {

    val listExercise = viewModel.getAllExerciseInTime(7)
    val listWorkout = viewModel.getAllWorkoutInTime(7)
    val totalWeight = convertTotalWeight(listExercise)
    val totalReps = convertTotalReps(listExercise)
    val totalWorkout = listWorkout.size
    val totalTime = convertTotalTime(listWorkout)
    var commentFromLast : String
    if(listWorkout.isEmpty()){
        commentFromLast = ""
    }
    else{
        commentFromLast = listWorkout.last().comment
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(painter = painterResource(id = R.drawable.bench_press),
            contentScale = ContentScale.FillWidth,
            contentDescription = "banner",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            .background(MuscleBlue, shape = RoundedCornerShape(10.dp))
            .height(100.dp)
        ) {
            Column() {
                Text(textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(),
                    text = "Comment from your last workout", fontWeight = FontWeight.Bold, color = Color.White)
                Text(textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(),
                    text = commentFromLast)
            }

        }

        Spacer(modifier = Modifier.height(30.dp))
        
        Column() {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .height(100.dp)
            ) {

                Box(modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight()
                    .background(MuscleBlue, shape = RoundedCornerShape(10.dp))
                ){
                    Column() {
                        Text(textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            text = "Total weight lifted this week",
                        )
                        Text(textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(),
                            text = "$totalWeight Kg", fontSize = 30.sp)
                    }

                }

                Spacer(modifier = Modifier.weight(0.05f))

                Box(modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight()
                    .background(MuscleBlue, shape = RoundedCornerShape(10.dp))
                ){
                    Column() {
                        Text(textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            text = "Total reps done this week",
                        )
                        Text(textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(),
                            text = "$totalReps", fontSize = 30.sp)
                    }

                }
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .height(100.dp)
            ) {

                Box(modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight()
                    .background(MuscleBlue, shape = RoundedCornerShape(10.dp))
                ){
                    Column() {
                        Text(textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            text = "Total workouts this week",
                        )
                        Text(textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(),
                            text = "$totalWorkout", fontSize = 30.sp)
                    }

                }
                Spacer(modifier = Modifier.weight(0.05f))

                Box(modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight()
                    .background(MuscleBlue, shape = RoundedCornerShape(10.dp))
                ){
                    Column() {
                        Text(textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            text = "Total time worked out this week",
                        )
                        Text(textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(),
                            text = totalTime, fontSize = 30.sp)
                    }

                }

            }
            
        }


    }
}