package hr.nullsafe.ernietechtask.network

import hr.nullsafe.ernietechtask.data.ServiceApiResponse
import retrofit2.http.GET

interface TechTaskService {

    @GET("services")
    suspend fun fetchServices(): ServiceApiResponse
}