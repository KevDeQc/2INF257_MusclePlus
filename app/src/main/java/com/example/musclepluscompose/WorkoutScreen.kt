package com.example.musclepluscompose


import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musclepluscompose.data.AppViewModel
import com.example.musclepluscompose.data.Workout
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun WorkoutScreen(viewModel: AppViewModel)
{
    val workouts by viewModel.allWorkout.collectAsState(emptyList())
    var isEditing by remember { mutableStateOf(false) }


    if(isEditing){
        Column(modifier = Modifier
            .fillMaxSize()
        ) {
            var name by remember { mutableStateOf(TextFieldValue("")) }
            var desc by remember { mutableStateOf(TextFieldValue(""))}

            Text(text = "Workout name :")
            TextField(value = name, onValueChange = {newText -> name = newText} )
            Text(text = "Workout description :")
            TextField(value = desc, onValueChange = {newDesc -> desc = newDesc} )
            Button(onClick = {
                isEditing = false
                viewModel.insertWorkout(Workout( name.text, desc.text))
            }) {
                Text(text = "save")
            }
        }
    }
    else{
        Box(modifier = Modifier
            .fillMaxSize(),
        ){
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ){
                items(workouts){workout ->
                    WorkoutItem(workout, viewModel)
                    //Text("${workout.name}: ${workout.desc}")
                }
            }

            FloatingActionButton(
                modifier = Modifier
                    .padding(all = 16.dp)
                    .align(alignment = Alignment.BottomEnd),
                onClick = {
                    isEditing = true
                }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
            }
        }
    }

}

@Composable
fun WorkoutItem(workout : Workout, viewModel: AppViewModel) {

    Row(
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(rememberScrollState())
            .border(2.dp, Color.LightGray),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
        Column(modifier = Modifier.weight(1f)) {
            Text(workout.name, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
            Text(workout.desc)
        }
        IconButton( onClick = { viewModel.deleteWorkout(workout)}) {
            Icon(modifier = Modifier.fillMaxSize(), imageVector = Icons.Filled.Delete, contentDescription = "delete")
        }




    }
}


@Composable
fun EditWindow(){

    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        Text(text = "test")
        Text(text = "test")
        Text(text = "test")
        Text(text = "test")
        Text(text = "test")
        Text(text = "test")
        Text(text = "test")
        Text(text = "test")
        Text(text = "test")
        Text(text = "test")
        Text(text = "test")
        Text(text = "test")

        Button(onClick = { }) {

        }
    }

}