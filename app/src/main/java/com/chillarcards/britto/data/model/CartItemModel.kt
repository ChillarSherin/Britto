package com.chillarcards.britto.data.model

data class CartItemModel(
    val itemName: String,
    val price: Double,
    var quantity: Int
)