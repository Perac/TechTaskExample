package hr.nullsafe.ernietechtask.di

import android.app.Application
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hr.nullsafe.ernietechtask.AppDispatchers
import hr.nullsafe.ernietechtask.BuildConfig
import hr.nullsafe.ernietechtask.DispatcherSettings
import hr.nullsafe.ernietechtask.data.ServicesRepository
import hr.nullsafe.ernietechtask.data.SettingsDAO
import hr.nullsafe.ernietechtask.data.TechTaskDatabase
import hr.nullsafe.ernietechtask.network.LocalJsonInterceptor
import hr.nullsafe.ernietechtask.network.TechTaskService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun providesJson(): Json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        isLenient = true
        explicitNulls = false
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(application: Application): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .addInterceptor(LocalJsonInterceptor(application))
            .build()

    @Provides
    @Singleton
    fun providesTechTaskService(okHttpClient: OkHttpClient, json: Json): TechTaskService =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(TechTaskService::class.java)

    @Provides
    @Singleton
    fun providesDatabase(application: Application): TechTaskDatabase =
        Room.databaseBuilder(
            application,
            TechTaskDatabase::class.java,
            "tech_task_database"
        )
            .build()

    @Provides
    @Singleton
    fun providesSettingsDao(database: TechTaskDatabase): SettingsDAO =
        database.settingsDao()

    @Provides
    @Singleton
    fun providesDispatchersSettings(): DispatcherSettings =
        AppDispatchers()

    @Provides
    @Singleton
    fun providesServicesRepository(
        techTaskService: TechTaskService,
        settingsDAO: SettingsDAO
    ): ServicesRepository =
        ServicesRepository(techTaskService, settingsDAO)

}