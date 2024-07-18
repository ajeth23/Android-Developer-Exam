package com.ajecuacion.androiddeveloperexam.di

import android.content.Context
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.source.remote.RandomUserApi
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.source.remote.RetrofitInstance
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.repository.PersonRepositoryImpl
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.repository.PersonRepository
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.source.local.AppDatabase
import com.ajecuacion.androiddeveloperexam.feature.personlist.data.source.local.PersonDao
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.use_case.GetPersonsUseCase
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.use_case.RefreshPersonsUseCase
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.use_case.LoadMorePersonsUseCase
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.use_case.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersonListModule {

    @Provides
    @Singleton
    fun providePersonApi(): RandomUserApi {
        return RetrofitInstance.api
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun providePersonDao(appDatabase: AppDatabase): PersonDao {
        return appDatabase.personDao()
    }

    @Provides
    @Singleton
    fun providePersonRepository(
        personApi: RandomUserApi,
        personDao: PersonDao
    ): PersonRepository {
        return PersonRepositoryImpl(personApi, personDao)
    }


    @Provides
    @Singleton
    fun provideUseCases(repository: PersonRepository): UseCases {
        return UseCases(
            getPersonsUseCase = GetPersonsUseCase(repository),
            refreshPersonsUseCase = RefreshPersonsUseCase(repository),
            loadMorePersonsUseCase = LoadMorePersonsUseCase(repository)
        )
    }
}
