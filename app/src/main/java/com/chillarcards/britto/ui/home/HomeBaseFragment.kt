package com.chillarcards.britto.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentHomeBaseBinding

class HomeBaseFragment : Fragment() {

    lateinit var binding: FragmentHomeBaseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_base, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager
            .findFragmentById(R.id.inner_host_nav) as NavHostFragment
        val navController = navHostFragment.navController

        binding.tvHome.setOnClickListener {
            navController.navigate(R.id.HomeFragment)
        }
        binding.tvProfile.setOnClickListener {
            navController.navigate(R.id.ProfileFragment)
        }
        binding.tvCart.setOnClickListener {
            navController.navigate(R.id.CartPharmacyFragment)
        }


        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.MapFragment, R.id.OTPFragment, R.id.successFragment,
                R.id.ProfileFragment ,R.id.SavedLocFragment ,R.id.EstimateFragment ,
                R.id.ItemPharmacyFragment,R.id.CartPharmacyFragment  -> {
                    binding.bottomMenu.visibility = View.GONE
                }
                else -> {
                    binding.bottomMenu.visibility = View.VISIBLE
                }
            }
        }

     }

}