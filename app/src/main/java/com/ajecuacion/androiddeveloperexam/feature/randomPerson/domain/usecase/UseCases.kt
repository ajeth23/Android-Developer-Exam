package com.ajecuacion.androiddeveloperexam.feature.randomPerson.domain.usecase

data class UseCases(
    val getPersonsUseCase: GetPersonListUseCase,
    val refreshPersonsUseCase: RefreshPersonsUseCase,
    val loadMorePersonsUseCase: LoadMorePersonsUseCase,
    val getPersonDetailsUseCase: GetPersonDetailsUseCase
)
