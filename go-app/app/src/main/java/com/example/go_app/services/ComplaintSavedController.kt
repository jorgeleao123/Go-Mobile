package com.example.go_app.services

import com.example.go_app.models.ComplaintsResponse
import com.example.go_app.models.SaveComplaint
import retrofit2.Call
import retrofit2.http.*

interface ComplaintSavedController {
    @POST("/complaint-saved")
    fun saveComplaint(@Body body: SaveComplaint)

    @GET("/complaint-saved/{userId}")
    fun getComplaintSave(
        @Path("userId") userId: Int,
    ): Call<List<ComplaintsResponse>>

    @DELETE("/complaint-saved/{userId}/{complaintId}")
    fun removeComplaintSave(
        @Path("userId") userId: Int,
        @Path("complaintId") complaintId: Int,
    )
}