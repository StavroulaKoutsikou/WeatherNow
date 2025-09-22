package com.example.weathernow.domain.model

data class WeatherInfo(
    val city: String,
    val temperature: Float,
    val feelsLike: Float,
    val humidity: Int,
    val description: String,
    val iconCode: String
) {
    fun iconUrl(): String = "https://openweathermap.org/img/wn/${iconCode}@2x.png"
}
