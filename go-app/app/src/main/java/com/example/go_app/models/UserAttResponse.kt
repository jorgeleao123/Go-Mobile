package com.example.go_app.models

data class UserAttResponse(
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    val genre: String,
    val colorProfile: String,
    val colorMenu: String,
    val birthDate: String,
    val status: String,
    val searchCounter: Int
)
