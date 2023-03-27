@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.musclepluscompose.data.workoutModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musclepluscompose.data.DbDao
import com.example.musclepluscompose.data.Exercise
import com.example.musclepluscompose.data.SortType
import com.example.musclepluscompose.data.Workout
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


@OptIn(ExperimentalCoroutinesApi::class)
class WorkoutViewModel(
    private val dao : DbDao
): ViewModel() {

    private val _sortType = MutableStateFlow(SortType.NAME)
    private val _workouts = _sortType
        .flatMapLatest { sortType->
            when(sortType){
                SortType.NAME -> dao.getWorkoutOrderedByName()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(WorkoutState())
    val state = combine(_state, _sortType, _workouts){state, sortType, workouts ->
        state.copy(
            workouts = workouts,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), WorkoutState())

    fun onEvent(event: WorkoutEvent){
        when(event){
            is WorkoutEvent.DeleteWorkout ->{
                viewModelScope.launch {
                    dao.deleteWorkout(event.workout)
                }
            }
            WorkoutEvent.SaveWorkout -> {
                val name = state.value.name

                if(name.isBlank()){
                    return
                }
                val workout = Workout(
                    name = name,
                    desc = "test",
                    //exerciseList = mutableListOf()
                )
                viewModelScope.launch {
                    dao.upsertWorkout(workout)
                }
                _state.update { it.copy(
                    isAddingWorkout = false,
                    name = ""
                ) }
            }
            is WorkoutEvent.SetWorkoutName -> {
                _state.update { it.copy(
                    name = event.name
                ) }
            }
            is WorkoutEvent.SortWorkouts -> {
                _sortType.value = event.sortType
            }
            WorkoutEvent.HideWorkout -> {
                _state.update { it.copy(
                    isAddingWorkout = false
                ) }
            }
            WorkoutEvent.ShowWorkout -> {
                _state.update { it.copy(
                    isAddingWorkout = true
                ) }
            }
        }
    }
}