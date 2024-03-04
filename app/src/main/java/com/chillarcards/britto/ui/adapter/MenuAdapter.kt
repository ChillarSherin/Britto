package com.chillarcards.britto.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chillarcards.britto.R
import com.chillarcards.britto.ui.DummyDrawer

class MenuAdapter (private val items: List<DummyDrawer>, private val context: Context?)
    : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val menuName: TextView = itemView.findViewById(R.id.item_name)
        private val iconMenuImage: ImageView = itemView.findViewById(R.id.im_menu)

        fun bind(item: DummyDrawer) {
            menuName.text = item.name
            iconMenuImage.setImageDrawable(context?.getDrawable(item.image))
        }
    }

}
