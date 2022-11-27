package com.example.go_app.models

import java.time.LocalDateTime

data class NotificationResponse(
    val id: Int,
    val description: String,
    val title: String,
    val hasViewed: Boolean,
    val dateTimeNotification: String,
)
