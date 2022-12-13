package com.example.go_app.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Rest {

    //Mude aqui para chamar diferentes bancos

    /* ----------- Develop - Teste local H2 ----------- */
    // Emulador - IP padrão da máquina
//    val baseUrl = "http://10.0.2.2:8080/"
    // Celular - IP da rede (No linux hostname -I)
    // Celular e máquina conectados no mesmo wifi/rede
//    val baseUrl = "http://192.168.28.15:8080/"

    /* ----------- Produção (nuvem aws) ----------- */
//    val baseUrl = "http://44.210.60.105:8080/"

    /* ----------- Produção (nuvem azure) ----------- */
    val baseUrl = "https://goapp-api.azurewebsites.net/"

    fun getInstance(): Retrofit {
        return Retrofit
            .Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl(baseUrl)
            .build()
    }

}