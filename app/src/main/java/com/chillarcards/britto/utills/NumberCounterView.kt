package com.chillarcards.britto.utills

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.chillarcards.britto.databinding.ViewNumberCounterBinding
import com.chillarcards.britto.ui.DummyOrderItems
import com.chillarcards.britto.ui.interfaces.OnIncrementListener

class NumberCounterView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var count = 1
    private lateinit var binding: ViewNumberCounterBinding
    private var cartItems: MutableList<DummyOrderItems> = mutableListOf()
    private var incrementListener: OnIncrementListener? = null

    init {
        initView()
    }

    private fun initView() {
        binding = ViewNumberCounterBinding.inflate(LayoutInflater.from(context), this)

        updateCount()

        binding.btnIncrement.setOnClickListener {
            count++
            updateCount()
            incrementListener?.onIncrement(count)

        }

        binding.btnDecrement.setOnClickListener {
            if (count > 0) {
                count--
                updateCount()
                incrementListener?.onDecrement(count)
            }
        }
    }

    fun setIncrementListener(listener: OnIncrementListener) {
        this.incrementListener = listener
    }

    private fun updateCount() {
        binding.tvCount.text = count.toString()
    }

    fun getCount(): Int {
        return count
    }

    fun setCount(count: Int) {
        this.count = count
        updateCount()
    }


//
//    // Add the item to cartItems
//    private fun itemToCart() {
//        val item = DummyOrderItems(
//            id = cartItems.size + 1,
//            prdname = "Product Name",
//            prdbrand = "Product Brand",
//            prdcrncy = "Currency",
//            prdmrp = "MRP",
//            prdofferrate = "Offer Rate",
//            prdoffer = "Offer",
//            cartQty = count.toString()
//        )
//        cartItems.add(item)
//    }

    // Get the list of items in the cart
    fun getCartItems(): List<DummyOrderItems> {
        return cartItems
    }


}
