package com.example.weathernow.di

import com.example.weathernow.data.local.SettingsDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {
    @Provides @Singleton
    fun provideSettings(@ApplicationContext ctx: Context): SettingsDataStore =
        SettingsDataStore(ctx)
}
