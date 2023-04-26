package com.example.musclepluscompose.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity(tableName = "workouts_done")
data class Workout_Done(


    val workout_id: Int,
    @TypeConverters(DateConverter::class)
    val date: Date,
    val comment: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
