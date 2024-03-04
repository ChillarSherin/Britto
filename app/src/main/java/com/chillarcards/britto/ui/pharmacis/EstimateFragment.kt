package com.chillarcards.britto.ui.pharmacis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentEstimateBinding
import com.chillarcards.britto.ui.adapter.EstimateAdapter
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.chillarcards.britto.utills.Const
import com.chillarcards.britto.utills.PrefManager

open class EstimateFragment : Fragment(), IAdapterViewUtills {

    lateinit var binding: FragmentEstimateBinding
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_estimate, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())

        Const.enableButton(binding.loginBtn)
        if (Const.cartItems.size != 0) {
            var total_Price = 0f
            var total_Count = 0
            for (i in 0 until Const.cartItems.size) {
                total_Price += Const.cartItems[i].cartRate.toFloat()
                total_Count += i
            }
            binding.gtotal.text = total_Price.toString() +" "+ resources.getString(R.string.rupee)
        }

        val adapter = EstimateAdapter(Const.cartItems)
        binding.estimateRv.adapter = adapter
        binding.estimateRv.layoutManager = LinearLayoutManager(context)

        binding.loginBtn.setOnClickListener{
            findNavController().navigate(
                EstimateFragmentDirections.actionEstimateFragmentToSuccessFragment("CM")
            )
        }
    }

    override fun getAdapterPosition(
        Position: Int,
        ValueArray: ArrayList<CommonDBaseModel>,
        Mode: String?
    ) {
        if(Mode.equals("VIEW")) {
            findNavController().navigate(
                AllPharmacyFragmentDirections.actionAllpharmcyToItemPharmFragment("id"
                )
            )
        }
    }

}
