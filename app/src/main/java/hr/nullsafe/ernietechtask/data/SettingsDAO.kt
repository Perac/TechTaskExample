package hr.nullsafe.ernietechtask.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SettingsDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSettings(settingsDTO: SettingsDTO)

    @Query("SELECT * FROM settings WHERE serviceId = :serviceId")
    suspend fun findAllSettings(serviceId: String): List<SettingsDTO>

    @Query("UPDATE settings SET enabled = :enabled WHERE id = :id")
    suspend fun toggleSettings(id: String, enabled: Boolean)
}