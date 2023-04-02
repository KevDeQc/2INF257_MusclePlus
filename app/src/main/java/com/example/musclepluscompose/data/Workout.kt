package com.example.musclepluscompose.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout(

    val name: String,
    val desc : String,
<<<<<<< HEAD
=======
    @ColumnInfo(name = "exercises") val exercise: MutableList<Exercise>,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    //val exerciseList: MutableList<Exercise>
>>>>>>> 1a72b476d60970d0993beb2e03413289f30de7df

)
