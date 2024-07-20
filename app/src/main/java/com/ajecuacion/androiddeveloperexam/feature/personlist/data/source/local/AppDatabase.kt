package com.ajecuacion.androiddeveloperexam.feature.personlist.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.model.PersonEntity

@Database(entities = [PersonEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}
