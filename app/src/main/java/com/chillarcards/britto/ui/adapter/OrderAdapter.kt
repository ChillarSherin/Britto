package com.chillarcards.britto.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chillarcards.britto.R
import com.chillarcards.britto.ui.DummyMenu
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.squareup.picasso.Picasso

class OrderAdapter (private val items: List<DummyMenu>,
                    context: Context?,
                    private val activity: FragmentActivity?,
                    private val pageFrom : String,
                    private val getAdapterUtil: IAdapterViewUtills,
)
    : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_order, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.menuFrm.setOnClickListener {
            val commonDObj = CommonDBaseModel()
            commonDObj.mastIDs = item.id.toString()
            commonDObj.itmName = item.name

            val sCommonDAry: ArrayList<CommonDBaseModel> = ArrayList()
            sCommonDAry.add(commonDObj)
            getAdapterUtil.getAdapterPosition(position, sCommonDAry, "PHAR")

        }
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuFrm: CardView = itemView.findViewById(R.id.order_frm)
        private val orderName: TextView = itemView.findViewById(R.id.cust_name)
        private val orderPhone: TextView = itemView.findViewById(R.id.cust_phone)
        private val orderId: TextView = itemView.findViewById(R.id.ord_id)
        private val orderDate: TextView = itemView.findViewById(R.id.ord_date)
        private val orderStatus: ImageView = itemView.findViewById(R.id.order_status)
        private val orderStatusComp: ImageView = itemView.findViewById(R.id.order_status_com)
        private val ordStsFrm: LinearLayout = itemView.findViewById(R.id.ord_sts_frm)
        private val ordHeadDate: TextView = itemView.findViewById(R.id.ord_head_date)

        fun bind(item: DummyMenu) {
//            orderName.text = item.name
//            orderPhone.text = item.title
            if(item.title == "head"){
                ordHeadDate.visibility=View.VISIBLE
                ordHeadDate.text = item.name
            }else{
                ordHeadDate.visibility=View.GONE
            }
            if(item.image == "1"){
                //completed
                orderStatusComp.visibility=View.VISIBLE
                orderStatus.visibility=View.GONE
            }else{
                //pending
                orderStatusComp.visibility=View.GONE
                orderStatus.visibility=View.VISIBLE
            }

            if(pageFrom == "home"){
                ordStsFrm.visibility=View.GONE
            }else{
                ordStsFrm.visibility=View.VISIBLE
            }
        }
    }

}
