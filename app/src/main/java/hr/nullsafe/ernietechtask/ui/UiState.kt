package hr.nullsafe.ernietechtask.ui

sealed class UiState<T> {

    class Loading<T> : UiState<T>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error<T>(val errorMessage: String) : UiState<T>()
}