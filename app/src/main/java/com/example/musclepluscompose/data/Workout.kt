package com.example.musclepluscompose.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val desc : String,

    //val exerciseList: MutableList<Exercise>

)
