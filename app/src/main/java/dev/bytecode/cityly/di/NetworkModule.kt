package dev.bytecode.cityly.di

import dagger.Provides
import dev.bytecode.cityly.network.Constants
import dev.bytecode.cityly.network.UrbanAreasService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

object NetworkModule {
    @Singleton
    @Provides
    fun provideUrbanAreasService(): UrbanAreasService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(UrbanAreasService::class.java)
    }
}