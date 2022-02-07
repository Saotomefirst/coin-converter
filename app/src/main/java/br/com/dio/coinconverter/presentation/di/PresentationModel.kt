package br.com.dio.coinconverter.presentation.di

import br.com.dio.coinconverter.presentation.HistoryViewModel
import br.com.dio.coinconverter.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object PresentationModel {
    fun load() {
        loadKoinModules(viewModelModules())
    }

    private fun viewModelModules(): Module {
        return module {
            // porque o Koin tem a sua dependencia koin-android-ext, não precisamos do single ou do factory
            // usamos a injecao de viewModel
            viewModel { HistoryViewModel(get())}
            viewModel { MainViewModel(get(), get())}
        }
    }
}