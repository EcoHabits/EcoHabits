package com.ecohabits.data.remote

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiMappingTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var weatherApi: WeatherApi
    private lateinit var locationApi: LocationApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        
        weatherApi = retrofit.create(WeatherApi::class.java)
        locationApi = retrofit.create(LocationApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `weatherApi returns correct mapping`() = runBlocking {
        val mockResponse = MockResponse()
            .setBody("""
                {
                    "current": {
                        "weather_code": 1
                    }
                }
            """.trimIndent())
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        val response = weatherApi.getWeatherState(0.0, 0.0)

        assertEquals(1, response.current.weatherCode)
    }

    @Test
    fun `locationApi returns correct mapping`() = runBlocking {
        val mockResponse = MockResponse()
            .setBody("""
                {
                    "address": {
                        "city": "Madrid"
                    }
                }
            """.trimIndent())
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        val response = locationApi.getCity(0.0, 0.0)

        assertEquals("Madrid", response.address.city)
    }
}
