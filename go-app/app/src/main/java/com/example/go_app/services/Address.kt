package com.example.go_app.services

import com.example.go_app.models.AddressResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Address {

    @GET("/addresses/users/{userId}")
    fun getUserAddressById(
        @Path("userId") userId: Int
    ) : Call<List<AddressResponse>>

}