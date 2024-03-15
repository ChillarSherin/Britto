package com.chillarcards.britto.ui.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentCommonHomeBaseBinding

class CommonHomeBaseFragment : Fragment() {

    lateinit var binding: FragmentCommonHomeBaseBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_common_home_base, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager
            .findFragmentById(R.id.common_host_nav) as NavHostFragment
        val navController = navHostFragment.navController

        binding.tvHome.setOnClickListener {

        }
        binding.tvProfile.setOnClickListener {

        }
        binding.tvCart.setOnClickListener {

        }
        binding.tvJob.setOnClickListener {

        }


        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.LandMapFragment, R.id.mobileFragment,R.id.MapFragment, R.id.OTPFragment, R.id.successFragment,
                R.id.ProfileFragment ,R.id.SavedLocFragment ,R.id.EstimateFragment ,
                R.id.ItemPharmacyFragment,R.id.CartPharmacyFragment ,R.id.JobFragment  -> {
                    binding.bottomMenu.visibility = View.GONE
                }
                else -> {
                    binding.bottomMenu.visibility = View.GONE
                }
            }
        }

     }

}