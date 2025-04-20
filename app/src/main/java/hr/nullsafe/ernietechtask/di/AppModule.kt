package hr.nullsafe.ernietechtask.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import hr.nullsafe.ernietechtask.BuildConfig
import hr.nullsafe.ernietechtask.network.LocalJsonInterceptor
import hr.nullsafe.ernietechtask.network.TechTaskService
import hr.nullsafe.ernietechtask.viewmodel.ServicesViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
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

    singleOf(::ServicesViewModel)
}