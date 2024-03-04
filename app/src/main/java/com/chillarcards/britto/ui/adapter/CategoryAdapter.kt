package com.chillarcards.britto.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chillarcards.britto.R
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel

class CategoryAdapter (private val specialties: List<String>,
                       private val getAdapterUtil: IAdapterViewUtills

) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.categrory_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val specialty = specialties[position]
        holder.textView.text = specialty

        holder.textView.setOnClickListener {
            val commonDObj = CommonDBaseModel()
            commonDObj.mastIDs = position.toString()
            commonDObj.itmName = specialty
            val sCommonDAry: ArrayList<CommonDBaseModel> = ArrayList()
            sCommonDAry.add(commonDObj)
            getAdapterUtil.getAdapterPosition(position, sCommonDAry, "CAT")

        }
    }

    override fun getItemCount(): Int {
        return specialties.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
    }
}
