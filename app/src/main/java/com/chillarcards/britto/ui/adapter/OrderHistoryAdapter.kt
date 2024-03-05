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

class OrderHistoryAdapter (private val items: List<DummyItem>,
                           context: Context?,
                           private val activity: FragmentActivity?,
                           private val pageFrom : String,
                           private val getAdapterUtil: IAdapterViewUtills,
)
    : RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_order_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuFrm: CardView = itemView.findViewById(R.id.order_frm)
        private val orderItem: TextView = itemView.findViewById(R.id.ord_name)
        private val orderId: TextView = itemView.findViewById(R.id.ord_id)
        private val orderBrand: TextView = itemView.findViewById(R.id.tvDistri)
        private val orderOffrAmt: TextView = itemView.findViewById(R.id.tvOffers)
        private val orderAmt: TextView = itemView.findViewById(R.id.ord_amt)
        private val orderDate: TextView = itemView.findViewById(R.id.ord_date)
        private val orderStatus: TextView = itemView.findViewById(R.id.order_status)

        fun bind(item: DummyItem) {
            orderItem.text = item.prdname
            orderBrand.text = item.prdbrand
            if(item.prdofferrate == ""){
                orderOffrAmt.visibility=View.GONE
                orderAmt.text = item.prdcrncy+item.prdmrp
            }else{
                orderOffrAmt.visibility=View.VISIBLE
                orderAmt.text = item.prdcrncy+item.prdofferrate
                val mrpText = "MRP ${item.prdcrncy}${item.prdmrp}"
                val offerText = "${item.prdoffer}% off"

                val spannableString = SpannableString("$mrpText $offerText")

                spannableString.setSpan(
                    StrikethroughSpan(),
                    0,  // Start index of MRP text
                    mrpText.length, // End index of MRP text
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannableString.setSpan(
                    ForegroundColorSpan(Color.GREEN),
                    mrpText.length + 1,  // Start index of offer text
                    spannableString.length, // End index of offer text
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                orderOffrAmt.text = spannableString

            }
        }
    }

}
