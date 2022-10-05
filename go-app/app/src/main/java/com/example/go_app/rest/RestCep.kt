package com.example.go_app.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestCep {

    val baseUrl = "https://viacep.com.br/ws/"

    fun getInstance(): Retrofit {
        return Retrofit
            .Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl(RestCep.baseUrl)
            .build()
    }
}