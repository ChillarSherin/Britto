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

open class GeneralHomeFragment : Fragment(), IAdapterViewUtills {

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
        binding.customerFrm.setOnClickListener{
            prefManager.setIsLoggedIn(true)
            prefManager.setRefToken("b2c")
            findNavController().navigate(
                GeneralHomeFragmentDirections.actionGenhomeFragmentToHomeFragment(
                )
            )
        }
        binding.vendorFrm.setOnClickListener{
            prefManager.setRefToken("b2b")
            prefManager.setIsLoggedIn(true)

            findNavController().navigate(
                GeneralHomeFragmentDirections.actionGenhomeFragmentToBphomeFragment(
                )
            )
        }

    }


    override fun getAdapterPosition(
        Position: Int,
        ValueArray: ArrayList<CommonDBaseModel>,
        Mode: String?
    ) {
        if(Mode.equals("VIEW")) {

        }
    }

}
