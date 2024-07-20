package com.ajecuacion.androiddeveloperexam.feature.personlist.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajecuacion.androiddeveloperexam.core.common.Constants
import com.ajecuacion.androiddeveloperexam.core.common.Constants.PERSONS_KEY
import com.ajecuacion.androiddeveloperexam.core.common.Resource
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.model.Person
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val useCases: UseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {

    }

    private val _persons = MutableStateFlow<Resource<List<Person>>>(Resource.Loading())
    val persons: StateFlow<Resource<List<Person>>> = _persons.asStateFlow()

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent: SharedFlow<String> = _toastEvent

    var currentPage: Int
        get() = savedStateHandle[Constants.CURRENT_PAGE_KEY] ?: 1
        set(value) {
            savedStateHandle[Constants.CURRENT_PAGE_KEY] = value
        }

    init {
        savedStateHandle.get<List<Person>>(PERSONS_KEY)?.let { savedPersons ->
            _persons.value = Resource.Success(savedPersons)
        } ?: run {
            getPersons()
        }
    }

    fun getPersons() {
        viewModelScope.launch {
            useCases.getPersonsUseCase().collect { resource ->
                handleResource(resource) { newList ->
                    _persons.value = Resource.Success(newList)
                    savedStateHandle[PERSONS_KEY] = newList
                }
            }
        }
    }

    fun refreshPersons() {
        currentPage = 1
        viewModelScope.launch {
            useCases.refreshPersonsUseCase().collect { resource ->
                handleResource(resource) { newList ->
                    _persons.value = Resource.Success(newList)
                    savedStateHandle[PERSONS_KEY] = newList
                }
            }
        }
    }

    fun loadMorePersons() {
        viewModelScope.launch {
            useCases.loadMorePersonsUseCase(currentPage).collect { resource ->
                handleResource(resource) { newList ->
                    val currentList = (_persons.value as? Resource.Success)?.data.orEmpty()
                    _persons.value = Resource.Success(currentList + newList)
                    savedStateHandle[PERSONS_KEY] = currentList + newList
                    currentPage++
                }
            }
        }
    }

    private suspend fun handleResource(
        resource: Resource<List<Person>>,
        onSuccess: (List<Person>) -> Unit
    ) {
        if (resource is Resource.Success) {
            val newList = resource.data ?: emptyList()
            onSuccess(newList)
        } else {
            _persons.value = resource
        }
        if (resource is Resource.Error) {
            _toastEvent.emit(resource.message ?: "An error occurred")
        }
    }
}
