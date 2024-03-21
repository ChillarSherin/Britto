package com.chillarcards.britto.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Sherin on 07-02-2024.
 */

data class RegisterModel(
    val msg: String,
    val create_otp: String,
    val code: String,
    val data: List<Any?>,
)
data class RegisterRequestModel(
    val user_phone: String
)
