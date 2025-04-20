package hr.nullsafe.ernietechtask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.nullsafe.ernietechtask.data.ServicesRepository
import hr.nullsafe.ernietechtask.ui.UiState
import hr.nullsafe.ernietechtask.ui.services.ServicesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ServicesViewModel(
    private val servicesRepository: ServicesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ServicesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val apiResponse = servicesRepository.fetchServices()

            _uiState.update { it.copy(state = UiState.Success(apiResponse)) }

            println(apiResponse)
        }
    }
}