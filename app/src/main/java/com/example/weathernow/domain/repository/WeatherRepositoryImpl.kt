package com.example.weathernow.data.repository

import com.example.weathernow.data.remote.WeatherApi
import com.example.weathernow.domain.model.WeatherInfo
import com.example.weathernow.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val api: WeatherApi,
    private val apiKey: String
) : WeatherRepository {

    override suspend fun getCurrent(city: String, lang: String): WeatherInfo {
        val dto = api.getCurrentWeather(city = city, apiKey = apiKey, lang = lang)
        val w = dto.weather.firstOrNull()
        return WeatherInfo(
            city = dto.name,
            temperature = dto.main.temp,
            feelsLike = dto.main.feels_like,
            humidity = dto.main.humidity,
            description = w?.description ?: "N/A",
            iconCode = w?.icon ?: "01d"
        )
    }
}
