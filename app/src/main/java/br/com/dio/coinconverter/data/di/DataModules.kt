package br.com.dio.coinconverter.data.di

import android.util.Log
import br.com.dio.coinconverter.data.services.AwesomeService
import com.google.gson.GsonBuilder
import com.google.gson.internal.GsonBuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//DI - Dependencies Injection - veja mais em
// https://medium.com/collabcode/inje%C3%A7%C3%A3o-de-depend%C3%AAncia-no-kotlin-com-koin-4d093f80cb63
object DataModules {

    private const val HTTP_TAG = "OkHTTP"
    //função responsável por carregar todas as dependências do projeto
    fun load () {
        loadKoinModules(networkModule())
    }

    private fun networkModule(): Module {
        return module{
            // para termos sempre o mesmo objeto sendo retornado, usamos o single
            single {
                val interceptor = HttpLoggingInterceptor {
                    Log.e(HTTP_TAG, ": $it")
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                val build = OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
                build // este e o retorno do tipo HttpClient
            }

            single {
                GsonConverterFactory.create(GsonBuilder().create())
            }

            single {
                // por causa dos tipos declarados na funcao e dos singles acima,
                // o Kotlin já sabe que objetos pegar para colocar na funcao!
                createService<AwesomeService>(get(), get())
            }
        }
    }

    private inline fun <reified T> createService (client: OkHttpClient, factory: GsonConverterFactory): T {
        return Retrofit.Builder()
            .baseUrl("https://economia.awesomeapi.com.br")
            .client(client)
            .addConverterFactory(factory)
            .build()
            .create(T::class.java)
    }
}