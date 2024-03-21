package com.chillarcards.britto.ui.register.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentAddLocationBinding
import com.chillarcards.britto.utills.PrefManager

open class AddLocationFragment : Fragment() {

    lateinit var binding: FragmentAddLocationBinding
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_location, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        prefManager = PrefManager(requireContext())

        binding.newLocBtn.setOnClickListener{
            prefManager.setIsLoggedIn(true)
            prefManager.setRefToken("b2c")

            findNavController().navigate(
                AddLocationFragmentDirections.actionSaveFragmentToHomeFragment(
                )
            )
        }

    }

    private fun setToolbar() {
        binding.toolbar.toolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.toolbarTitle.text = getString(R.string.map)
    }


}
