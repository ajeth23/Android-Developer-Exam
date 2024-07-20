package com.ajecuacion.androiddeveloperexam.feature.personlist.domain.usecase

data class UseCases(
    val getPersonsUseCase: GetPersonsUseCase,
    val refreshPersonsUseCase: RefreshPersonsUseCase,
    val loadMorePersonsUseCase: LoadMorePersonsUseCase,
    val getPersonDetailsUseCase: GetPersonUseCase
)
