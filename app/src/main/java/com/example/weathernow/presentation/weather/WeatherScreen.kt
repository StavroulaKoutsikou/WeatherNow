package com.example.weathernow.presentation.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.weathernow.domain.model.WeatherInfo
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    currentLang: String,
    isDark: Boolean,
    onLangChange: (String) -> Unit,
    onToggleDark: () -> Unit,
    onSaveLastCity: (String) -> Unit,
    vm: WeatherViewModel = hiltViewModel()
) {
    var city by remember { mutableStateOf("") }
    val state by vm.state.collectAsState()

    // (Προαιρετικό) Auto-search με debounce:
    LaunchedEffect(currentLang) {
        // reset or keep - optional
    }
    LaunchedEffect(city, currentLang) {
        snapshotFlow { city }
            .filter { it.length >= 3 }
            .distinctUntilChanged()
            .collect {
                // μικρή καθυστέρηση για debounce
                delay(400)
                vm.fetch(it, currentLang)
            }
    }

    // Αν πετύχει το fetch, αποθήκευσε last city
    LaunchedEffect(state) {
        val s = state
        if (s is WeatherUiState.Success) onSaveLastCity(s.data.city)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("WeatherNow") },
                actions = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Dark")
                        Switch(checked = isDark, onCheckedChange = { onToggleDark() })
                    }
                }
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilterChip(
                    selected = currentLang == "en",
                    onClick = { onLangChange("en") },
                    label = { Text("EN") }
                )
                FilterChip(
                    selected = currentLang == "el",
                    onClick = { onLangChange("el") },
                    label = { Text("EL") }
                )
            }

            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text(if (currentLang == "el") "Πόλη" else "City") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { vm.fetch(city, currentLang) },
                modifier = Modifier.fillMaxWidth()
            ) { Text(if (currentLang == "el") "Αναζήτηση" else "Search") }

            when (val s = state) {
                is WeatherUiState.Idle -> Text(if (currentLang == "el") "Πληκτρολόγησε πόλη" else "Type a city")
                is WeatherUiState.Loading -> {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        CircularProgressIndicator()
                    }
                }
                is WeatherUiState.Error -> Text(
                    s.message,
                    color = MaterialTheme.colorScheme.error
                )
                is WeatherUiState.Success -> WeatherCard(s.data)
            }
        }
    }
}

@Composable
private fun WeatherCard(info: WeatherInfo) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(info.iconUrl()),
                contentDescription = null,
                modifier = Modifier.size(72.dp)
            )
            Spacer(Modifier.width(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(info.city, style = MaterialTheme.typography.titleLarge)
                Text("${info.temperature}°C • feels ${info.feelsLike}°C")
                Text("Humidity ${info.humidity}%")
                Text(info.description)
            }
        }
    }
}
