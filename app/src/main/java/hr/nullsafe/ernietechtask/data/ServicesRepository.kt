package hr.nullsafe.ernietechtask.data

import hr.nullsafe.ernietechtask.network.TechTaskService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    fun getAllSettings(serviceId: String): Flow<List<Settings>> {
        return settingsDAO
            .findAllSettings(serviceId)
            .map { settings ->
                settings.map { setting ->
                    Settings(
                        setting.settingsId,
                        setting.settingsName,
                        setting.enabled
                    )
                }
            }
    }

    suspend fun toggleSettings(serviceId: String, settingsId: String, enabled: Boolean) {
        settingsDAO.toggleSettings(
            id = "${serviceId}_${settingsId}",
            enabled = enabled
        )
    }
}