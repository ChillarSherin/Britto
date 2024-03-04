package com.chillarcards.britto.utills

import com.chillarcards.britto.data.model.CartItemModel

class CartModel {
    private val cartItems: MutableList<CartItemModel> = mutableListOf()

    fun addItem(item: CartItemModel) {
        cartItems.add(item)
    }

    fun incrementQuantity(itemName: String) {
        val item = cartItems.find { it.itemName == itemName }
        item?.let {
            it.quantity++
        }
    }

    fun decrementQuantity(itemName: String) {
        val item = cartItems.find { it.itemName == itemName }
        item?.let {
            if (it.quantity > 0) {
                it.quantity--
            }
        }
    }

    fun getTotalPrice(): Double {
        var totalPrice = 0.0
        for (item in cartItems) {
            totalPrice += item.price * item.quantity
        }
        return totalPrice
    }
}
