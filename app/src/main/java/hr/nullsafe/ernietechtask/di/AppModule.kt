package hr.nullsafe.ernietechtask.di

import android.app.Application
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import hr.nullsafe.ernietechtask.BuildConfig
import hr.nullsafe.ernietechtask.data.ServicesRepository
import hr.nullsafe.ernietechtask.data.SettingsDAO
import hr.nullsafe.ernietechtask.data.TechTaskDatabase
import hr.nullsafe.ernietechtask.network.LocalJsonInterceptor
import hr.nullsafe.ernietechtask.network.TechTaskService
import hr.nullsafe.ernietechtask.viewmodel.ServicesViewModel
import hr.nullsafe.ernietechtask.viewmodel.SettingsViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

val appModule = module {

    single {
        Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
            isLenient = true
            explicitNulls = false
        }
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .addInterceptor(LocalJsonInterceptor(get()))
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(get<OkHttpClient>())
            .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
            .build()
            .create(TechTaskService::class.java)
    }

    single {
        Room.databaseBuilder(
            get<Application>(),
            TechTaskDatabase::class.java,
            "tech_task_database"
        )
            .build()
    }

    single<SettingsDAO> {
        get<TechTaskDatabase>().settingsDao()
    }

    singleOf(::ServicesRepository)

    viewModelOf(::ServicesViewModel)

    viewModelOf(::SettingsViewModel)
}