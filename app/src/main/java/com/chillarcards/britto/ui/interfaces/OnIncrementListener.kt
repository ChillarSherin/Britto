package com.chillarcards.britto.ui.interfaces

import com.chillarcards.britto.ui.DummyOrderItems

interface OnIncrementListener {

    //fun onIncrement(item: DummyOrderItems)
    fun onIncrement(item: Int)
    fun onDecrement(count: Int)

}