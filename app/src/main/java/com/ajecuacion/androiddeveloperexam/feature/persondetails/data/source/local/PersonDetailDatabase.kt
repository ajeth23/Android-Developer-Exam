package com.ajecuacion.androiddeveloperexam.feature.persondetails.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ajecuacion.androiddeveloperexam.feature.persondetails.data.model.PersonDetailEntity

@Database(entities = [PersonDetailEntity::class], version = 1)
abstract class PersonDetailDatabase : RoomDatabase() {
    abstract fun personDetailDao(): PersonDetailDao

    companion object {
        @Volatile
        private var INSTANCE: PersonDetailDatabase? = null

        fun getDatabase(context: Context): PersonDetailDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PersonDetailDatabase::class.java,
                    "person_detail_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
