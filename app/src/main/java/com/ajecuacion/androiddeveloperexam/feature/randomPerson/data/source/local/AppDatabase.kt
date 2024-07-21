package com.ajecuacion.androiddeveloperexam.feature.randomPerson.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ajecuacion.androiddeveloperexam.feature.randomPerson.data.model.PersonEntity

@Database(entities = [PersonEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}
