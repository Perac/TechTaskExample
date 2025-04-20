package hr.nullsafe.ernietechtask.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class SettingsDTO(
    @PrimaryKey(autoGenerate = false)
    val id: String, // serviceId_settingsId
    val settingsId: String,
    val settingsName: String,
    val enabled: Boolean,
    val serviceId: String
)