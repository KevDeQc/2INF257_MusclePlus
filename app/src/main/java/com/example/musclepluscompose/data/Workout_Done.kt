package com.example.musclepluscompose.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.Date

@Entity()
data class Workout_Done(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val workout_id: Int,
    @TypeConverters(DateConverter::class)
    val date: Date,
    val comment: String
)
