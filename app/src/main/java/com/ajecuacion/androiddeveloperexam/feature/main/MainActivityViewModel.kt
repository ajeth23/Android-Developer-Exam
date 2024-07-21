package com.ajecuacion.androiddeveloperexam.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajecuacion.androiddeveloperexam.core.common.ConnectivityMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val connectivityMonitor: ConnectivityMonitor
) : ViewModel() {

    private val _connectivityStatus = MutableSharedFlow<Boolean>()
    val connectivityStatus: SharedFlow<Boolean> = _connectivityStatus

    init {
        monitorConnectivity()
    }

    private fun monitorConnectivity() {
        viewModelScope.launch {
            connectivityMonitor.observeConnectivity().collect { isConnected ->
                _connectivityStatus.emit(isConnected)
            }
        }
    }
}
