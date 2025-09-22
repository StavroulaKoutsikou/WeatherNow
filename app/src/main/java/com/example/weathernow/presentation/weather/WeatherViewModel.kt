package com.example.weathernow.presentation.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathernow.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repo: WeatherRepository
) : ViewModel() {

    private val _state = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val state = _state.asStateFlow()

    fun fetch(city: String, lang: String) {
        if (city.isBlank()) {
            _state.value = WeatherUiState.Error("Type a city")
            return
        }
        viewModelScope.launch {
            _state.value = WeatherUiState.Loading
            try {
                val info = repo.getCurrent(city.trim(), lang)
                _state.value = WeatherUiState.Success(info)
            } catch (e: retrofit2.HttpException) {
                _state.value = if (e.code() == 404) {
                    WeatherUiState.Error("City not found")
                } else WeatherUiState.Error("Server error (${e.code()})")
            } catch (e: java.io.IOException) {
                _state.value = WeatherUiState.Error("Check your internet connection")
            } catch (e: Exception) {
                _state.value = WeatherUiState.Error("Unexpected error")
            }
        }
    }

}
