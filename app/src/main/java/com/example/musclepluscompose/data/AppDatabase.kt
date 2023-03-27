package com.example.musclepluscompose.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Workout::class, Exercise::class],
    version = 1

)
abstract class AppDatabase: RoomDatabase() {

    abstract val dao: DbDao
}