package com.example.musclepluscompose.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val workoutDao = AppDatabase.getDatabase(application).workoutDao()
    private val exerciseDao = AppDatabase.getDatabase(application).exerciseDao()

    val allWorkout: Flow<List<Workout>> = workoutDao.getAll()

    fun insertWorkout(workout: Workout){
        viewModelScope.launch {
            workoutDao.insert(workout)
        }

    }

    fun deleteWorkout(workout: Workout){
        viewModelScope.launch {
            workoutDao.delete(workout)
        }
    }

    fun updateWorkout(workout: Workout){
        viewModelScope.launch {
            workoutDao.update(workout)
        }
    }

    fun upsertWorkout(workout: Workout){
        viewModelScope.launch {
            workoutDao.upsert(workout)
        }
    }

//-------- Exercise -------

    val allExercise: Flow<List<Exercise>> = exerciseDao.getAll()

    fun insertExercise(exercise: Exercise){
        viewModelScope.launch {
            exerciseDao.insert(exercise)
        }
    }

    fun deleteExercise(exercise: Exercise){
        viewModelScope.launch {
            exerciseDao.delete(exercise)
        }
    }

    fun updateExercise(exercise: Exercise){
        viewModelScope.launch{
            exerciseDao.update(exercise)
        }
    }

    fun upsertExercise(exercise: Exercise){
        viewModelScope.launch {
            exerciseDao.upsert(exercise)
        }
    }




}