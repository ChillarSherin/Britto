package com.chillarcards.britto.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.chillarcards.britto.R
import com.chillarcards.britto.ui.Dummy
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel


class SavedLocationAdapter(private val items: List<Dummy>,
                          context: Context?,
                          private val getAdapterUtil: IAdapterViewUtills
)
    : RecyclerView.Adapter<SavedLocationAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.location_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        holder.mLocView.setOnClickListener {
            val commonDObj = CommonDBaseModel()
            commonDObj.mastIDs = item.id.toString()
            commonDObj.itmName = item.name
            commonDObj.valueStr1 = item.type
            val sCommonDAry: ArrayList<CommonDBaseModel> = ArrayList()
            sCommonDAry.add(commonDObj)
            getAdapterUtil.getAdapterPosition(position, sCommonDAry, "VIEW")
        }
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mLocView: CardView = itemView.findViewById(R.id.loc_frm)
        private val mLocName: TextView = itemView.findViewById(R.id.loc_name)
        private val mLocType: TextView = itemView.findViewById(R.id.loc_type)

        fun bind(item: Dummy) {
            mLocName.text = item.name
            mLocType.text = item.type

        }

    }

    fun getFirstLetterAfterSpace(inputText: String): String {
        val words = inputText.split(" ")
        val result = StringBuilder()

        for (word in words) {
            if (word.isNotEmpty()) {
                val firstChar = word[0]
                result.append(firstChar)
            }
        }

        return result.toString()
    }

}
