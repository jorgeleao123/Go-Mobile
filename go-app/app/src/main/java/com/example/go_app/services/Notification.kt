package com.example.go_app.services

import com.example.go_app.models.NotificationResponse
import com.example.go_app.models.SuccessResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface Notification {
    @GET("/notifications/user/{userId}")
    fun getNotifications(
        @Path("userId") userId: Int,
    ): Call<List<NotificationResponse>>

    @PATCH("/notifications/view/{userId}")
    fun patchNotifications(
        @Path("userId") userId: Int,
    ): Call<SuccessResponse>
}