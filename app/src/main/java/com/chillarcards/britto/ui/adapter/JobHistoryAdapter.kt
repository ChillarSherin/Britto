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

class JobHistoryAdapter (private val items: List<DummyItem>,
                         context: Context?,
                         private val activity: FragmentActivity?,
                         private val pageFrom : String,
                         private val getAdapterUtil: IAdapterViewUtills,
)
    : RecyclerView.Adapter<JobHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_job_history, parent, false)
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

        fun bind(item: DummyItem) {
            orderItem.text = item.prdname
            orderBrand.text = item.prdbrand
        }
    }

}
