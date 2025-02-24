package com.example.hw06.data.modules

import androidx.room.Room
import com.example.hw06.BuildConfig.BASE_URL
import com.example.hw06.data.api.CharacterApiService
import com.example.hw06.data.api.LocationApiService
import com.example.hw06.data.local.AppDatabase
import com.example.hw06.data.repository.CharacterRepository
import com.example.hw06.data.repository.EpisodeRepository
import com.example.hw06.data.repository.LocationRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule: Module = module {

    single { provideOkHttpClient() }

    single { provideRetrofit(get()) }

    single { get<Retrofit>().create(CharacterApiService::class.java) }

    single { get<Retrofit>().create(LocationApiService::class.java) }

    single { get<AppDatabase>().favoriteCharacterDao() }

    single { CharacterRepository(get()) }

    single { LocationRepository(get()) }

    single { EpisodeRepository(get()) }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}

private fun provideOkHttpClient() : OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
}