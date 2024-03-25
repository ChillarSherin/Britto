package com.chillarcards.britto.ui.item

import StockItemAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.chillarcards.britto.R
import com.chillarcards.britto.data.model.PharmacyItemBrand
import com.chillarcards.britto.data.model.PharmacyItemCategory
import com.chillarcards.britto.data.model.ProvinceData
import com.chillarcards.britto.databinding.FragmentMainInnerBinding
import com.chillarcards.britto.ui.DummyItem
import com.chillarcards.britto.ui.adapter.SpinnerSingleAdapter
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.chillarcards.britto.utills.Const
import com.chillarcards.britto.utills.PrefManager
import com.chillarcards.britto.utills.Status
import com.chillarcards.britto.utills.toSpinnerItmBaseModel
import com.chillarcards.britto.viewmodel.BusinessViewModel
import com.chillarcards.britto.viewmodel.ItemViewModel
import com.chillarcards.britto.viewmodel.OTPViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

open class ItemFragment : Fragment(), IAdapterViewUtills {

    lateinit var binding: FragmentMainInnerBinding
    private lateinit var prefManager: PrefManager
    private val args: ItemFragmentArgs by navArgs()
    private val itemViewModel by viewModel<ItemViewModel>()
    private val businessViewModel by viewModel<BusinessViewModel>()
    private var pharItemDataMastCols: List<PharmacyItemCategory> = emptyList()
    private var pharItemBrdDataMastCols: List<PharmacyItemBrand> = emptyList()

