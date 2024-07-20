package com.ajecuacion.androiddeveloperexam.feature.persondetails.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajecuacion.androiddeveloperexam.core.common.Resource
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.model.Person
import com.ajecuacion.androiddeveloperexam.feature.personlist.domain.usecase.GetPersonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    private val getPersonDetailsUseCase: GetPersonUseCase
) : ViewModel() {

    private val _personDetail = MutableStateFlow<Resource<Person>>(Resource.Loading())
    val personDetail: StateFlow<Resource<Person>> = _personDetail

    fun loadPersonDetail(id: String) {
        viewModelScope.launch {
            getPersonDetailsUseCase(id).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Log.d("PersonDetailViewModel", "Data loaded successfully: ${resource.data}")
                    }
                    is Resource.Error -> {
                        Log.e("PersonDetailViewModel", "Error loading data: ${resource.message}")
                    }
                    is Resource.Loading -> {
                        Log.d("PersonDetailViewModel", "Loading data...")
                    }
                }
                _personDetail.value = resource
            }
        }
    }
}
