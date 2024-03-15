package com.chillarcards.britto.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentGeneralHomeBinding
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.chillarcards.britto.utills.PrefManager

open class GeneralHomeFragment : Fragment() {

    lateinit var binding: FragmentGeneralHomeBinding
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_general_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())

        binding.pharFrm.setOnClickListener { navigateToRegisterFragment("1") }
        binding.hospiFrm.setOnClickListener { navigateToRegisterFragment("2") }
        binding.docFrm.setOnClickListener { navigateToRegisterFragment("3") }
        binding.labFrm.setOnClickListener { navigateToRegisterFragment("4") }

    }
    private fun navigateToRegisterFragment(argument: String) {
        if(argument == "1"){
            findNavController().navigate(
                GeneralHomeFragmentDirections.actionGnerlFragmentToRegisterFragment(argument)
            )
        } else if(argument == "2"){
            findNavController().navigate(
                GeneralHomeFragmentDirections.actionGnerlFragmentToRegisterFragment(argument)
            )
        }else if(argument == "3"){
            findNavController().navigate(
                GeneralHomeFragmentDirections.actionGnerlFragmentToDoctorFragment(argument)
            )
        }else if(argument == "4"){
            findNavController().navigate(
                GeneralHomeFragmentDirections.actionGnerlFragmentToRegisterFragment(argument)
            )
        }
    }

}
