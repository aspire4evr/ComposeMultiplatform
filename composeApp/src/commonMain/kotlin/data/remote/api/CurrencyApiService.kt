package data.remote.api

import org.example.project.domain.model.Currency
import org.example.project.domain.model.RequestState

interface CurrencyApiService {
    fun getLatestExchangeRates() : RequestState<List<Currency>>
}