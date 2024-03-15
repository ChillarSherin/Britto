package com.chillarcards.britto.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentSavedLocBinding
import com.chillarcards.britto.ui.Dummy
import com.chillarcards.britto.ui.adapter.SavedLocationAdapter
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.chillarcards.britto.utills.PrefManager

open class SavedLocationFragment : Fragment(), IAdapterViewUtills {

    lateinit var binding: FragmentSavedLocBinding
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved_loc, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
        prefManager = PrefManager(requireContext())


        val dummyItem = listOf(
            Dummy(1,"Infopark Phase 2\nKakkanad, Kerala 682030",   "HOME"),
            Dummy(2,"Infopark Phase 1\nKakkanad, Kerala 682030",   "WORK"),
            Dummy(3,"Infopark Phase 2\nKakkanad, Kerala 682030",   "HOME"),
        )

        val savedLocationAdapter = SavedLocationAdapter(
            dummyItem, context,this@SavedLocationFragment)

        binding.locRv.adapter = savedLocationAdapter
        binding.locRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.newLocBtn.setOnClickListener{
            SavedLocationFragmentDirections.actionSavedFragmentToMapFragment()
        }

    }

    private fun setToolbar() {
        binding.toolbar.toolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.toolbarTitle.text = getString(R.string.map)
    }

    override fun getAdapterPosition(
        Position: Int,
        ValueArray: ArrayList<CommonDBaseModel>,
        Mode: String?
    ) {
        if(Mode.equals("VIEW")) {

            findNavController().navigate(
                SavedLocationFragmentDirections.actionSavedFragmentToGenhomeFragment(
                )
            )
        }
    }

}