    var categoryId: String = "-1"
    var brandId: String = "-1"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_inner, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())
        businessViewModel.run { getCategory() }
        businessViewModel.run { getBrand() }
        itemViewModel.run {
            businessUuid.value = args.id
            getItemList()
        }

        binding.addItemBtn.setOnClickListener {
            binding.addPharFrm.visibility=View.VISIBLE
            binding.addItemBtn.visibility=View.GONE
        }

        binding.tvCancel.setOnClickListener {
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)

            binding.addPharFrm.visibility=View.GONE
            binding.addItemBtn.visibility=View.VISIBLE
        }

        setToolbar()
        setObserver()

        // using set validator
        binding.etCategroy.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                //using for loop
                for(item in pharItemDataMastCols){
                    if(item.category_uuid!="-1"){
                        if(item.category_name == pharItemDataMastCols.get(position).category_name){
                            categoryId = item.category_uuid
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
     // using setvalidator
        binding.etBrand.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                //using for loop
                for(item in pharItemBrdDataMastCols){
                    if(item.brand_uuid!="-1"){
                        if(item.brand_name == pharItemBrdDataMastCols.get(position).brand_name){
                            brandId = item.brand_uuid
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

        binding.imEdit.setOnClickListener {
            setBottomSheet()
        }

        binding.tvUpdate.setOnClickListener {
            val mrp = binding.etRate.text.toString()
            val prdname = binding.pName.text.toString()
            when {
                prdname.isEmpty() -> {
                    binding.pName.error = getString(R.string.enter_empty)
                }
                mrp.isEmpty()  -> {
                    binding.etRate.error = getString(R.string.enter_empty)
                }
                categoryId == "-1"  ->{
                    Const.shortToast(requireContext(), getString(R.string.enter_cat))
                }
                brandId == "-1"  ->{
                    Const.shortToast(requireContext(), getString(R.string.enter_brand))
                }
                else -> {
                    val etOffer = if ( binding.etOffer.text.toString().trim().isEmpty()) "0.0" else  binding.etOffer.text.toString().trim()
                    binding.addPharFrm.visibility=View.GONE
                    binding.addItemBtn.visibility=View.VISIBLE
                    binding.loginBtn.visibility =View.GONE
                    showProgress()
                    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)

                    itemViewModel.run {
                        businessUuid.value= args.id.toString()
                        itemName.value = binding.pName.text.toString().trim()
                        brandUuid.value = brandId
                        categoryUuid.value = categoryId
                        itemPrice.value = binding.etRate.text.toString().trim()
                        itemDiscount.value = etOffer
                        addItem()
                        getObserver()
                    }
                }
            }

        }
    }

    private fun setToolbar() {
        binding.toolbar.toolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.toolbarTitle.text = getString(R.string.stock)
    }

    override fun getAdapterPosition(
        Position: Int,
        ValueArray: ArrayList<CommonDBaseModel>,
        Mode: String?
    ) {
        if(Mode.equals("UPDATE")) {
            itemViewModel.run {
                itemUuid.value= ValueArray[0].mastIDs
                itemName.value = ValueArray[0].itmName
                brandUuid.value = ValueArray[0].valueStr4
                categoryUuid.value = ValueArray[0].valueStr3
                itemPrice.value = ValueArray[0].valueStr1
                itemDiscount.value = ValueArray[0].valueStr2
                updateItem()
                getObserver()
            }
        }else if(Mode.equals("STATUS")) {

            itemViewModel.run {
                itemUuid.value= ValueArray[0].mastIDs
                statusItem()
                getObserver()
            }
        }
    }
    private fun setObserver() {
        try {
            businessViewModel.cateData.observe(viewLifecycleOwner) {
                if (it != null) {
                    when (it.status) {
                        Status.SUCCESS -> {
                            hideProgress()
                            it.data?.let { cateData ->
                                when (cateData.code) {
                                    "200" -> {
                                        var pharItemDataMastColsTemp  = cateData.data.toMutableList()
                                        pharItemDataMastColsTemp.add(0, PharmacyItemCategory("-1", getString(R.string.enter_cat),-1))
                                        pharItemDataMastCols  = pharItemDataMastColsTemp
                                        val spinnerItemBaseModelList = pharItemDataMastColsTemp.map { PharmacyItemCategory ->
                                            PharmacyItemCategory.toSpinnerItmBaseModel()
                                        }

                                        val jsSpinnerAdapterObj = SpinnerSingleAdapter(
                                            requireContext(),
                                            R.layout.item_testview,
                                            spinnerItemBaseModelList
                                        )

                                        binding.etCategroy.prompt = getString(R.string.enter_cat)
                                        binding.etCategroy.adapter = jsSpinnerAdapterObj
                                        jsSpinnerAdapterObj.notifyDataSetChanged()

                                        binding.addItemBtn.visibility=View.VISIBLE

                                    }
                                    else -> Const.shortToast(requireContext(), cateData.msg)
                                }
                            }
                        }
                        Status.LOADING -> {
                            showProgress()
                        }
                        Status.ERROR -> {
                            hideProgress()
                            Const.shortToast(requireContext(), it.message.toString())
                        }
                    }
                }
            }
            businessViewModel.brandData.observe(viewLifecycleOwner) {
                if (it != null) {
                    when (it.status) {
                        Status.SUCCESS -> {
                            hideProgress()
                            it.data?.let { cateData ->
                                when (cateData.code) {
                                    "200" -> {
                                        val pharItemBrndDataMastColsTemp  = cateData.data.toMutableList()
                                        pharItemBrndDataMastColsTemp.add(0, PharmacyItemBrand("-1", getString(R.string.enter_brand),-1))
                                        pharItemBrdDataMastCols  = pharItemBrndDataMastColsTemp
                                        val spinnerItemBaseModelList = pharItemBrndDataMastColsTemp.map { PharmacyItemBrand ->
                                            PharmacyItemBrand.toSpinnerItmBaseModel()
                                        }
                                        pharItemBrdDataMastCols= cateData.data.toMutableList()

                                        val jsSpinnerAdapterObj = SpinnerSingleAdapter(
                                            requireContext(),
                                            R.layout.item_testview,
                                            spinnerItemBaseModelList
                                        )

                                        binding.etBrand.prompt = getString(R.string.enter_brand)
                                        binding.etBrand.adapter = jsSpinnerAdapterObj
                                        jsSpinnerAdapterObj.notifyDataSetChanged()
                                    }
                                    else -> Const.shortToast(requireContext(), cateData.msg)
                                }
                            }
                        }
                        Status.LOADING -> {
                            showProgress()
                        }
                        Status.ERROR -> {
                            hideProgress()
                            Const.shortToast(requireContext(), it.message.toString())
                        }
                    }
                }
            }
            itemViewModel.itemData.observe(viewLifecycleOwner) { resource ->
                resource?.let { it ->
                    when (it.status) {
                        Status.SUCCESS -> {
                            hideProgress()
                            it.data?.let { itemData ->
                                if (itemData != null) { // Check if itemData is not null
                                    when (itemData.code) {
                                        "200" -> {
                                            binding.nodata.visibility=View.GONE

                                            val pharItemDataMastColsTemp: List<PharmacyItemCategory> = pharItemDataMastCols.toMutableList()
                                            val pharItemBrdDataMastColsTemp: List<PharmacyItemBrand> = pharItemBrdDataMastCols.toMutableList()

                                            val commonAdapter = StockItemAdapter(
                                                itemData.data, pharItemDataMastColsTemp, pharItemBrdDataMastColsTemp,
                                                requireContext(), this@ItemFragment
                                            )
                                            binding.topOptionRv.adapter = commonAdapter
                                            binding.topOptionRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

                                        }
                                        "400" -> {
                                            binding.nodata.visibility=View.VISIBLE
                                        }
                                        else -> {
                                            val errorMessage = itemData.list_pharmacy_items ?: "Unknown error"
                                            Const.shortToast(requireContext(), errorMessage)
                                        }
                                    }
                                } else {
                                    Const.shortToast(requireContext(), "No data received")
                                }
                            }
                        }
                        Status.LOADING -> {
                            showProgress()
                        }
                        Status.ERROR -> {
                            hideProgress()
                            Const.shortToast(requireContext(), it.message ?: "Error occurred")
                        }
                    }
                }
            }

        } catch (e: Exception) {
            Log.e("abc_otp", "setUpObserver: ", e)
        }

    }
    private fun addObserver() {
        try {
            itemViewModel.addBrandData.observe(viewLifecycleOwner) { resource ->
                resource?.let { it ->
                    when (it.status) {
                        Status.SUCCESS -> {
                            hideProgress()
                            it.data?.let { itemData ->
                                if (itemData != null) { // Check if itemData is not null
                                    when (itemData.code) {
                                        "200" -> {
                                            Const.shortToast(requireContext(), itemData.add_item_brand)
                                            binding.addPharFrm.visibility=View.VISIBLE
                                            binding.addItemBtn.visibility=View.GONE
                                            businessViewModel.run { getBrand() }

                                        }
                                        else -> {
                                            Const.shortToast(requireContext(), itemData.msg)
                                        }
                                    }
                                } else {
                                    Const.shortToast(requireContext(), "No data received")
                                }
                            }
                        }
                        Status.LOADING -> {
                            showProgress()
                        }
                        Status.ERROR -> {
                            hideProgress()
                            Const.shortToast(requireContext(), it.message ?: "Error occurred")
                        }
                    }
                }
            }

        } catch (e: Exception) {
            Log.e("abc_otp", "setUpObserver: ", e)
        }

    }
    private fun getObserver() {
        try {
            itemViewModel.addItemData.observe(viewLifecycleOwner) { resource ->
                resource?.let { it ->
                    when (it.status) {
                        Status.SUCCESS -> {
                            hideProgress()
                            it.data?.let { itemData ->
                                if (itemData != null) { // Check if itemData is not null
                                    when (itemData.code) {
                                        "200" -> {
                                            itemViewModel.run {
                                                businessUuid.value = args.id
                                                getItemList()
                                            }
                                        }
                                        else -> {
                                            val errorMessage = itemData.add_pharmacy_item ?: "Unknown error"
                                            Const.shortToast(requireContext(), errorMessage)
                                        }
                                    }
                                } else {
                                    Const.shortToast(requireContext(), "No data received")
                                }
                            }
                        }
                        Status.LOADING -> {
                            showProgress()
                        }
                        Status.ERROR -> {
                            hideProgress()
                            Const.shortToast(requireContext(), it.message ?: "Error occurred")
                        }
                    }
                }
            }
            itemViewModel.statusItemData.observe(viewLifecycleOwner) { resource ->
                resource?.let { it ->
                    when (it.status) {
                        Status.SUCCESS -> {
                            hideProgress()
                            it.data?.let { itemData ->
                                if (itemData != null) { // Check if itemData is not null
                                    when (itemData.code) {
                                        "200" -> {
                                            Const.shortToast(requireContext(), "Updated Successfully")
                                            itemViewModel.run {
                                                businessUuid.value = args.id
                                                getItemList()
                                            }
                                        }
                                        else -> {
                                            val errorMessage = itemData.item_status_check ?: "Unknown error"
                                            Const.shortToast(requireContext(), errorMessage)
                                        }
                                    }
                                } else {
                                    Const.shortToast(requireContext(), "No data received")
                                }
                            }
                        }
                        Status.LOADING -> {
                            showProgress()
                        }
                        Status.ERROR -> {
                            hideProgress()
                            Const.shortToast(requireContext(), it.message ?: "Error occurred")
                        }
                    }
                }
            }

        } catch (e: Exception) {
            Log.e("abc_otp", "setUpObserver: ", e)
        }
    }
    private fun setBottomSheet() {

        val bottomSheetView = LayoutInflater.from(context).inflate(R.layout.bottom_add_brand, null)
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomSheetView)

        val brandNametv: TextView = bottomSheetView.findViewById(R.id.service_name_et)

        val cancelButton: TextView = bottomSheetView.findViewById(R.id.cancelButton)
        cancelButton.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val completeButton: TextView = bottomSheetView.findViewById(R.id.completedButton)
        completeButton.setOnClickListener {
            when {
                brandNametv.text.toString().isEmpty() -> {
                    brandNametv.error = getString(R.string.enter_empty)
                }
                else -> {
                    itemViewModel.run {
                        businessUuid.value = args.id
                        brandName.value = brandNametv.text.toString()
                        addBrand()
                        addObserver()

                    }
                    bottomSheetDialog.dismiss()

                }
            }

        }

        bottomSheetDialog.show()

    }

    private fun showProgress() {
        binding.ldProgress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.ldProgress.visibility = View.GONE
    }
}
