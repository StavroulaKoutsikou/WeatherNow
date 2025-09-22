package com.example.weathernow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weathernow.presentation.AppViewModel
import com.example.weathernow.presentation.weather.WeatherScreen
import com.example.weathernow.ui.theme.WeatherNowTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.collectAsState

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appVm: AppViewModel = hiltViewModel()

            val dark by appVm.darkMode.collectAsState()
            val lang by appVm.lang.collectAsState()
            val lastCity by appVm.lastCity.collectAsState()

            WeatherNowTheme(darkTheme = dark) {
                WeatherScreen(
                    currentLang = lang,
                    isDark = dark,
                    onLangChange = appVm::setLang,
                    onToggleDark = appVm::toggleDark,
                    onSaveLastCity = appVm::saveLastCity,
                    initialCity = lastCity
                )
            }
        }
    }
}
