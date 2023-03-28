package com.example.musclepluscompose.data

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Database(
    entities = [Workout::class, Exercise::class],
    version = 1

)
abstract class AppDatabase: RoomDatabase() {

    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "App.db"
                ).build()
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workout: Workout)

    @Delete
    suspend fun delete(workout: Workout)
}

@Dao
interface ExerciseDao{

    @Query("SELECT * FROM exercises")
    fun getAll(): Flow<List<Exercise>>

    @Insert
    suspend fun insert(exercise: Exercise)

}