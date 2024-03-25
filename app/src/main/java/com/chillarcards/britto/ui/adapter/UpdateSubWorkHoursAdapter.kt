package com.chillarcards.britto.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.chillarcards.britto.R
import com.chillarcards.britto.data.model.WorkSchedule

class UpdateSubWorkHoursAdapter(
    private val context: Context,
    private var dataList: List<WorkSchedule>
//    private var dataList: WorkSchedule
    ) :
    RecyclerView.Adapter<UpdateSubWorkHoursAdapter.MyView>() {

    class MyView(view: View) : RecyclerView.ViewHolder(view) {
        var serviceFrom: EditText = view.findViewById(R.id.service_from)
        var serviceTo: EditText = view.findViewById(R.id.service_to)
        var serviceTime: Spinner = view.findViewById(R.id.service_shift)
        var switchButton: SwitchCompat = view.findViewById(R.id.service_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.add_sub_service_adapter,
                parent,
                false
            )
        return MyView(itemView)
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        val currentSchedule  = dataList[position]

        holder.serviceTime.visibility=View.VISIBLE
        holder.serviceFrom.setText(currentSchedule.startTime)
        holder.serviceTo.setText(currentSchedule.endTime)

        val sessionOfDay = arrayOf("AM", "PM")
        val adapterSession = ArrayAdapter(context, android.R.layout.simple_spinner_item, sessionOfDay)
        adapterSession.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.serviceTime.adapter = adapterSession
        if (currentSchedule.session.equals("Evening")) {
            holder.serviceTime.setSelection(1) // Set PM
        } else if (currentSchedule.session.equals("Morning")) {
            holder.serviceTime.setSelection(0) // Set AM
        }

        holder.switchButton.isChecked = (currentSchedule.status == 1)

        if (currentSchedule.status == 0) {
            holder.switchButton.setTextColor(context.resources.getColor(R.color.onoff))
        }else {
            holder.switchButton.setTextColor(context.resources.getColor(R.color.theme_lgt))
        }

        holder.switchButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                holder.switchButton.setTextColor(context.resources.getColor(R.color.black))
            } else {
                holder.switchButton.setTextColor(context.resources.getColor(R.color.onoff))
            }
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}