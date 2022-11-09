package com.example.go_app.services

import com.example.go_app.models.UserRequest
import com.example.go_app.models.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface Publications {

    @POST("/complaints/{userId}")
    fun postPublication(
        @Body body: UserRequest
    ) : Call<UserResponse>

    @GET("/complaints/cep")
    fun getPublicationsByCep(
        @Header header: Header
    ) : Call<UserResponse>

    @GET("/complaints/{city}")
    fun getPublicationsByCity(
        @Body body: UserRequest
    ) : Call<UserResponse>

    @GET("/complaints/{city}")
    fun getPublicationsByCity(
        @Body body: UserRequest
    ) : Call<UserResponse>

    @GET("/complaints/{city}")
    fun getPublicationsByCity(
        @Body body: UserRequest
    ) : Call<UserResponse>

    @GET("/complaints/{city}")
    fun getPublicationsByCity(
        @Body body: UserRequest
    ) : Call<UserResponse>


}
