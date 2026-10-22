package uz.ttpu.smd_lab6.task6


sealed class UiState {
    data object Loading : UiState()
    data object Offline : UiState()
    data class Success(val city: String, val temperature: Int) : UiState()
    data class Error(val message: String) : UiState()
}

val temperatures = mapOf("tashkent" to 24, "samarkand" to 22, "bukhara" to 27, "london" to 12)

fun loadWeather(city: String): UiState {
    if (city.lowercase() == "offline") return UiState.Offline
    if (city.isBlank()) return UiState.Error("The city name is empty")
    val temperature = temperatures[city.lowercase()]
        ?: return UiState.Error("No data for '$city'")
    return UiState.Success(city, temperature)
}

fun render(state: UiState) {
    when (state) {
        UiState.Loading -> println("Loading...")
        UiState.Offline -> println("No internet connection")
        is UiState.Success -> println("${state.city}: ${state.temperature} C")
        is UiState.Error -> println("Error: ${state.message}")
    }
}

fun main() {
    while (true) {
        print("City (or 'exit'): ")
        val city = readln().trim()
        if (city == "exit") break
        render(UiState.Loading)
        render(loadWeather(city))
    }
}
