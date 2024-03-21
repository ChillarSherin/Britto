package com.chillarcards.britto.ui.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentAccountBinding
import com.chillarcards.britto.ui.register.business.PhotoFragment
import com.chillarcards.britto.utills.PrefManager
import com.google.android.material.tabs.TabLayoutMediator


open class BpAccountFragment : Fragment() {

    lateinit var binding: FragmentAccountBinding
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())

        setToolbar()

        binding.viewPager2.adapter = TabbedAdapter(requireActivity())

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "Profile"
                1 -> tab.text = "Logo"
                2 -> tab.text = "Working Hours"
            }
        }.attach()
    }

    private fun setToolbar() {
        binding.toolbar.toolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.toolbarTitle.text = getString(R.string.profile_menu)
    }
}

class TabbedAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3 // Number of fragments
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BpProfileFragment()
            1 -> PhotoFragment()
            2 -> BpWorkHoursFragment()
            else -> BpProfileFragment() // Handle default case
        }
    }
}