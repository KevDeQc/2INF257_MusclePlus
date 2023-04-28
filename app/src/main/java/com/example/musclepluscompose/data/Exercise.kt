package com.example.musclepluscompose.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.musclepluscompose.R

@Entity(tableName = "exercises")
data class Exercise(

    val name : String,
    val desc : String,
    val imageId : Int = R.drawable.default_exercise,
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
)
