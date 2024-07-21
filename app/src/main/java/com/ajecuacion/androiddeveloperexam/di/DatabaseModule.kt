package com.ajecuacion.androiddeveloperexam.di

import android.content.Context
import androidx.room.Room
import com.ajecuacion.androiddeveloperexam.feature.randomPerson.data.source.local.AppDatabase
import com.ajecuacion.androiddeveloperexam.feature.randomPerson.data.source.local.PersonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()

    @Provides
    @Singleton
    fun providePersonDao(appDatabase: AppDatabase): PersonDao = appDatabase.personDao()
}
