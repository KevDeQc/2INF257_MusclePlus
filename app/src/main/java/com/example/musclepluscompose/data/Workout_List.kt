package com.example.musclepluscompose.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Workout_List(

    @PrimaryKey
    val workout_id: Int,
    val exercise_id : Int

    )