package br.com.dio.coinconverter.data.repository

import br.com.dio.coinconverter.core.exceptions.RemoteException
import br.com.dio.coinconverter.data.model.ErrorResponse
import br.com.dio.coinconverter.data.model.ExchangeResponse
import br.com.dio.coinconverter.data.services.AwesomeService
import com.google.gson.Gson
import kotlinx.coroutines.flow.flow
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException

class CoinRepositoryImplementer(
    private val service: AwesomeService
) : CoinRepository {
    override suspend fun getExchangeValue(coins: String) = flow {
        try{
            val exchangeValue = service.exchangeValue(coins)
            val exchange = exchangeValue.values.first()
            // esse método é do coroutines
            emit(exchange)
        }
        catch (e: HttpException) {
            //{"status":404,"code":"CoinNotExists","message":"moeda nao encontrada USD-USD"}
            val json = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(json, ErrorResponse::class.java)
            throw RemoteException(errorResponse.message)
        }

    }
}