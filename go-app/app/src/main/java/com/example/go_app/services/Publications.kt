package com.example.go_app.services

import com.example.go_app.models.ComplaintRequest
import com.example.go_app.models.ComplaintsResponse
import com.example.go_app.models.UserRequest
import com.example.go_app.models.UserResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.io.File

interface Publications {
    
    @POST("/complaints/{userId}")
    fun postPublication(
        @Path("userId") userId : Int,
        @Body body: ComplaintRequest
    ) : Call<ComplaintsResponse>

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

    @Multipart
        @PUT("/complaints/archive/{userId}/{complaintId}")
    fun putImageInComplaint(
        @Path("userId") userId : Int,
        @Path("complaintId") complaintId : Int,
        @Part file: MultipartBody.Part?,
    ) : Call<ComplaintsResponse>


}
