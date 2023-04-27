package com.example.musclepluscompose.data


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Database(
    entities = [Workout::class, Exercise::class, Workout_Done::class, Exercise_Done::class],
    version = 1

)
@TypeConverters(DateConverter::class, Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun Exercise_DoneDao(): Exercise_DoneDao
    abstract fun Workout_DoneDao(): Workout_DoneDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        //allowMainThread -> for functions with no coroutines
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "App.db"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }

}

@Dao
interface WorkoutDao{

    @Query("SELECT * FROM workouts")
    fun getAll(): Flow<List<Workout>>

    @Query("SELECT * FROM workouts WHERE id =:id")
    fun getWorkoutById(id : Int) : Workout

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workout: Workout)

    @Delete
    suspend fun delete(workout: Workout)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(workout: Workout)

    @Upsert()
    suspend fun upsert(workout: Workout)

}

@Dao
interface ExerciseDao{

    @Query("SELECT * FROM exercises")
    fun getAll(): Flow<List<Exercise>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exercise: Exercise)

    @Delete
    suspend fun delete(exercise: Exercise)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(exercise: Exercise)

    @Upsert()
    suspend fun upsert(exercise: Exercise)

}

@Dao
interface Workout_DoneDao{

    @Query("SELECT * FROM workouts_done")
    fun getAll(): Flow<List<Workout_Done>>

    @Query("SELECT * FROM workouts_done WHERE id =:id")
    fun getWorkoutDoneById(id : Int) : Workout_Done

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workout_done: Workout_Done)

    @Delete
    suspend fun delete(workout_done: Workout_Done)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(workout_done: Workout_Done)

    @Upsert()
    suspend fun upsert(workout_done: Workout_Done)

}

@Dao
interface Exercise_DoneDao{

    @Query("SELECT * FROM exercises_done")
    fun getAll(): Flow<List<Exercise_Done>>

    @Query("SELECT * FROM exercises_done WHERE exercise_id =:exercise_id")
    fun getAllExerciseDoneByExerciseId(exercise_id : Int) : List<Exercise_Done>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exercise_done: Exercise_Done)

    @Delete
    suspend fun delete(exercise_done: Exercise_Done)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(exercise_done: Exercise_Done)

    @Upsert()
    suspend fun upsert(exercise_done: Exercise_Done)

}