package hr.nullsafe.ernietechtask.data

import kotlinx.serialization.Serializable

@Serializable
data class ServiceApiResponse(
    val services: List<Service>
)

@Serializable
data class Service(
    val id: String,
    val name: String,
    val icon: String,
    val settings: List<Settings>
)

@Serializable
data class Settings(
    val id: String,
    val name: String,
    val enabled: Boolean
)