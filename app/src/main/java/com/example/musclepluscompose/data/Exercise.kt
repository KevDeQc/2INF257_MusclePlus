package com.example.musclepluscompose.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class Exercise(

    val name : String,
    val desc : String,
    val imageId : Int,
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
)
