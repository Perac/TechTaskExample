package hr.nullsafe.ernietechtask.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, exportSchema = false, entities = [SettingsDTO::class])
abstract class TechTaskDatabase : RoomDatabase() {

    abstract fun settingsDao(): SettingsDAO
}