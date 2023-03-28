package com.example.musclepluscompose.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class Exercise(

    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val name : String,
    val desc : String
)
