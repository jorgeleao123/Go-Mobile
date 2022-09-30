package com.example.go_app.models

data class UserRequest(
    val name: String,
    val email: String,
    val password: String,
    val role: String,
    val genre: String,
    val birthDate: String,
    val state: String,
    val city: String,
    val district: String
)
