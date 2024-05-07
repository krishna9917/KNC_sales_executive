package com.application.knc.model

data class Login(
    val access_token: String,
    val `data`: UserData,
    val message: String,
    val status: Boolean,
    val support: CustomerSupportDetails
)