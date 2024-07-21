package com.ajecuacion.androiddeveloperexam.di

import android.content.Context
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.repository.PersonRepositoryImpl
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.source.local.PersonDao
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.source.remote.RandomUserApi
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.repository.PersonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePersonRepository(
        api: RandomUserApi,
        dao: PersonDao,
        @ApplicationContext context: Context
    ): PersonRepository = PersonRepositoryImpl(api, dao, context)
}
