package com.chillarcards.britto.data.model


import com.google.gson.annotations.SerializedName

data class AddItemResponseModel(
    val msg: String,
    val add_pharmacy_item: String,
    val code: String,
    val data: ItemData
)

data class ItemData(
    val item_uuid: String,
    val item_name: String,
    val brand_name: String,
    val category_name: String,
    val item_status: Int
)

data class AddItemRequestModel(
val business_uuid: String,
val item_name: String,
val brand_uuid: String,
val category_uuid: String,
val item_price: String,
val item_discount: String,
)
