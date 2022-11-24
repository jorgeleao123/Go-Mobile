package com.example.go_app.services

import com.example.go_app.models.ComplaintsResponse
import com.example.go_app.models.UserRequest
import com.example.go_app.models.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface Publications {
    
    @POST("/complaints/{userId}")
    fun postPublication(
        @Path("userId") userId : Int,
        @Body body: UserRequest
    ) : Call<List<ComplaintsResponse>>

    @GET("/complaints/Id/{complaintId}")
    fun getPublication(
        @Path("complaintId") userId : Int
    ) : Call<List<ComplaintsResponse>>

    @GET("/complaints/user/{userId}")
    fun getPublicationUser(
        @Path("userId") userId : Int
    ) : Call<List<ComplaintsResponse>>

    @GET("/complaints/cep")
    fun getPublicationsByCep(
        @Header("cep") cep : String
    ) : Call<List<ComplaintsResponse>>

    @GET("/complaints/city")
    fun getPublicationsByCity(
        @Header("city") city : String
    ) : Call<List<ComplaintsResponse>>

    @GET("/complaints/district")
    fun getPublicationsByDistrict(
        @Header("district") district : String
    ) : Call<List<ComplaintsResponse>>

    @GET("/complaints/state")
    fun getPublicationsByState(
        @Header("state") state : String
    ) : Call<List<ComplaintsResponse>>

    @GET("/complaints/license/{license}")
    fun getPublicationsByLicense(
        @Path("license") license : String
    ) : Call<List<ComplaintsResponse>>

}
