package br.com.dio.coinconverter.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
Não seja burro de fazer isso na mão, use o site!
- pegue a referência de resposta no site https://docs.awesomeapi.com.br/api-de-moedas ,
 ->Função GET Moedas -> resposta 200

jogue no site https://app.quicktype.io/
-> Configure como [ Kotlin ] , [ Just Types ] e deixe o package quieto

O que você vê abaixo é o resultado copiado e colado, menos o package,
que foi criado aqui no Android Studio
 */
typealias ExchangeResponse = HashMap<String, ExchangeResponseValue>

@Entity(tableName = "tb_exchange")
data class ExchangeResponseValue(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    val code: String,
    val codein: String,
    val name: String,
    val bid: Double,

)
