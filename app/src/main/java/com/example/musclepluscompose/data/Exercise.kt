package com.example.musclepluscompose.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exercise(
    val name : String,
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val desc : String
)
