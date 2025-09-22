package com.example.weathernow.data.remote.dto

data class WeatherResponseDto(
    val name: String,
    val weather: List<WeatherX>,
    val main: Main
) {
    data class WeatherX(
        val description: String,
        val icon: String
    )
    data class Main(
        val temp: Float,
        val feels_like: Float,
        val humidity: Int
    )
}
