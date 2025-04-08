package ru.maxgog.pagebook.models

import kotlinx.datetime.LocalDate

data class WeatherData(
    val temperature: Double,
    val condition: String,
    val icon: String,
    val date: LocalDate
)