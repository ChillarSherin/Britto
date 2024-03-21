package com.chillarcards.britto.data.model

data class ProvinceModel(
    val msg: String,
    val list_province: String,
    val code: String,
    val data: List<ProvinceData>
)

data class ProvinceData(
    val province_name: String,
    val province_status: Int,
    val province_uuid: String,
    val country_id: Int
)