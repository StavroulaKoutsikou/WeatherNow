package com.example.weathernow.domain.repository

import com.example.weathernow.domain.model.WeatherInfo

interface WeatherRepository {
    suspend fun getCurrent(city: String, lang: String): WeatherInfo
}
