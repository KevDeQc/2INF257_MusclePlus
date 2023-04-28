package com.example.musclepluscompose

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.musclepluscompose.data.AppViewModel
import com.example.musclepluscompose.data.Exercise
import com.example.musclepluscompose.data.Workout
import com.example.musclepluscompose.ui.theme.MuscleBlue


@Composable
fun ExerciseScreen(viewModel: AppViewModel) {
    val exercises by viewModel.allExercise.collectAsState(emptyList())
    var isEditing by remember { mutableStateOf(false) }

    var selectedExercise by remember { mutableStateOf<Exercise?>(null) }

    val gradient = Brush.linearGradient(
        0.0f to Color.White,
        500.0f to Color.DarkGray,
        start = Offset.Zero,
        end = Offset.Infinite
    )


    if (isEditing) {

        val exercise = selectedExercise
        if (exercise != null) {
            EditExerciseScreen(
                exercise = exercise,
                onDismiss = { isEditing = false },
                onSave = { modifiedExercise ->
                    viewModel.upsertExercise(modifiedExercise)
                    isEditing = false
                },
                onDelete = {modifiedExercise ->
                    viewModel.deleteExercise(modifiedExercise)
                isEditing = false})
        } else {
            EditExerciseScreen(exercise = Exercise("", "", R.drawable.default_exercise),
                onDismiss = { isEditing = false },
                onSave = { modifiedExercise ->
                    viewModel.upsertExercise(modifiedExercise)
                    isEditing = false
                },
            onDelete = { modifiedExercise ->
                viewModel.deleteExercise(modifiedExercise)
                isEditing = false
            })
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(exercises) { exercise ->
                    Row(
                        modifier = Modifier
                            .background(Color.White, shape = RoundedCornerShape(50.dp))
                            .fillMaxSize()
                            .horizontalScroll(rememberScrollState())
                            .padding(20.dp)
                            .clickable {
                                isEditing = true
                                selectedExercise = exercise
                            },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                exercise.name,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(exercise.desc)
                            
                            Spacer(modifier = Modifier.height(20.dp))

                            Image(painter = painterResource(id = exercise.imageId), contentDescription = "exercise image", modifier = Modifier.clip(
                                RoundedCornerShape(50.dp)
                            ))

                        }
                    }

                }
            }

            FloatingActionButton(
                modifier = Modifier
                    .padding(all = 16.dp)
                    .align(alignment = Alignment.BottomEnd),
                onClick = {
                    isEditing = true
                    selectedExercise = null
                }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
            }
        }
    }
}


@Composable
fun EditExerciseScreen(
    exercise: Exercise,
    onDismiss: () -> Unit,
    onSave: (Exercise) -> Unit,
    onDelete: (Exercise) -> Unit
) {
    var name by remember { mutableStateOf(TextFieldValue(exercise.name)) }
    var desc by remember { mutableStateOf(TextFieldValue(exercise.desc)) }
    val gradient = Brush.linearGradient(
        0.0f to Color.White,
        500.0f to Color.DarkGray,
        start = Offset.Zero,
        end = Offset.Infinite
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Edit Exercise",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Text(
            text = "Exercise name",
            modifier = Modifier.padding(vertical = 5.dp)
        )
        TextField(
            value = name,
            onValueChange = { newName -> name = newName },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
        )
        Text(
            text = "Exercise description",
            modifier = Modifier.padding(vertical = 5.dp)
        )
        TextField(
            value = desc,
            onValueChange = { newDesc -> desc = newDesc },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White)
        )

        Image(painter = painterResource(id = R.drawable.default_exercise),
            contentDescription = "img",
            modifier = Modifier
                .width(150.dp)
                .height(150.dp)
                .padding(20.dp)
                .clip(RoundedCornerShape(20))
                .background(Color.White)

            )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(modifier = Modifier
                .padding(end = 16.dp)
                , onClick = { onDelete(exercise) }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(237, 88, 88))) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "delete"
                )

                Text(text = "Delete")
            }
            
            Spacer(modifier = Modifier.weight(0.4f))

            Button( // Cancel btn
                onClick = onDismiss,
                modifier = Modifier.padding(end = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MuscleBlue,
                    contentColor = MaterialTheme.colors.onPrimary // White
                )
            ) {
                Text("Cancel")
            }
            Button( // Save btn
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MuscleBlue,
                    contentColor = MaterialTheme.colors.onPrimary // White
                ),
                onClick = {
                    onSave(exercise.copy(name = name.text, desc = desc.text, imageId = R.drawable.barbell_rows))
                }

            ) {
                Text("Save")
            }
        }
    }
}