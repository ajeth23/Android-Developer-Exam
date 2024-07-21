package com.ajecuacion.androiddeveloperexam.feature.randomPerson.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajecuacion.androiddeveloperexam.core.common.Resource
import com.ajecuacion.androiddeveloperexam.feature.randomPerson.domain.model.Person
import com.ajecuacion.androiddeveloperexam.feature.randomPerson.domain.usecase.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _personDetail = MutableStateFlow<Resource<Person>>(Resource.Loading())
    val personDetail: StateFlow<Resource<Person>> = _personDetail

    fun loadPersonDetail(id: String) {
        viewModelScope.launch {
            useCases.getPersonDetailsUseCase(id).collect { resource ->
                _personDetail.value = resource
            }
        }
    }
}
