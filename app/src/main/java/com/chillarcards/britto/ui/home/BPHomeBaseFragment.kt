package com.chillarcards.britto.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentHomeBaseBinding
import com.chillarcards.britto.databinding.FragmentHomeBpBaseBinding
import com.chillarcards.britto.databinding.FragmentHomeBpBinding
import com.chillarcards.britto.utills.PrefManager

class BPHomeBaseFragment : Fragment() {

    lateinit var binding: FragmentHomeBpBaseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_bp_base, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager
            .findFragmentById(R.id.bp_inner_host_nav) as NavHostFragment
        val navController = navHostFragment.navController

//        binding.tvJob.setOnClickListener {
//            navController.navigate(R.id.HomeFragment)
//        }
//        binding.tvHome.setOnClickListener {
//            navController.navigate(R.id.StockFragment)
//        }
//        binding.tvHome.setOnClickListener {
//            navController.navigate(R.id.CartPharmacyFragment)
//        }
//
//
//        navController.addOnDestinationChangedListener { controller, destination, arguments ->
//            when (destination.id) {
//                R.id.MapFragment, R.id.OTPFragment, R.id.successFragment,  -> {
//                    binding.bottomMenu.visibility = View.GONE
//                }
//                else -> {
//                    binding.bottomMenu.visibility = View.VISIBLE
//                }
//            }
//        }

     }

}