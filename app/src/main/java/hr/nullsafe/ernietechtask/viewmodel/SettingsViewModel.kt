package hr.nullsafe.ernietechtask.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.nullsafe.ernietechtask.data.ServicesRepository
import hr.nullsafe.ernietechtask.ui.UiState
import hr.nullsafe.ernietechtask.ui.settings.SettingsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val servicesRepository: ServicesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val serviceId = savedStateHandle.get<String>("id")
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            fetchSettings()
        }
    }

    private suspend fun fetchSettings() {
        if (serviceId == null) return
        val settingsList = servicesRepository.getAllSettings(serviceId)

        _uiState.update { it.copy(state = UiState.Success(settingsList)) }

        println(settingsList)
    }

    fun toggleSettings(settingsId: String, enabled: Boolean) {
        if (serviceId == null) return
        viewModelScope.launch {
            servicesRepository.toggleSettings(serviceId, settingsId, enabled)

            fetchSettings()
        }
    }
}