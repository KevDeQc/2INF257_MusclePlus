package com.example.musclepluscompose.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Exercise_Done(

    @PrimaryKey
    val workout_done_id: Int,
    val exercise_id: Int,
    val rep: Int,
    val weight: Int
)
