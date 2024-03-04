package com.chillarcards.britto.ui.adapter

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.chillarcards.britto.R
import com.chillarcards.britto.ui.DummyItem
import com.chillarcards.britto.ui.DummyMenu
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel

class OrderViewAdapter (private val items: List<DummyItem>,
                        context: Context?,
                        private val activity: FragmentActivity?,
                        private val pageFrom : String,
                        private val getAdapterUtil: IAdapterViewUtills,
)
    : RecyclerView.Adapter<OrderViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_order_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuFrm: CardView = itemView.findViewById(R.id.order_frm)
        private val orderItem: TextView = itemView.findViewById(R.id.ord_item)
        private val orderQty: TextView = itemView.findViewById(R.id.ord_qty)
        private val orderAmt: TextView = itemView.findViewById(R.id.ord_amt)

        fun bind(item: DummyItem) {
            orderItem.text = item.prdname
            orderQty.text = item.cartQty
            if (item.prdofferrate == "") {
                orderAmt.text = item.prdcrncy + item.prdmrp
            } else {
                orderAmt.text = item.prdcrncy + item.prdofferrate
            }
        }
    }

}
