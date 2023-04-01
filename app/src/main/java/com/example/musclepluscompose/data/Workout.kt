package com.example.musclepluscompose.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout(

    val name: String,
    val desc : String,
    @ColumnInfo(name = "exercises") val exercise: MutableList<Exercise>,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    //val exerciseList: MutableList<Exercise>

)
