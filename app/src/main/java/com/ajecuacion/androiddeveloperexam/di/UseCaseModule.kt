package com.ajecuacion.androiddeveloperexam.di

import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.repository.PersonRepository
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.usecase.GetPersonUseCase
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.usecase.GetPersonsUseCase
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.usecase.LoadMorePersonsUseCase
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.usecase.RefreshPersonsUseCase
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.usecase.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideUseCases(repository: PersonRepository): UseCases = UseCases(
        getPersonsUseCase = GetPersonsUseCase(repository),
        refreshPersonsUseCase = RefreshPersonsUseCase(repository),
        loadMorePersonsUseCase = LoadMorePersonsUseCase(repository),
        getPersonDetailsUseCase = GetPersonUseCase(repository)
    )
}
