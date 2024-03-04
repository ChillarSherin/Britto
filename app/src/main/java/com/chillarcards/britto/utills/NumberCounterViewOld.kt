package com.chillarcards.britto.utills

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.chillarcards.britto.data.model.CartItemModel
import com.chillarcards.britto.databinding.ViewNumberCounterBinding
import com.chillarcards.britto.ui.DummyOrderItems


class NumberCounterViewOld @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var count = 1
    private lateinit var binding: ViewNumberCounterBinding
    var CartItems: List<DummyOrderItems> = ArrayList()

    init {
        initView()
    }

    private fun initView() {
        binding = ViewNumberCounterBinding.inflate(LayoutInflater.from(context), this)

        updateCount()

        binding.btnIncrement.setOnClickListener {
            count++
            updateCount()
        }

        binding.btnDecrement.setOnClickListener {
            if (count > 0) {
                count--
                updateCount()
            }
        }
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
}
