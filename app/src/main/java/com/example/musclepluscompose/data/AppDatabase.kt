package com.example.musclepluscompose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Workout::class, Exercise::class, Workout_List::class, Workout_Done::class, Exercise_Done::class],
    version = 1

)
@TypeConverters(DateConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract val dao: DbDao
}