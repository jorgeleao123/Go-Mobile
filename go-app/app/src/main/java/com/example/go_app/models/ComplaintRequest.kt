package com.example.go_app.models

data class ComplaintRequest(
    val type : String,
    val description : String,
    val bo : String,
    val driverName : String,
    val licensePlate : String,
    val state : String,
    val city : String,
    val district : String,
    val dateTimeComplaint : String
)
