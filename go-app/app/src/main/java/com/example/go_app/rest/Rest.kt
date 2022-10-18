package com.example.go_app.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Rest {

    //Teste local H2
    // Emulador
    val baseUrl = "http://10.0.2.2:8080/"

    //Produção (nuvem)
    // Emulador
//    val baseUrl = "http://44.210.60.105:8080/"

    // Celular
    // No linux hostname -I
//     val baseUrl = "http://192.168.28.15:8080/"

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