package data.remote.api

import data.remote.api.Constants.API_KEY
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.project.domain.model.Currency
import org.example.project.domain.model.RequestState

class CurrencyApiServiceImpl : CurrencyApiService {

    private val httpClient = HttpClient{
        install(ContentNegotiation){
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(HttpTimeout){
            requestTimeoutMillis = 15000
        }

        install(DefaultRequest){
            headers {
                append ( "apikey", API_KEY)
            }
        }
    }

    override fun getLatestExchangeRates() : RequestState<List<Currency>> {
        return try{
            RequestState.Error(message = "e.message.toString()")
        }catch (e : Exception){
            RequestState.Error(message = e.message.toString())
        }
    }
}