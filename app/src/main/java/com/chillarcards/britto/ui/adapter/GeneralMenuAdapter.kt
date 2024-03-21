package com.chillarcards.britto.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chillarcards.britto.R
import com.chillarcards.britto.data.model.Daum
import com.chillarcards.britto.ui.DummyMenu
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.squareup.picasso.Picasso

class GeneralMenuAdapter (private val items: List<Daum>,
                          context: Context?,
                          private val getAdapterUtil: IAdapterViewUtills
)
    : RecyclerView.Adapter<GeneralMenuAdapter.ViewHolder>() {
    private val colors = arrayOf("#FF738F", "#FFFFFFFF", "#FFFFFFFF", "#FED06B")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_gnrl_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.menuFrm.setCardBackgroundColor(Color.parseColor(colors[position % colors.size]))
        holder.bind(item)

    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuFrm: CardView = itemView.findViewById(R.id.menu_frm)
        private val itemName: TextView = itemView.findViewById(R.id.menu_title)
//        private val itemTitle: TextView = itemView.findViewById(R.id.item_title)

        fun bind(item: Daum) {
            itemName.text = item.business_type_name
            menuFrm.setOnClickListener {
                val commonDObj = CommonDBaseModel()
                commonDObj.mastIDs = item.business_type_uuid
                commonDObj.itmName = item.business_type_name
                val sCommonDAry: ArrayList<CommonDBaseModel> = ArrayList()
                sCommonDAry.add(commonDObj)
                getAdapterUtil.getAdapterPosition(position, sCommonDAry, "MENU")
            }

            when (item.business_type_name) {
                "Pharmacy" -> itemName.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_phar, 0, 0)
                "Hospital" -> itemName.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_hosp, 0, 0)
                "Lab" -> itemName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                "Doctor" -> itemName.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_doc, 0, 0)
                else -> {
                    itemName.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_doc, 0, 0)
                itemName.text="New"
                }
            }
        }
    }


}
