package com.example.musclepluscompose.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.musclepluscompose.data.Exercise
import com.example.musclepluscompose.data.Workout
import kotlinx.coroutines.flow.Flow

@Dao
interface DbDao {

    @Upsert
    suspend fun upsertWorkout(workout: Workout)

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    @Query("SELECT * FROM workout ORDER BY name ASC")
    fun getWorkoutOrderedByName(): Flow<List<Workout>>

    @Upsert
    suspend fun upsertExercise(exercise: Exercise)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Query("SELECT * FROM workout ORDER BY name ASC")
    fun getExerciseOrderedByName(): Flow<List<Exercise>>





}