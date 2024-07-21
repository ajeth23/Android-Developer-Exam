package com.ajecuacion.androiddeveloperexam.di

import com.ajecuacion.androiddeveloperexam.feature.randomPerson.domain.repository.PersonRepository
import com.ajecuacion.androiddeveloperexam.feature.randomPerson.domain.usecase.GetPersonDetailsUseCase
import com.ajecuacion.androiddeveloperexam.feature.randomPerson.domain.usecase.GetPersonListUseCase
import com.ajecuacion.androiddeveloperexam.feature.randomPerson.domain.usecase.LoadMorePersonsUseCase
import com.ajecuacion.androiddeveloperexam.feature.randomPerson.domain.usecase.RefreshPersonsUseCase
import com.ajecuacion.androiddeveloperexam.feature.randomPerson.domain.usecase.UseCases
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
        getPersonsUseCase = GetPersonListUseCase(repository),
        refreshPersonsUseCase = RefreshPersonsUseCase(repository),
        loadMorePersonsUseCase = LoadMorePersonsUseCase(repository),
        getPersonDetailsUseCase = GetPersonDetailsUseCase(repository)
    )
}
