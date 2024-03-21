package com.chillarcards.britto.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.chillarcards.britto.R
import com.chillarcards.britto.utills.CommonDBaseModel
import java.util.*


class SpinnerSingleAdapter(
    mContext: Context,
    @LayoutRes private val mLayoutResourceId: Int,
    commonDBaseModelCls: List<CommonDBaseModel>
) :
    ArrayAdapter<CommonDBaseModel>(mContext, mLayoutResourceId, commonDBaseModelCls) {
    private val ItmBaseModelCls: MutableList<CommonDBaseModel> = ArrayList(commonDBaseModelCls)

    override fun getCount(): Int {
        return ItmBaseModelCls.size
    }

    override fun getItem(position: Int): CommonDBaseModel {
        return ItmBaseModelCls[position]
    }

//    override fun getItemId(position: Int): Long {
//        return ItmBaseModelCls[position].mastIDs!!.toLong()
//    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(mLayoutResourceId, null).apply {
            this.findViewById<TextView>(R.id.textView).text = getItem(position).itmName
        }

    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getDropDownView(position, convertView, parent) as TextView
        label.setTextColor(Color.BLACK)
        label.setText(ItmBaseModelCls.get(position).itmName)
      //  this.Selposition = position
        return label
    }


}

