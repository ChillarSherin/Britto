package com.chillarcards.britto.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chillarcards.britto.R
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.ui.register.doctor.DaySchedule
import com.chillarcards.britto.utills.CommonDBaseModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WorkHoursAdapter(
    private val context: Context,
    private val getAdapterUtil: IAdapterViewUtills,
    private var dataList: List<DaySchedule>
    ) :
    RecyclerView.Adapter<WorkHoursAdapter.MyView>() {

    var dataFilterList: List<DaySchedule> = dataList

    class MyView(view: View) : RecyclerView.ViewHolder(view) {
        var serviceEdit: TextView = view.findViewById(R.id.edit)
        var serviceName: TextView = view.findViewById(R.id.service_name)
        var switchButton: SwitchCompat = view.findViewById(R.id.service_status)
        var subAdapter: RecyclerView = view.findViewById(R.id.sub_work)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.all_service_adapter,
                parent,
                false
            )
        return MyView(itemView)
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        val currentSchedule  = dataFilterList[position]

        holder.serviceName.text = currentSchedule.day
        holder.switchButton.isChecked = (currentSchedule.dayStatus == 1)

        if (currentSchedule.dayStatus == 0) {
            holder.switchButton.setTextColor(context.resources.getColor(R.color.onoff))
            holder.subAdapter.visibility = View.GONE
        }
        else {
            holder.subAdapter.visibility = View.VISIBLE
            holder.subAdapter.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            val subWorkHoursAdapter = SubWorkHoursAdapter(context, currentSchedule.workSchedule)
            holder.subAdapter.adapter = subWorkHoursAdapter
        }

        holder.switchButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // Switch is turned on
                // Perform actions when switch is on
                holder.switchButton.setTextColor(context.resources.getColor(R.color.black))
            } else {
                // Switch is turned off
                // Perform actions when switch is off
                holder.switchButton.setTextColor(context.resources.getColor(R.color.onoff))
            }
        }
        holder.serviceEdit.setOnClickListener {
            val commonDObj = CommonDBaseModel()
            commonDObj.mastIDs = currentSchedule.dayStatus.toString()
            commonDObj.itmName = currentSchedule.day.toString()
            commonDObj.listData = currentSchedule.workSchedule

            val sCommonDAry: ArrayList<CommonDBaseModel> = ArrayList()
            sCommonDAry.add(commonDObj)
            getAdapterUtil.getAdapterPosition(position, sCommonDAry, "EDIT")

        }

    }

    private fun parseDateString(dateString: String?): Date? {
        return if (dateString != null) {
            SimpleDateFormat("HH:mm", Locale.getDefault()).parse(dateString)
        } else {
            null
        }
    }
    override fun getItemCount(): Int {
        return dataList.size
    }

}