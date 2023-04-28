package com.example.musclepluscompose.data


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.flow.Flow

import com.google.gson.Gson


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

        val exercises = listOf<Exercise>(
            Exercise(name = "Push-ups", desc = "Start in a plank position with your hands and feet on the ground, lower your body towards the ground by bending your elbows, and push back up.", id = 0),
            Exercise(name = "Squats", desc = "Stand with feet hip-width apart, lower down into a sitting position while keeping your back straight, and return to standing.", id = 1),
            Exercise(name = "Sit-ups", desc = "Lie on your back with your knees bent, lift your upper body towards your knees and then return to lying flat.", id = 2),
            Exercise(name = "Plank ", desc = "Start in a push-up position but with forearms on the ground, hold the position while keeping your body straight.", id = 3),
            Exercise(name = "Burpees", desc = "Start in a standing position, drop down into a plank position, do a push-up, jump back up to a standing position, and jump with arms overhead.", id = 4),
            Exercise(name = "Jumping Jacks", desc = "Jump with feet apart while raising arms overhead, jump again and return to starting position with feet together and arms at your sides.", id = 5),
            Exercise(name = "Mountain climbers", desc = "Start in a plank position, bring your knee to your chest, alternate legs quickly while keeping your back straight.", id = 6),
            Exercise(name = "Jump squats", desc = "Stand with feet hip-width apart, lower down into a squat position and jump up while keeping arms overhead.", id = 7),
            Exercise(name = "Plank jacks", desc = "Start in a plank position, jump feet apart and back together while keeping your body straight.", id = 8),
            Exercise(name = "Side plank", desc = "Start in a plank position but with one forearm on the ground and the other hand on your hip, hold the position.", id = 9),
            Exercise(name = "Hanging leg raises", desc = "Hang from a bar and raise your legs straight up towards the bar.", id = 10),
            Exercise(name = "Bicep curls", desc = "Stand with feet hip-width apart, hold weights in each hand, and curl weights towards your shoulders.", id = 11),
            Exercise(name = "Tricep extensions", desc = "Stand with feet hip-width apart, hold weights above your head, and lower weights behind your head by bending elbows.", id = 12),
            Exercise(name = "Shoulder press", desc = "Stand with feet hip-width apart, hold weights at shoulder height, and push weights overhead.", id = 13),
            Exercise(name = "Side lunges", desc = "Take a big step to the side with one foot, bending the knee and keeping the other leg straight, and return to standing.", id = 14),
            Exercise(name = "High jumps", desc = "Jump as high as possible, lifting knees towards chest while in the air.", id = 15),
            Exercise(name = "Long jumps", desc = "Jump forward as far as possible while keeping feet together.", id = 16),
            Exercise(name = "Skipping", desc = "Jump with feet together, lifting one knee at a time towards your chest.", id = 17),
            Exercise(name = "Box jumps", desc = "Jump onto a box or bench and step back down, then repeat.", id = 18),
            Exercise(name = "Lunges", desc = "Take a big step forward with one foot, bending both knees to lower the back knee towards the ground, and return to standing.", id = 19)
        )

        val workout1 = listOf<Exercise>(
            exercises[0], // Push-ups
            exercises[1], // squats
            exercises[19], // Lunges
            exercises[3], // Plank
            exercises[11], // Bicep curl
            exercises[2], // Sit-ups
            exercises[6], // Mountain climbers
            exercises[13], // Shoulder press
            exercises[14], // Side lunges
            exercises[10] // Hanging leg raises
        )

        val workout2 = listOf<Exercise>(
            exercises[5], // Jumping Jacks
            exercises[4], // Burpees
            exercises[7], // Jump squats
            exercises[8], // Plank jacks
            exercises[15], // High Jumps
            exercises[6], // Mountain climbers
            exercises[18], // Box jumps
            exercises[17], // Skipping
            exercises[16], // Long jump
            exercises[9] // Plank jack
        )

        val workout3 = listOf<Exercise>(
            exercises[1], // squats
            exercises[19], // Lunges
            exercises[7], // Jump squats
            exercises[14], // Side lunges
            exercises[18], // Box jumps
            exercises[5], // Jumping Jacks
            exercises[16], // Long jump
            exercises[3], // Plank
            exercises[10], // Hanging leg raises
            exercises[15] // High Jumps
        )

        //val gson = Gson()
        //val workout1Json = gson.toJson(workout1)
        val workout1MutableList = workout1.toMutableList()
        val workout2MutableList = workout2.toMutableList()
        val workout3MutableList = workout3.toMutableList()

        val workouts = listOf<Workout>(
            Workout(name = "Full-body circuit", desc = "Description 1", exercise = workout1MutableList, id = 0),
            Workout(name = "HIIT (High-Intensity Interval Training)", desc = "Description 2", exercise = workout2MutableList, id = 1),
            Workout(name = "Leg-focused routine", desc = "Description 3", exercise = workout3MutableList, id = 2)
        )

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "App.db"
                ).allowMainThreadQueries().addCallback(DataBaseCallback(exercises, workouts)).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class DataBaseCallback(private val exercises: List<Exercise>, private val workouts: List<Workout>) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        exercises.forEach()
        {
            db.execSQL("INSERT INTO exercises (name, desc) VALUES ('${it.name}', '${it.desc}')")
        }

        workouts.forEach()
        {
            val gson = Gson()
            val workoutJson = gson.toJson(it.exercise)
            db.execSQL("INSERT INTO workouts (name, desc, exercises) VALUES ('${it.name}', '${it.desc}', '$workoutJson')")
        }

    }
}

@Dao
interface WorkoutDao{

    @Query("SELECT * FROM workouts")
    fun getAll(): Flow<List<Workout>>

    @Query("SELECT * FROM workouts WHERE id = :id")
    fun getById(id: Int): Workout

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

    @Query("SELECT * FROM workouts_done ORDER BY id DESC LIMIT 1")
    fun getLatestWorkoutDone(): Workout_Done?

    @Query("SELECT * FROM workouts_done WHERE id = :id")
    fun getWorkoutDoneById(id: Int): Workout_Done?

    @Query("SELECT * FROM workouts_done WHERE id =:id")
    fun getWorkoutDoneByIdTom(id : Int) : Workout_Done

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