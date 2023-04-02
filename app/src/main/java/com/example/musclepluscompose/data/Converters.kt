package com.example.musclepluscompose.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromExerciseList(exerciseList: List<Exercise>): String {
        val gson = Gson()
        return gson.toJson(exerciseList)
    }

    @TypeConverter
    fun toExerciseList(exerciseListString: String): List<Exercise> {
        val gson = Gson()
        val type = object : TypeToken<List<Exercise>>() {}.type
        return gson.fromJson(exerciseListString, type)
    }
}