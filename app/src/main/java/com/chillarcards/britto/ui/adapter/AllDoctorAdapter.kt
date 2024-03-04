package com.chillarcards.britto.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chillarcards.britto.R
import com.chillarcards.britto.ui.DummyMenu
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.squareup.picasso.Picasso

class AllDoctorAdapter(
    private val items: List<DummyMenu>,
    private val context: Context?,
    private val activity: FragmentActivity?,
    private val getAdapterUtil: IAdapterViewUtills
)
    : RecyclerView.Adapter<AllDoctorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_doctor, parent, false)
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
            getAdapterUtil.getAdapterPosition(position, sCommonDAry, "VIEW")
        }
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuFrm: CardView = itemView.findViewById(R.id.menu_frm)
        private val imagePic: ImageView = itemView.findViewById(R.id.im_pic)
        private val itemName: TextView = itemView.findViewById(R.id.tvMainName)
        private val itemTitle: TextView = itemView.findViewById(R.id.tvTitle)

        fun bind(item: DummyMenu) {
          //  itemName.text = item.name
          //  itemTitle.text = item.title
            val requestOptions = RequestOptions().transform(RoundedCorners(20))
            Glide.with(activity!!)
                .load(item.image)
                .apply(requestOptions)
                .into(imagePic)
        }
    }

}
