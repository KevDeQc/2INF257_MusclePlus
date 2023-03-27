@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.musclepluscompose.data.exerciseModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musclepluscompose.data.Exercise
import com.example.musclepluscompose.data.SortType
import com.example.musclepluscompose.data.DbDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


@OptIn(ExperimentalCoroutinesApi::class)
class ExerciseViewModel(
    private val dao : DbDao
): ViewModel() {

    private val _sortType = MutableStateFlow(SortType.NAME)
    private val _exercise = _sortType
        .flatMapLatest { sortType->
            when(sortType){
                SortType.NAME -> dao.getExerciseOrderedByName()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(ExerciseState())
    val state = combine(_state, _sortType, _exercise){ state, sortType, exercises ->
        state.copy(
            exercise = exercises,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExerciseState())

    fun onEvent(event: ExerciseEvent){
        when(event){
            is ExerciseEvent.DeleteExercise ->{
                viewModelScope.launch {
                    dao.deleteExercise(event.exercise)
                }
            }
            ExerciseEvent.SaveExercise -> {
                val name = state.value.name

                if(name.isBlank()){
                    return
                }
                val exercise = Exercise(
                    name = name,
                    desc = "test"
                )
                viewModelScope.launch {
                    dao.upsertExercise(exercise)
                }
                _state.update { it.copy(
                    isAddingExercise = false,
                    name = ""
                ) }
            }
            is ExerciseEvent.SetExerciseName -> {
                _state.update { it.copy(
                    name = event.name
                ) }
            }
            is ExerciseEvent.SortExercise -> {
                _sortType.value = event.sortType
            }
            ExerciseEvent.HideExercise -> {
                _state.update { it.copy(
                    isAddingExercise = false
                ) }
            }
            ExerciseEvent.ShowExercise -> {
                _state.update { it.copy(
                    isAddingExercise = true
                ) }
            }
        }
    }
}