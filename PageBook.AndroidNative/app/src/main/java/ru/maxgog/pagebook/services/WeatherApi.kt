package ru.maxgog.pagebook.services

import android.R.attr.level
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

/*object RetrofitClient {
    private const val BASE_URL = "https://api.weatherapi.com/v1/"

    val weatherApi: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
            .build()
            .create(WeatherApi::class.java)
    }
}*/

interface WeatherApi {
    @GET("forecast.json")
    suspend fun getForecast(
        @Query("q") location: String,
        @Query("days") days: Int = 7,
        @Query("key") apiKey: String = "YOUR_API_KEY" // Замените на реальный ключ
    )//: WeatherResponse
}