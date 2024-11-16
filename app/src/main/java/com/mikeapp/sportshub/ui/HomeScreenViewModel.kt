package com.mikeapp.sportshub.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikeapp.sportshub.domain.NbaHubUseCase
import com.mikeapp.sportshub.domain.NflHubUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val nbaHubUseCase: NbaHubUseCase,
    private val nflHubUseCase: NflHubUseCase
) : ViewModel() {
    val uiState: MutableStateFlow<String> = MutableStateFlow("")

    fun queryNbaOnce() {
        uiState.value = "start querying NBA schedule..."
        viewModelScope.launch(IO) {
            nbaHubUseCase.queryOnce()
        }
        uiState.value = "querying NBA schedule completed!"
    }

    fun queryNflOnce() {
        uiState.value = "start querying NFL schedule..."
        viewModelScope.launch(IO) {
            nflHubUseCase.queryOnce()
        }
        uiState.value = "querying NFL schedule completed!"
    }
}