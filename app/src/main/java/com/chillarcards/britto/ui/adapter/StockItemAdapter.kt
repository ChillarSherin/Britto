import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.chillarcards.britto.R
import com.chillarcards.britto.data.model.Item
import com.chillarcards.britto.data.model.PharmacyItemBrand
import com.chillarcards.britto.data.model.PharmacyItemCategory
import com.chillarcards.britto.ui.DummyItem
import com.chillarcards.britto.ui.adapter.SpinnerSingleAdapter
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.chillarcards.britto.utills.Const
import com.chillarcards.britto.utills.toSpinnerItmBaseModel
import org.koin.core.KoinApplication.Companion.init


class StockItemAdapter(
    private val items: List<Item>,
    private val categoryitems: List<PharmacyItemCategory>,
    private val branditems: List<PharmacyItemBrand>,
    private val context: Context,
    private val getAdapterUtil: IAdapterViewUtills,
) : RecyclerView.Adapter<StockItemAdapter.ViewHolder>() {

    var categoryId="-1"
    var brandId="-1"

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
        private val itemStatus: SwitchCompat = itemView.findViewById(R.id.item_status)
        private val itemEdit: ImageView = itemView.findViewById(R.id.im_edit)
        private val itemName: TextView = itemView.findViewById(R.id.tvMainName)
        private val itemBrand: TextView = itemView.findViewById(R.id.tvDistri)
        private val itemOffer: TextView = itemView.findViewById(R.id.tvOffers)
        private val itemRate: TextView = itemView.findViewById(R.id.tvRate)
        private val tvOffersPer: TextView = itemView.findViewById(R.id.tvOffers_per)
        private val itemCancel: TextView = itemView.findViewById(R.id.tv_cancel)
        private val itemUpdate: TextView = itemView.findViewById(R.id.tv_update)
        private val etItemName: TextView = itemView.findViewById(R.id.p_name)
        private val etItemMrp: TextView = itemView.findViewById(R.id.et_rate)
        private val etItemDis: TextView = itemView.findViewById(R.id.et_offer)
        private val etCategroy: Spinner = itemView.findViewById(R.id.et_categroy)
        private val etBrand: Spinner = itemView.findViewById(R.id.et_brand)


        fun bind(item: Item) {
            itemName.text = item.item_name
            itemBrand.text = item.item_brand
            if (item.item_discount == 0.0) {
                itemOffer.visibility = View.GONE
                itemRate.text = Const.currency + item.item_price
            }
            else {
                itemOffer.visibility = View.VISIBLE
                itemRate.text = Const.currency+item.item_discount.toString()
                val mrpText = "MRP ${Const.currency}${item.item_price}"
//                val offerText = "${1111}% off"
                tvOffersPer.text=  "${0}% off"
                val spannableString = SpannableString("$mrpText")

                spannableString.setSpan(
                    StrikethroughSpan(),
                    0,  // Start index of MRP text
                    mrpText.length, // End index of MRP text
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                itemOffer.text = spannableString
            }
            if (item.item_status == 1) {
                itemStatus.isChecked = true // Set the toggle button to ON if item_status is 1
            } else {
                itemStatus.isChecked = false // Set the toggle button to OFF if item_status is not 1
            }

            itemEdit.setOnClickListener{
                itemStatus.visibility=View.GONE
                itemEdit.visibility=View.GONE
                itemViewFrm.visibility=View.GONE
                itemEditFrm.visibility=View.VISIBLE

                etItemName.text = item.item_name
                etItemMrp.text = item.item_price.toString()
                etItemDis.text = item.item_discount.toString()

                // Brand
                val pharItemBrndDataMastColsTemp  = branditems.toMutableList()
                pharItemBrndDataMastColsTemp.add(0, PharmacyItemBrand("-1", context.getString(R.string.enter_cat),-1))
                val spinnerItemBaseModelList = pharItemBrndDataMastColsTemp.map { PharmacyItemBrand ->
                    PharmacyItemBrand.toSpinnerItmBaseModel()
                }

                val jsSpinnerBrandAdapterObj = SpinnerSingleAdapter(
                    context,
                    R.layout.item_testview,
                    spinnerItemBaseModelList
                )

                etBrand.prompt = context.getString(R.string.enter_brand)
                etBrand.adapter = jsSpinnerBrandAdapterObj
                jsSpinnerBrandAdapterObj.notifyDataSetChanged()
                val brdItemName = item.item_brand
                val selectedIndex = spinnerItemBaseModelList.indexOfFirst { it.itmName == brdItemName }
                etBrand.setSelection(selectedIndex)
                brandId = (if (selectedIndex != -1) ({
                    spinnerItemBaseModelList[selectedIndex].mastIDs
                }).toString() else TODO()).toString()

                etBrand.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        //using for loop
                        for(items in pharItemBrndDataMastColsTemp){
                            if(items.brand_uuid!="-1"){
                                if(items.brand_uuid == pharItemBrndDataMastColsTemp.get(position).brand_uuid){
                                    brandId = items.brand_uuid
                                }
                            }else{
                                brandId = "-1"
                            }
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // Code to perform some action when nothing is selected
                    }
                }


                //CATEGORY
                val pharItemDataMastColsTemp  = categoryitems.toMutableList()
                pharItemDataMastColsTemp.add(0, PharmacyItemCategory("-1", context.getString(R.string.enter_cat),-1))
                val spinnerItemCatBaseModelList = pharItemDataMastColsTemp.map { PharmacyItemCategory ->
                    PharmacyItemCategory.toSpinnerItmBaseModel()
                }

                val jsSpinnerAdapterObj = SpinnerSingleAdapter(
                    context,
                    R.layout.item_testview,
                    spinnerItemCatBaseModelList
                )

                etCategroy.prompt = context.getString(R.string.enter_cat)
                etCategroy.adapter = jsSpinnerAdapterObj
                jsSpinnerAdapterObj.notifyDataSetChanged()
                val brandItemName = item.item_category
                val selectedCatIndex = spinnerItemCatBaseModelList.indexOfFirst { it.itmName == brandItemName }
                etCategroy.setSelection(selectedCatIndex)
                categoryId = (if (selectedCatIndex != -1) ({
                    spinnerItemCatBaseModelList[selectedCatIndex].mastIDs
                }).toString() else TODO()).toString()

                etCategroy.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        //using for loop
                        for(items in pharItemDataMastColsTemp){
                            if(items.category_uuid!="-1"){
                                if(items.category_uuid == pharItemDataMastColsTemp.get(position).category_uuid){
                                    categoryId = items.category_uuid
                                }
                            }else{
                                categoryId = "-1"
                            }
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>) {
                        // Code to perform some action when nothing is selected
                    }
                }

            }

            itemCancel.setOnClickListener{
                itemStatus.visibility=View.VISIBLE
                itemEdit.visibility=View.VISIBLE
                itemViewFrm.visibility=View.VISIBLE
                itemEditFrm.visibility=View.GONE
            }
            itemUpdate.setOnClickListener{
                itemStatus.visibility=View.VISIBLE
                itemEdit.visibility=View.VISIBLE
                itemViewFrm.visibility=View.VISIBLE
                itemEditFrm.visibility=View.GONE
                when {

                    categoryId == "-1"  ->{
                        Const.shortToast(context, context.getString(R.string.enter_cat))
                    }
                    brandId == "-1"  ->{
                        Const.shortToast(context, context.getString(R.string.enter_brand))
                    }
                    else -> {
                        val commonDObj = CommonDBaseModel()
                        commonDObj.mastIDs = item.item_uuid
                        commonDObj.itmName = etItemName.text.toString()
                        commonDObj.valueStr1 = etItemMrp.text.toString()
                        commonDObj.valueStr2 = etItemDis.text.toString()
                        commonDObj.valueStr3 = categoryId
                        commonDObj.valueStr4 = brandId
                        val sCommonDAry: ArrayList<CommonDBaseModel> = ArrayList()
                        sCommonDAry.add(commonDObj)
                        getAdapterUtil.getAdapterPosition(position, sCommonDAry, "UPDATE")

                    }
                }

            }


            itemStatus.setOnClickListener {
                val commonDObj = CommonDBaseModel()
                commonDObj.mastIDs = item.item_uuid
                val sCommonDAry: ArrayList<CommonDBaseModel> = ArrayList()
                sCommonDAry.add(commonDObj)
                getAdapterUtil.getAdapterPosition(position, sCommonDAry, "STATUS")
            }
        }
    }

}
