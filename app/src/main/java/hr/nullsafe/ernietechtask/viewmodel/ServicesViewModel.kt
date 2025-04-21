package hr.nullsafe.ernietechtask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.nullsafe.ernietechtask.DispatcherSettings
import hr.nullsafe.ernietechtask.data.ServicesRepository
import hr.nullsafe.ernietechtask.ui.UiState
import hr.nullsafe.ernietechtask.ui.services.ServicesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ServicesViewModel @Inject constructor(
    private val servicesRepository: ServicesRepository,
    private val dispatcherSettings: DispatcherSettings
) : ViewModel() {

    private val _uiState = MutableStateFlow(ServicesUiState())
    val uiState = _uiState.asStateFlow()

    init {
        initData()
    }

    private fun initData() {
        viewModelScope.launch {
            val apiResponse = fetchServices()

            _uiState.update {
                if (apiResponse != null) {
                    it.copy(state = UiState.Success(apiResponse))
                } else {
                    it.copy(state = UiState.Error("Error fetching data."))
                }
            }
        }
    }

    fun retryDataFetch() {
        initData()
    }

    private suspend fun fetchServices() =
        withContext(dispatcherSettings.ioDispatcher()) {
            servicesRepository.fetchServices()
        }
}