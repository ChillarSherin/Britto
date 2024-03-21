package com.chillarcards.britto.data.model

data class ItemResponseModel(
    val msg: String,
    val list_pharmacy_items: String,
    val code: String,
    val data: List<Item>
)

data class Item(
    val item_uuid: String,
    val item_name: String,
    val item_brand: String,
    val item_category: String,
    val item_price: Double,
    val item_discount: Double,
    val item_status: Int
)

data class ItemRequestModel(
     val business_uuid: String
)
