package ru.maxgog.pagebook.repositories

import ru.maxgog.pagebook.models.WeatherData
import ru.maxgog.pagebook.services.WeatherApi

/*class WeatherRepository(private val weatherApi: WeatherApi) {
    suspend fun getWeatherForecast(location: String): List<WeatherData> {
        // Здесь должна быть реализация API вызова
        // Это примерная реализация
        return try {
            val response = weatherApi.getForecast(location)
            //response.toWeatherDataList()
        } catch (e: Exception) {
            emptyList()
        } as List<WeatherData>
    }
}*/