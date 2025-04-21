package hr.nullsafe.ernietechtask.data

import hr.nullsafe.ernietechtask.network.TechTaskService

class ServicesRepository(
    private val apiService: TechTaskService,
    private val settingsDAO: SettingsDAO
) {

    suspend fun fetchServices(): List<Service>? {

        val services = apiService.fetchServices()?.services

        services?.forEach { service ->
            service.settings.forEach { settings ->
                settingsDAO.insertSettings(
                    SettingsDTO(
                        id = "${service.id}_${settings.id}",
                        settingsId = settings.id,
                        settingsName = settings.name,
                        enabled = settings.enabled,
                        serviceId = service.id
                    )
                )
            }
        }

        return services
    }

    suspend fun getAllSettings(serviceId: String): List<Settings> {
        return settingsDAO
            .findAllSettings(serviceId)
            .map { settings ->
                Settings(
                    settings.settingsId,
                    settings.settingsName,
                    settings.enabled
                )
            }
    }

    suspend fun toggleSettings(serviceId: String, settingsId: String, enabled: Boolean) {
        settingsDAO.toggleSettings(
            id = "${serviceId}_${settingsId}",
            enabled = enabled
        )
    }
}