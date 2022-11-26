package com.example.go_app.services

import com.example.go_app.models.ComplaintsResponse
import com.example.go_app.models.SaveComplaint
import com.example.go_app.models.SuccessResponse
import retrofit2.Call
import retrofit2.http.*

interface ComplaintSavedController {
    @POST("/complaints-saved")
    fun saveComplaint(@Body body: SaveComplaint): Call<SuccessResponse>

    @GET("/complaints-saved/{userId}")
    fun getComplaintSave(
        @Path("userId") userId: Int,
    ): Call<List<ComplaintsResponse>>

    @DELETE("/complaints-saved/{userId}/{complaintId}")
    fun removeComplaintSave(
        @Path("userId") userId: Int,
        @Path("complaintId") complaintId: Int,
    ): Call<SuccessResponse>
}