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
import com.chillarcards.britto.ui.DummyOrderItems
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.ui.interfaces.OnCallBackListner
import com.chillarcards.britto.ui.interfaces.OnIncrementListener
import com.chillarcards.britto.utills.Const
import com.chillarcards.britto.utills.NumberCounterView

class CartItemAdapter(
    private val items: List<DummyOrderItems>,
    private val context: Context?,
    private val activity: FragmentActivity?,
    private val getCallBackUtil: OnCallBackListner,
)
    : RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_cart_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        holder.itemQty.setIncrementListener(object : OnIncrementListener {
            override fun onIncrement(count: Int) {
                holder.itemQty.setCount(count)
                item.cartQty = count.toString()

                val index = Const.cartItems.indexOfFirst { it.id == item.id }
                if (index != -1) {
                    val sellRate = (item.prdsellrate.toFloat() * count).toString()
                    Const.cartItems[index] = DummyOrderItems(
                        item.id,
                        item.prdname,
                        item.prdbrand,
                        item.prdcrncy,
                        item.prdmrp,
                        item.prdofferrate,
                        item.prdsellrate,
                        item.prdoffer,
                        count.toString(),
                        sellRate
                    )
                }
                else {
                    val sellRate = (item.prdsellrate.toFloat() * count).toString()
                    val cartItem = DummyOrderItems(
                        item.id,
                        item.prdname,
                        item.prdbrand,
                        item.prdcrncy,
                        item.prdmrp,
                        item.prdofferrate,
                        item.prdsellrate,
                        item.prdoffer,
                        count.toString(),
                        sellRate
                    )
                    Const.cartItems.add(cartItem)
                }
                notifyDataSetChanged()
                getCallBackUtil.onAddtocartCallback()

            }

            override fun onDecrement(count: Int) {
                val index = Const.cartItems.indexOfFirst { it.id == item.id  }
                if (index != -1) {
                    val updatedQuantity = Const.cartItems[index].cartQty.toInt() - 1
                    if (updatedQuantity > 0) {
                        Const.cartItems[index] = Const.cartItems[index].copy(cartQty = updatedQuantity.toString())
                    } else {
                        Const.cartItems.removeAt(index)
                        getCallBackUtil.onAddtocartCallback()

                    }
                    notifyDataSetChanged()
                    getCallBackUtil.onAddtocartCallback()

                }
            }
        })
        holder.itemQty.getCount()

    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuFrm: CardView = itemView.findViewById(R.id.pharm_frm)
        private val itemName: TextView = itemView.findViewById(R.id.tvMainName)
        private val itemBrand: TextView = itemView.findViewById(R.id.tvDistri)
        private val itemOffer: TextView = itemView.findViewById(R.id.tvOffers)
        private val itemRate: TextView = itemView.findViewById(R.id.tvRate)
        val itemQty: NumberCounterView = itemView.findViewById(R.id.QtyEBTN)

        val count = itemQty.getCount()

        fun bind(item: DummyOrderItems) {
            itemName.text = item.prdname
            itemBrand.text = item.prdbrand
            itemQty.setCount(item.cartQty.toInt())

            item.cartRate=(item.prdsellrate.toFloat() * itemQty.getCount()).toString()

            if(item.prdofferrate == ""){
                itemOffer.visibility=View.GONE
                itemRate.text = item.prdcrncy+item.prdmrp
            }else{
                itemOffer.visibility=View.VISIBLE
                itemRate.text = item.prdcrncy+item.prdofferrate
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

                itemOffer.text = spannableString

            }
        }
    }

}
