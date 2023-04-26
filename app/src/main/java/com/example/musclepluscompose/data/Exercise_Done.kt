package com.example.musclepluscompose.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises_done")
data class Exercise_Done(

    val exercise_id: Int,
    val rep: Int,
    val weight: Int,
    @PrimaryKey
    val workout_done_id: Int
)
