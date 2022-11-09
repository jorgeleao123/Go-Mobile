package com.example.go_app.services

import com.example.go_app.models.SaveComplaint
import retrofit2.http.Body
import retrofit2.http.POST

interface ComplaintSavedController {
    @POST("/complaint-saved")
    fun login(@Body body: SaveComplaint)
}