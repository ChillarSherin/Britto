import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.chillarcards.britto.R
import com.chillarcards.britto.ui.DummyItem
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills


class StockItemAdapter(
    private val items: List<DummyItem>,
    private val context: Context?,
    private val getCartUtil: IAdapterViewUtills,
) : RecyclerView.Adapter<StockItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_stock_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }



    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuFrm: CardView = itemView.findViewById(R.id.pharm_frm)
        private val itemViewFrm: LinearLayout = itemView.findViewById(R.id.item_view_frm)
        private val itemEditFrm: LinearLayout = itemView.findViewById(R.id.item_edit_frm)
        private val itemEdit: ImageView = itemView.findViewById(R.id.im_edit)
        private val itemName: TextView = itemView.findViewById(R.id.tvMainName)
        private val itemBrand: TextView = itemView.findViewById(R.id.tvDistri)
        private val itemOffer: TextView = itemView.findViewById(R.id.tvOffers)
        private val itemRate: TextView = itemView.findViewById(R.id.tvRate)
        private val itemCancel: TextView = itemView.findViewById(R.id.tv_cancel)
        private val itemUpdate: TextView = itemView.findViewById(R.id.tv_update)


        fun bind(item: DummyItem) {
            itemName.text = item.prdname
            itemBrand.text = item.prdbrand
            if (item.prdofferrate == "") {
                itemOffer.visibility = View.GONE
                itemRate.text = item.prdcrncy + item.prdmrp
            }
            else {
                itemOffer.visibility = View.VISIBLE
                itemRate.text = item.prdcrncy + item.prdofferrate
                val mrpText = "MRP ${item.prdmrp}${item.prdcrncy}"
                val offerText = "${item.prdoffer}% off"
                val spannableString = SpannableString("$mrpText $offerText")

                spannableString.setSpan(
                    StrikethroughSpan(),
                    0,  // Start index of MRP text
                    mrpText.length, // End index of MRP text
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannableString.setSpan(
                    ForegroundColorSpan(context?.getColor(R.color.primary_green) ?: Color.GREEN),
                    mrpText.length + 1,  // Start index of offer text
                    spannableString.length, // End index of offer text
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                itemOffer.text = spannableString
            }

            itemEdit.setOnClickListener{
                itemEdit.visibility=View.GONE
                itemViewFrm.visibility=View.GONE
                itemEditFrm.visibility=View.VISIBLE
            }
            itemCancel.setOnClickListener{
                itemEdit.visibility=View.VISIBLE
                itemViewFrm.visibility=View.VISIBLE
                itemEditFrm.visibility=View.GONE
            }
            itemUpdate.setOnClickListener{
                itemEdit.visibility=View.VISIBLE
                itemViewFrm.visibility=View.VISIBLE
                itemEditFrm.visibility=View.GONE
            }
        }
    }

}
