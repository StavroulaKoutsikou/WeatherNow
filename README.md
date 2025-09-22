# WeatherNow 🌤️

Μικρή Android εφαρμογή σε **Kotlin + Jetpack Compose** που εμφανίζει τον καιρό για μία πόλη χρησιμοποιώντας το **OpenWeather API**.  
Υποστηρίζει **EN/EL** γλώσσα, **Dark Mode** toggle και αποθήκευση ρυθμίσεων (last city / γλώσσα / theme) με **DataStore**.

> 🎥 Demo Video:

[Screen_recording_20250922_183905.webm](https://github.com/user-attachments/assets/a09e3b20-2537-44f8-b5b4-298dd828b72e)


## Features

- Αναζήτηση καιρού με βάση πόλη (π.χ. *Athens*, *London*).
- Εναλλαγή γλώσσας **EN / EL** (περνάει το `lang` στο OpenWeather).
- **Dark Mode** toggle, άμεσα στο UI.
- Αποθήκευση: **last city**, **language**, **dark mode** (DataStore).
- Καθαρά **UI states**: Loading / Success / Error.
- **Clean Architecture** + **MVVM** + **Hilt DI**.
- **Coroutines/Flow** (asynchronous calls & reactive UI).
- **OkHttp logging** για εύκολο debug των κλήσεων.
- Φόρτωση weather icon με **Coil**.

## Tech Stack

- **UI:** Jetpack Compose, Material 3  
- **DI:** Hilt  
- **Networking:** Retrofit, OkHttp (logging interceptor), GSON converter  
- **Async:** Kotlin Coroutines, StateFlow  
- **State/Prefs:** DataStore (Preferences)  
- **Images:** Coil  
- **Min SDK:** 24 — **Compile/Target:** 36  

---

## Αρχιτεκτονική & Δομή

**Clean Architecture + MVVM** σε τρία layers:

- `data/` → Retrofit API, DTOs, Repository implementation  
- `domain/` → Models, Repository interfaces  
- `presentation/` → ViewModels, Compose screens, UI state
