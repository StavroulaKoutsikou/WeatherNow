package com.example.weathernow.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathernow.data.local.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class AppViewModel @Inject constructor(
    private val settings: SettingsDataStore
) : ViewModel() {

    val darkMode = settings.darkFlow.stateIn(viewModelScope, SharingStarted.Eagerly, false)
    val lang     = settings.langFlow.stateIn(viewModelScope, SharingStarted.Eagerly, "en")
    val lastCity = settings.lastCityFlow.stateIn(viewModelScope, SharingStarted.Eagerly, "")

    fun toggleDark() = viewModelScope.launch {
        settings.setDark(!darkMode.value)
    }

    fun setLang(l: String) = viewModelScope.launch {
        settings.setLang(l)
    }

    fun saveLastCity(c: String) = viewModelScope.launch {
        settings.setLastCity(c)
    }
}
