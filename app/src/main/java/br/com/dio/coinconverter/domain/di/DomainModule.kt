package br.com.dio.coinconverter.domain.di

import br.com.dio.coinconverter.domain.GetExchangeValueUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {
    fun load() {
        loadKoinModules(useCaseModules())
    }

    private fun useCaseModules(): Module {
        return module {
            // toda vez que precisar deste Use Case, retornamos uma nova instância
            factory {
                GetExchangeValueUseCase(get())
            }
        }
    }
}