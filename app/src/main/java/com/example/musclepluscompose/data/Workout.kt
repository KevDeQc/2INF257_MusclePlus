package com.example.musclepluscompose.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout(

    val name: String,
    val desc : String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    //val exerciseList: MutableList<Exercise>

)
