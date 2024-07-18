package com.ajecuacion.androiddeveloperexam.feature.personlist.presentation.viewmodels

import androidx.lifecycle.*
import com.ajecuacion.androiddeveloperexam.core.common.Resource
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.model.Person
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.use_case.GetPersonsUseCase
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.use_case.RefreshPersonsUseCase
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.use_case.LoadMorePersonsUseCase
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _persons = MutableLiveData<List<Person>>()
    val persons: LiveData<List<Person>> get() = _persons

    private val _state = MutableLiveData<Resource<List<Person>>>()
    val state: LiveData<Resource<List<Person>>> = _state

    private val currentList = mutableListOf<Person>()

    init {
        fetchCachedPersons()
    }

    private fun fetchCachedPersons() {
        useCases.getPersonsUseCase().observeForever { cachedPersons ->
            if (!cachedPersons.isNullOrEmpty()) {
                currentList.clear()
                currentList.addAll(cachedPersons)
                _persons.value = currentList
            }
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            useCases.refreshPersonsUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        currentList.clear()
                        currentList.addAll(resource.data ?: emptyList())
                        _persons.value = currentList
                    }

                    is Resource.Loading -> {
                        _state.value = Resource.Loading()
                    }

                    is Resource.Error -> {
                        _state.value = Resource.Error(resource.message ?: "Unknown error")
                    }
                }
            }
        }
    }

    fun loadMoreData() {
        viewModelScope.launch {
            useCases.loadMorePersonsUseCase().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val newData = resource.data ?: emptyList()
                        currentList.addAll(newData)
                        _persons.value = currentList
                        _state.value = Resource.Success(currentList)
                    }

                    is Resource.Loading -> {
                        _state.value = Resource.Loading()
                    }

                    is Resource.Error -> {
                        _state.value = Resource.Error(resource.message ?: "Unknown error")
                    }
                }
            }
        }
    }
}
