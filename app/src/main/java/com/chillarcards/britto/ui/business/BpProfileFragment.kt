package com.chillarcards.britto.ui.business

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.chillarcards.britto.R
import com.chillarcards.britto.data.model.ProvinceData
import com.chillarcards.britto.databinding.FragmentBpProfileBinding
import com.chillarcards.britto.ui.adapter.SpinnerSingleAdapter
import com.chillarcards.britto.utills.Const
import com.chillarcards.britto.utills.PrefManager
import com.chillarcards.britto.utills.Status
import com.chillarcards.britto.utills.toSpinnerItmBaseModel
import com.chillarcards.britto.viewmodel.BusinessRegisterViewModel
import com.chillarcards.britto.viewmodel.ProvinceViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream


open class BpProfileFragment : Fragment() {

    lateinit var binding: FragmentBpProfileBinding
    private lateinit var prefManager: PrefManager
    private val provinceViewModel by viewModel<ProvinceViewModel>()
    lateinit var provinceDataMastCols: List<ProvinceData>
    var shopProvinceId: String = "-1"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bp_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())
        provinceViewModel.run { getProvince() }

        setUpObserver()

        binding.shopGovEt.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                //using for loop
                for(item in provinceDataMastCols){
                    if(item.province_uuid!="-1"){
                        if(item.province_name == provinceDataMastCols.get(position).province_name){
                            shopProvinceId = item.province_uuid
                            Const.enableButton(binding.confirmBtn)
                        }
                    }else{
                        shopProvinceId = "-1"
                        Const.disableButton(binding.confirmBtn)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }


    }
    private fun setUpObserver() {
        try {
            provinceViewModel.provinceData.observe(viewLifecycleOwner) {
                if (it != null) {
                    when (it.status) {
                        Status.SUCCESS -> {
                            it.data?.let { provinceData ->
                                when (provinceData.code) {
                                    "200" -> {
                                        val provinceMastTemp  = provinceData.data.toMutableList()
                                        provinceMastTemp.add(0, ProvinceData(getString(R.string.enter_provi), -1,"-1",-1))
                                        provinceDataMastCols  = provinceMastTemp
                                        val spinnerItemBaseModelList = provinceMastTemp.map { ProvinceData ->
                                            ProvinceData.toSpinnerItmBaseModel()
                                        }

                                        val jsSpinnerAdapterObj = SpinnerSingleAdapter(
                                            requireContext(),
                                            R.layout.item_testview,
                                            spinnerItemBaseModelList
                                        )

                                        binding.shopGovEt.prompt = getString(R.string.enter_provi)
                                        binding.shopGovEt.adapter = jsSpinnerAdapterObj
                                        jsSpinnerAdapterObj.notifyDataSetChanged()
                                    }
                                    else -> Const.shortToast(requireContext(), provinceData.msg)
                                }
                            }
                        }
                        Status.LOADING -> {
                        }
                        Status.ERROR -> {
                            Const.shortToast(requireContext(), it.message.toString())
                        }
                    }
                }
            }

        } catch (e: Exception) {
            Log.e("abc_otp", "setUpObserver: ", e)
        }
    }

}
