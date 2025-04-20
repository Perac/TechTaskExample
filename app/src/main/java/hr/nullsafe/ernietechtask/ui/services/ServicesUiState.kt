package hr.nullsafe.ernietechtask.ui.services

import hr.nullsafe.ernietechtask.data.Service
import hr.nullsafe.ernietechtask.ui.UiState

data class ServicesUiState(
    val state: UiState<List<Service>> = UiState.Loading()
)
