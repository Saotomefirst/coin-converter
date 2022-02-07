package br.com.dio.coinconverter.presentation

import androidx.lifecycle.*
import br.com.dio.coinconverter.data.model.ExchangeResponseValue
import br.com.dio.coinconverter.domain.ListExchangeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HistoryViewModel (
    private val listExchangeUseCase: ListExchangeUseCase
): ViewModel(), LifecycleObserver {

    private val _state = MutableLiveData<State>()
    val state : LiveData<State> = _state

    // roda este método no onCreate da nossa aplicação
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun getExchanges() {
        viewModelScope.launch {
            viewModelScope.launch {
                listExchangeUseCase()
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
    }

    sealed class State {
        object Loading : State()
        // quando o banco estiver vazio, podemos usar isto para mostrar uma tela mais amigável
        //object Empty : State()

        data class Success(val list : List<ExchangeResponseValue>) : State()
        data class Error (val error: Throwable) : State()
    }

}