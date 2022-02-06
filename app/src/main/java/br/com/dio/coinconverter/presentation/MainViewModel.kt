package br.com.dio.coinconverter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dio.coinconverter.data.model.ExchangeResponseValue
import br.com.dio.coinconverter.domain.GetExchangeValueUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel (
    private val getExchangeValueUseCase: GetExchangeValueUseCase
        ) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state : LiveData<State> = _state

    fun getExchangeValue (coins: String) {
        // iniciamos uma corotina e a partir dela chamamos o getExchangeValueUseCase
        viewModelScope.launch {
            getExchangeValueUseCase(coins)
                .flowOn(Dispatchers.Main)
                .onStart {
                    // sempre que o use case for chamado, ele cai aqui
                    // usamos para mostrar o progresso
                    _state.value = State.Loading
                }
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    // havendo sucesso, o resultado cai aqui
                    _state.value = State.Success(it)
                }
        }
    }

    sealed class State {
        object Loading : State()

        data class Success(val exchange : ExchangeResponseValue) : State()
        data class Error (val error: Throwable) : State()
    }
}