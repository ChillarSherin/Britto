package com.chillarcards.britto.ui.pharmacis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentMainInnerBinding
import com.chillarcards.britto.ui.DummyMenu
import com.chillarcards.britto.ui.adapter.AllPharmacyAdapter
import com.chillarcards.britto.ui.home.HomeFragmentDirections
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.chillarcards.britto.utills.PrefManager

open class AllPharmacyFragment : Fragment(), IAdapterViewUtills {

    lateinit var binding: FragmentMainInnerBinding
    private lateinit var prefManager: PrefManager

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
        val dummyPhar = listOf(
            DummyMenu(1,"https://www.searcharabia.com/uploads/ef863b7b4f.jpg","Mazoon Pharmacy, Oman Commercial Center","Muscat, Oman"),
            DummyMenu(2,"https://www.searcharabia.com/uploads/ef863b7b4f.jpg","Mazoon Pharmacy, Oman Commercial Center","Muscat, Oman"),
            DummyMenu(3,"https://www.searcharabia.com/uploads/39a9defee9.png","Mazoon Pharmacy, Oman Commercial Center","Muscat, Oman"),
            DummyMenu(4,"https://assets.truemeds.in/Images/dwebbanner2.jpeg?tr=cm-pad_resize,bg-FFFFFF,lo-true,w-724","Mazoon Pharmacy","Oman"),
            DummyMenu(5,"https://assets.truemeds.in/Images/dwebbanner2.jpeg?tr=cm-pad_resize,bg-FFFFFF,lo-true,w-724","Mazoon Pharmacy","Oman"),
            DummyMenu(6,"https://assets.truemeds.in/Images/dwebbanner2.jpeg?tr=cm-pad_resize,bg-FFFFFF,lo-true,w-724","Mazoon Pharmacy","Oman"),
        )

        val commonAdapter = AllPharmacyAdapter(
            dummyPhar, context,activity,this@AllPharmacyFragment)
        binding.topOptionRv.adapter = commonAdapter
        binding.topOptionRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }

    private fun setToolbar() {
        binding.toolbar.toolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.toolbarTitle.text = getString(R.string.pharm)
    }

    override fun getAdapterPosition(
        Position: Int,
        ValueArray: ArrayList<CommonDBaseModel>,
        Mode: String?
    ) {
        if(Mode.equals("VIEW")) {
            val pharmacyId = ValueArray[0].mastIDs
            val pharmacyName = ValueArray[0].itmName
            findNavController().navigate(
                AllPharmacyFragmentDirections.actionAllpharmcyToItemPharmFragment(pharmacyId,pharmacyName
                )
            )
        }
    }

}
