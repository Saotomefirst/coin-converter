package br.com.dio.coinconverter.data.repository

import br.com.dio.coinconverter.data.model.ExchangeResponse
import br.com.dio.coinconverter.data.services.AwesomeService
import kotlinx.coroutines.flow.flow

class CoinRepositoryImplementer(
    private val service: AwesomeService
) : CoinRepository {
    override suspend fun getExchangeValue(coins: String) = flow {
        val exchangeValue = service.exchangeValue(coins)
        val exchange = exchangeValue.values.first()
        // esse método é do coroutines
        emit(exchange)
    }
}