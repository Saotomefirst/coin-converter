package br.com.dio.coinconverter.data.model

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

data class ExchangeResponseValue (
    val code: String,
    val codein: String,
    val name: String,
    val high: String,
    val low: String,
    val varBid: String,
    val pctChange: String,
    val bid: String,
    val ask: String,
    val timestamp: String,
    val createDate: String
)