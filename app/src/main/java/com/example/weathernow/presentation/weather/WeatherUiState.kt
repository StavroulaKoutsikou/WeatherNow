package com.example.weathernow.presentation.weather

import com.example.weathernow.domain.model.WeatherInfo

sealed interface WeatherUiState {
    data object Idle : WeatherUiState
    data object Loading : WeatherUiState
    data class Success(val data: WeatherInfo) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}
