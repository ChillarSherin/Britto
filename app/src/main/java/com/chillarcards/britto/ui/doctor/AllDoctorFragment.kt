package com.chillarcards.britto.ui.doctor

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
import com.chillarcards.britto.ui.adapter.AllDoctorAdapter
import com.chillarcards.britto.ui.adapter.AllPharmacyAdapter
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.chillarcards.britto.utills.PrefManager

open class AllDoctorFragment : Fragment(), IAdapterViewUtills {

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

        val dummyDoc= listOf(
            DummyMenu(1,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
            DummyMenu(2,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
            DummyMenu(3,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
            DummyMenu(4,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
            DummyMenu(5,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
            DummyMenu(6,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
        )
        val commonAdapter = AllDoctorAdapter(
            dummyDoc, context,activity,this@AllDoctorFragment)
        binding.topOptionRv.adapter = commonAdapter
        binding.topOptionRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }

    private fun setToolbar() {
        binding.toolbar.toolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.toolbarTitle.text = getString(R.string.doc)
    }

    override fun getAdapterPosition(
        Position: Int,
        ValueArray: ArrayList<CommonDBaseModel>,
        Mode: String?
    ) {
        if(Mode.equals("VIEW")) {
            val doctorId = ValueArray[0].mastIDs
            val doctorName = ValueArray[0].itmName
            findNavController().navigate(
                AllDoctorFragmentDirections.actionAlldocFragmentToDetailsFragment(doctorId,doctorName)
            )
        }
    }

}
