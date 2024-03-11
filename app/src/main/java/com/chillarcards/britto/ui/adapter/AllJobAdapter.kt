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
import com.chillarcards.britto.ui.DummyJob
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import de.hdodenhof.circleimageview.CircleImageView

class AllJobAdapter(
    private val items: List<DummyJob>,
    private val context: Context?,
    private val activity: FragmentActivity?,
    private val getAdapterUtil: IAdapterViewUtills,
)
    : RecyclerView.Adapter<AllJobAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_job, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.menuFrm.setOnClickListener {
            val commonDObj = CommonDBaseModel()
            commonDObj.mastIDs = item.id.toString()
            commonDObj.itmName = item.jobname

            val sCommonDAry: ArrayList<CommonDBaseModel> = ArrayList()
            sCommonDAry.add(commonDObj)
            getAdapterUtil.getAdapterPosition(position, sCommonDAry, "VIEW")

        }
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuFrm: CardView = itemView.findViewById(R.id.pharm_frm)
        private val imagePic: CircleImageView = itemView.findViewById(R.id.im_pic)
        private val jobName: TextView = itemView.findViewById(R.id.tvMainName)
        private val jobComp: TextView = itemView.findViewById(R.id.tvComp)
        private val jobPlace: TextView = itemView.findViewById(R.id.tvplace)
        private val jobId: TextView = itemView.findViewById(R.id.tvtype)
        private val jobDate: TextView = itemView.findViewById(R.id.job_date)

        fun bind(item: DummyJob) {
            jobName.text = item.jobname
            jobComp.text = item.comname
            jobPlace.text = item.jonloc
            jobDate.text = "Job posted "+item.posted
            if (item.categ == "0") {
                jobId.text = "Job Id: 123SD2012SD"
            }
            if (item.categ == "1") {
                jobId.text = "Applied 1w ago"
            }
            if (item.categ == "2") {
                jobId.text = "No longer accepting application"
            }
            val requestOptions = RequestOptions().transform(RoundedCorners(20))
            Glide.with(activity!!)
                .load(item.image)
                .apply(requestOptions)
                .into(imagePic)
        }
    }

}
