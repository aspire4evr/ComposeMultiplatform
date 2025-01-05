package data.remote.api

import data.remote.api.Constants.API_KEY
import data.remote.api.Constants.ENDPOINT
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.project.domain.model.ApiResponse
import org.example.project.domain.model.Currency
import org.example.project.domain.model.RequestState

class CurrencyApiServiceImpl : CurrencyApiService {

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 15000
        }

        install(DefaultRequest) {
            headers {
                append("apikey", API_KEY)
            }
        }
    }

    override suspend fun getLatestExchangeRates(): RequestState<List<Currency>> {
        return try {
            val response = httpClient.get(ENDPOINT)
            if (response.status.value == 200) {
                println("Response : ${response.body<String>()}")
                val apiResponse = Json.decodeFromString<ApiResponse>(response.body())
                val timpeStamp = apiResponse.metadata.lastUpdatedAt
                //save time stamp

                RequestState.Success(data = apiResponse.data.values.toList())
            } else {
                println("HttP error Http : Error code ${response.status}")
                RequestState.Error(message = "Http : Error code ${response.status} ")
            }
        } catch (e: Exception) {
            RequestState.Error(message = e.message.toString())
        }
    }
}