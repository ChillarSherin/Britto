package com.chillarcards.britto.ui.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentMainInnerBinding
import com.chillarcards.britto.ui.adapter.CartItemAdapter
import com.chillarcards.britto.ui.interfaces.OnCallBackListner
import com.chillarcards.britto.ui.pharmacis.PharmacyFragmentArgs
import com.chillarcards.britto.utills.Const
import com.chillarcards.britto.utills.PrefManager

open class CartFragment : Fragment(), OnCallBackListner {

    lateinit var binding: FragmentMainInnerBinding
    private lateinit var prefManager: PrefManager
    private val args: PharmacyFragmentArgs by navArgs()

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
        setToolbar()

        Const.enableButton(binding.loginBtn)
        binding.cartFrm.visibility=View.VISIBLE
        binding.proceedBtn.visibility = View.GONE


        val commonAdapter = CartItemAdapter(
            Const.cartItems, context,activity,this@CartFragment)
        binding.topOptionRv.adapter = commonAdapter
        binding.topOptionRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.loginBtn.setOnClickListener{
            findNavController().navigate(
                CartFragmentDirections.actionCartFragmentToEstimateFragment(
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()

        if (Const.cartItems.size != 0) {
            binding.proceedBtn.visibility = View.GONE

            var total_Price = 0f
            var total_Count = 0
            for (i in 0 until Const.cartItems.size) {
                total_Price += Const.cartItems[i].cartRate.toFloat()
                total_Count += i
            }
            binding.subTtl.text = "Subtotal: " +total_Price.toString() +" "+ resources.getString(R.string.rupee)
        }else{
            binding.cartFrm.visibility = View.GONE
            findNavController().popBackStack()
        }

    }

    private fun setToolbar() {
        binding.toolbar.toolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.toolbarTitle.text = getString(R.string.cart)
    }


    override fun onAddtocartCallback() {

        if (Const.cartItems.size != 0) {
            binding.proceedBtn.visibility = View.GONE

            var total_Price = 0f
            var total_Count = 0
            for (i in 0 until Const.cartItems.size) {
                total_Price += Const.cartItems[i].cartRate.toFloat()
                total_Count += i
            }
           // binding.subTtl.text = "Subtotal : " +total_Price.toString() +" "+ resources.getString(R.string.rupee)
            binding.subTtl.text ="Subtotal : " + String.format("%.2f %s", total_Price, resources.getString(R.string.rupee))

        }else{
            binding.cartFrm.visibility = View.GONE
            findNavController().popBackStack()
        }

    }

}
