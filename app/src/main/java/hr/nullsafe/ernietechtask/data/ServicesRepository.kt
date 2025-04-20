package hr.nullsafe.ernietechtask.data

import hr.nullsafe.ernietechtask.network.TechTaskService

class ServicesRepository(
    private val apiService: TechTaskService
) {

    suspend fun fetchServices(): List<Service> {
        return apiService.fetchServices().services
    }
}