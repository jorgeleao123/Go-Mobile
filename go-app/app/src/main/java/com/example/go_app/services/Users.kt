package com.example.go_app.services

import com.example.go_app.models.UserAttRequest
import com.example.go_app.models.UserAttResponse
import com.example.go_app.models.UserRequest
import com.example.go_app.models.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface Users {

    @POST("/users")
    fun postUser(
        @Body body: UserRequest
    ): Call<UserResponse>

    @POST("/users/login/{email}/{password}")
    fun loginUser(
        @Path("email") email: String,
        @Path("password") password: String,
        ): Call<UserResponse>

    @PUT("/users/{id}")
    fun attUser(
        @Path("id") id: Int,
        @Body body: UserAttRequest,
    ): Call<UserAttResponse>

}