package com.chillarcards.britto.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentBpProfileBinding
import com.chillarcards.britto.databinding.FragmentProfileBinding
import com.chillarcards.britto.ui.DummyImage
import com.chillarcards.britto.ui.adapter.SliderPagerAdapter
import com.chillarcards.britto.utills.PrefManager


open class BpProfileFragment : Fragment() {

    lateinit var binding: FragmentBpProfileBinding
    private lateinit var prefManager: PrefManager

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

        binding.toolbarBack.setOnClickListener{
            findNavController().popBackStack()
        }


    }


}
