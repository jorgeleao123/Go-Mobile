package com.example.go_app.models

data class ComplaintsResponse(
    val id: Int,
    val description: String,
    val status: String,
    val bo: String,
    val type: String,
    val driver: DriveResponse,
    val user: UserResponse,
    val address: AddressResponse,
)
