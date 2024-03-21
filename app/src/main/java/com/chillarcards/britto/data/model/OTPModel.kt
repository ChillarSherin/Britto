package com.chillarcards.britto.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Sherin on 18-03-2024.
 */

data class OTPModel(
    val msg: String,
    val verify_otp: String,
    val code: String,
    val user_uuid: String,
    val data: List<Data>
)

data class Data(
    val profile_completion_status: String,
    val Type: String,
    val Business_Type: String,
    val business_user: Any?,
    val business_user_uuid: String,
    @SerializedName("business_user_status")
    val businessUserStatus: Int
)

data class OTPRequestModel(
    val user_phone: String,
    val otp: String
)