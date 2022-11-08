package com.example.go_app.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestPublications {

    val baseUrl = "http://10.0.2.2:8080/complaints"

    fun getInstance(): Retrofit {
        return Retrofit
            .Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl(RestPublications.baseUrl)
            .build()
    }

}