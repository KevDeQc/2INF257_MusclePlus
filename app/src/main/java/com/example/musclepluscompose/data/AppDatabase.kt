package com.example.musclepluscompose.data

<<<<<<< HEAD
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
=======
import android.content.Context
import androidx.room.*
import kotlinx.coroutines.flow.Flow
>>>>>>> 1a72b476d60970d0993beb2e03413289f30de7df

@Database(
    entities = [Workout::class, Exercise::class, Workout_List::class, Workout_Done::class, Exercise_Done::class],
    version = 1

)
<<<<<<< HEAD
@TypeConverters(DateConverter::class)
=======
@TypeConverters(Converters::class)
>>>>>>> 1a72b476d60970d0993beb2e03413289f30de7df
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