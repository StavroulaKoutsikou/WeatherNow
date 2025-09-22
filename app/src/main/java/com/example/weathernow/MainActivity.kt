package com.example.weathernow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weathernow.presentation.AppViewModel
import com.example.weathernow.presentation.weather.WeatherScreen
import com.example.weathernow.ui.theme.WeatherNowTheme

@dagger.hilt.android.AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appVm: AppViewModel = hiltViewModel()
            val dark = appVm.darkMode.value
            val lang = appVm.lang.value

            WeatherNowTheme(darkTheme = dark) {
                WeatherScreen(
                    currentLang = lang,
                    isDark = dark,
                    onLangChange = appVm::setLang,
                    onToggleDark = appVm::toggleDark,
                    onSaveLastCity = appVm::saveLastCity
                )
            }
        }
    }
}
