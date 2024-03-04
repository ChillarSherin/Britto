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
import com.chillarcards.britto.ui.DummyMenu
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.squareup.picasso.Picasso

class HospitalAdapter (private val items: List<DummyMenu>,
                       context: Context?,
                       private val activity: FragmentActivity?,
                       private val getAdapterUtil: IAdapterViewUtills
)
    : RecyclerView.Adapter<HospitalAdapter.ViewHolder>() {
    private val colors = arrayOf("#FED06B", "#FF738F", "#B6E3DC", "#8881DA")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_hospital, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.menuFrm.setCardBackgroundColor(Color.parseColor(colors[position % colors.size]))
        holder.bind(item)
        holder.menuFrm.setOnClickListener {
            val commonDObj = CommonDBaseModel()
            commonDObj.mastIDs = item.id.toString()
            commonDObj.itmName = item.name
            val sCommonDAry: ArrayList<CommonDBaseModel> = ArrayList()
            sCommonDAry.add(commonDObj)
            getAdapterUtil.getAdapterPosition(position, sCommonDAry, "HOSP")

        }
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuFrm: CardView = itemView.findViewById(R.id.menu_frm)
        private val imagePic: ImageView = itemView.findViewById(R.id.im_pic)
        private val itemName: TextView = itemView.findViewById(R.id.item_name)
        private val itemTitle: TextView = itemView.findViewById(R.id.item_title)

        fun bind(item: DummyMenu) {

            itemName.text = item.name
            itemTitle.text = item.title
            val requestOptions = RequestOptions().transform(RoundedCorners(20))
            Glide.with(activity!!)
                .load(item.image)
                .apply(requestOptions)
                .into(imagePic)
//            Picasso.get().load(item.image)
//                .placeholder(R.drawable.place_holder)
//                .error(R.drawable.place_holder) //  .transform(RoundCornersTransform(32.0f))
//                .into(imagePic)
        }
    }


}
