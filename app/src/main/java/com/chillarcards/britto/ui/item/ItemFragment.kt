package com.chillarcards.britto.ui.item

import StockItemAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
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
import org.koin.androidx.viewmodel.ext.android.viewModel

open class ItemFragment : Fragment(), IAdapterViewUtills {

    lateinit var binding: FragmentMainInnerBinding
    private lateinit var prefManager: PrefManager
    private val args: ItemFragmentArgs by navArgs()
    private val itemViewModel by viewModel<ItemViewModel>()
    private val businessViewModel by viewModel<BusinessViewModel>()
    lateinit var pharItemDataMastCols: List<PharmacyItemCategory>
    lateinit var pharItemBrdDataMastCols: List<PharmacyItemBrand>
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
        binding.addPharFrm.visibility=View.VISIBLE
        businessViewModel.run { getCategory() }
        businessViewModel.run { getBrand() }
        itemViewModel.run {
            businessUuid.value = args.id
            getItemList()
        }

        setToolbar()
        setObserver()


        // using setvalidator
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


        binding.tvUpdate.setOnClickListener {
            itemViewModel.run {
                businessUuid.value= args.id.toString()
                itemName.value = binding.pName.text.toString().trim()
                brandUuid.value = brandId
                categoryUuid.value = categoryId
                itemPrice.value = binding.etRate.text.toString().trim()
                itemDiscount.value = binding.etOffer.text.toString().trim()
                addItem()
                getObserver()
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
//        if(Mode.equals("VIEW")) {
//            findNavController().navigate(
//                AllPharmacyFragmentDirections.actionAllpharmcyToItemPharmFragment(
//                    ValueArray[0].mastIDs
//                )
//            )
//        }
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
                                        val pharItemDataMastColsTemp  = cateData.data.toMutableList()
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
                                            val commonAdapter = StockItemAdapter(
                                                itemData.data, context, this@ItemFragment)
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
    private fun getObserver() {
        try {
            itemViewModel.itemData.observe(viewLifecycleOwner) { resource ->
                resource?.let { it ->
                    when (it.status) {
                        Status.SUCCESS -> {
                            hideProgress()
                            it.data?.let { itemData ->
                                if (itemData != null) { // Check if itemData is not null
                                    when (itemData.code) {
                                        "200" -> {
                                            Const.shortToast(requireContext(), "completed")
                                            itemViewModel.run {
                                                businessUuid.value = args.id
                                                getItemList()
                                            }
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
    private fun showProgress() {
        binding.ldProgress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.ldProgress.visibility = View.GONE
    }
}
