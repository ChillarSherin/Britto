package com.chillarcards.britto.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.chillarcards.britto.R
import com.chillarcards.britto.ui.DummyMenu
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import de.hdodenhof.circleimageview.CircleImageView

class DoctorAdapter(
    private val items: List<DummyMenu>,
    private val context: Context?,
    private val activity: FragmentActivity?,
    private val getAdapterUtil: IAdapterViewUtills

)
    : RecyclerView.Adapter<DoctorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_doctors, parent, false)
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
            getAdapterUtil.getAdapterPosition(position, sCommonDAry, "DOC")
        }
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuFrm: LinearLayout = itemView.findViewById(R.id.menu_frm)
        private val docName: TextView = itemView.findViewById(R.id.doc_name)
        private val docDesig: TextView = itemView.findViewById(R.id.doc_desig)
        private val docImage: CircleImageView = itemView.findViewById(R.id.im_doc)

        fun bind(item: DummyMenu) {
            docName.text = item.name
            docDesig.text = item.title
            Glide.with(activity!!)
                .load(item.image)
                .centerCrop()
                .transform(CircleCrop())
                .into(docImage)
        }
    }

}
