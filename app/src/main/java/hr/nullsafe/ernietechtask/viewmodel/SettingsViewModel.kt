package hr.nullsafe.ernietechtask.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.nullsafe.ernietechtask.data.ServicesRepository
import hr.nullsafe.ernietechtask.data.Settings
import hr.nullsafe.ernietechtask.ui.UiState
import hr.nullsafe.ernietechtask.ui.settings.SettingsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val servicesRepository: ServicesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val serviceId = savedStateHandle.get<String>("id")
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        initData()
    }

    private fun initData() {
        if (serviceId == null) return
        viewModelScope.launch {
            servicesRepository
                .getAllSettings(serviceId)
                .collect {
                    updateUI(it)
                }
        }
    }

    private fun updateUI(settingsList: List<Settings>) {
        _uiState.update { it.copy(state = UiState.Success(settingsList)) }
    }

    fun toggleSettings(settingsId: String, enabled: Boolean) {
        if (serviceId == null) return
        viewModelScope.launch {
            servicesRepository.toggleSettings(serviceId, settingsId, enabled)
        }
    }
}