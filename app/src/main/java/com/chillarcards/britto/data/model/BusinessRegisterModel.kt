package com.chillarcards.britto.data.model

import com.google.gson.annotations.SerializedName


/**
 * Created by Sherin on 07-02-2024.
 */

data class BusinessRegisterModel(
    val msg: String,
    val register_business: String,
    val code: String,
    val data: BusinessUserData
)

data class BusinessUserData(
    val business_user_uuid: String,
    val user_id: Int,
    val business_user_status: Int,
    val business_user_created_date: String,
    val Business_Type: String
)

data class BusinessRegisterResponseModel(
    val business_type_uuid: String,
    val business_name: String,
    val user_uuid: String,
    val business_latitude: Double,
    val business_longitude: Double,
    val business_address_1: String,
    val business_address_2: String,
    val business_city: String,
    val province_uuid: String,
    val business_zip: String,
    val business_phone: String,
    val business_email: String,
)

data class BusinessProfileResponseModel(
    val file_base64: String,
    val business_user_uuid: String
)
data class BusinessProfileModel(
    val msg: String,
    val business_profile_picture_upload: String,
    val code: String
)
