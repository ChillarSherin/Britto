package com.chillarcards.britto.data.model

import com.google.gson.annotations.SerializedName


/**
 * Created by Sherin on 07-02-2024.
 */
data class BusinessModel(
    val msg: String,
    val list_business_types: String,
    val code: String,
    val data: List<Daum>
)

data class Daum(
    val business_type_status: Long,
    val business_type_uuid: String,
    val business_model: Long,
    val business_type_name: String,
    val business_type_id: Long
)

