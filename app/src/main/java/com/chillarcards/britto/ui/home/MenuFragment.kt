package com.chillarcards.britto.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentHomeBinding
import com.chillarcards.britto.databinding.FragmentMainInnerBinding
import com.chillarcards.britto.ui.DummyDrawer
import com.chillarcards.britto.ui.DummyImage
import com.chillarcards.britto.ui.DummyMenu
import com.chillarcards.britto.ui.adapter.DoctorAdapter
import com.chillarcards.britto.ui.adapter.HospitalAdapter
import com.chillarcards.britto.ui.adapter.MenuAdapter
import com.chillarcards.britto.ui.adapter.PharmacyAdapter
import com.chillarcards.britto.ui.adapter.SliderPagerAdapter
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.chillarcards.britto.utills.PrefManager

open class MenuFragment : Fragment(), IAdapterViewUtills {

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

        val dummyCat= listOf(
            DummyDrawer(1,R.drawable.ic_pharmacy ,"Pharmacy"),
            DummyDrawer(2,R.drawable.ic_hospital ,"Hospital"),
            DummyDrawer(3,R.drawable.ic_doctor ,"Doctor"),
            DummyDrawer(4,R.drawable.ic_lab ,"Lab"),
            DummyDrawer(4,R.drawable.ic_healthcare ,"Health care"),
            DummyDrawer(4,R.drawable.ic_ambulance ,"Ambulance"),
        )


        val menuAdapter = MenuAdapter(
            dummyCat, context)
        binding.topOptionRv.adapter = menuAdapter
        binding.topOptionRv.layoutManager = GridLayoutManager(context, 3)
    }

    private fun setToolbar() {
        binding.toolbar.toolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.toolbarTitle.text = getString(R.string.service)
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
