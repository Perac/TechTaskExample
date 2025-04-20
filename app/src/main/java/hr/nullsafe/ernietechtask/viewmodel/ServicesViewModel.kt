package hr.nullsafe.ernietechtask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.nullsafe.ernietechtask.network.TechTaskService
import kotlinx.coroutines.launch

class ServicesViewModel(
    private val apiService: TechTaskService
) : ViewModel() {

    init {
        viewModelScope.launch {
            val apiResponse = apiService.fetchServices()

            println(apiResponse)
        }
    }
}