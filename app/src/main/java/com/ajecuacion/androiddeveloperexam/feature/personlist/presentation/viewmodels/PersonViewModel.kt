package com.ajecuacion.androiddeveloperexam.feature.personlist.presentation.viewmodels

import androidx.lifecycle.*
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.usecase.UseCases
import com.ajecuacion.androiddeveloperexam.core.common.Resource
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.model.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _persons = MutableStateFlow<Resource<List<Person>>>(Resource.Loading())
    val persons: StateFlow<Resource<List<Person>>> = _persons.asStateFlow()

    private val _personDetails = MutableStateFlow<Resource<Person>>(Resource.Loading())
    val personDetails: StateFlow<Resource<Person>> = _personDetails.asStateFlow()

    var currentPage = 1
        private set

    init {
        getPersons()
    }

    fun getPersons() {
        viewModelScope.launch {
            useCases.getPersonsUseCase().collect {
                _persons.value = it
            }
        }
    }

    fun refreshPersons() {
        currentPage = 1
        viewModelScope.launch {
            useCases.refreshPersonsUseCase().collect {
                _persons.value = it
            }
        }
    }

    fun loadMorePersons() {
        viewModelScope.launch {
            useCases.loadMorePersonsUseCase(currentPage).collect {
                _persons.value = it
                currentPage++
            }
        }
    }

}
