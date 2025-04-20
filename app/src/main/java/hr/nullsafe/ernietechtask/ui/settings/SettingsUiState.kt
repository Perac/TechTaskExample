package hr.nullsafe.ernietechtask.ui.settings

import hr.nullsafe.ernietechtask.data.Settings
import hr.nullsafe.ernietechtask.ui.UiState

data class SettingsUiState(
    val state: UiState<List<Settings>> = UiState.Loading()
)
