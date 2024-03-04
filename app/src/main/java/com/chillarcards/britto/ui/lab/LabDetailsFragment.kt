package com.chillarcards.britto.ui.lab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentHospitalDocBinding
import com.chillarcards.britto.ui.DummyMenu
import com.chillarcards.britto.ui.adapter.HospitalDoctorAdapter
import com.chillarcards.britto.ui.doctor.AllDoctorFragmentDirections
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.chillarcards.britto.utills.PrefManager

open class LabDetailsFragment : Fragment(), IAdapterViewUtills {

    lateinit var binding: FragmentHospitalDocBinding
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hospital_doc, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())
        binding.toolbarBack.setOnClickListener{
            findNavController().popBackStack()
        }
        val dummyDoc= listOf(
            DummyMenu(1,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
            DummyMenu(2,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
            DummyMenu(3,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
            DummyMenu(4,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
            DummyMenu(5,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
            DummyMenu(6,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
            DummyMenu(7,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
            DummyMenu(8,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
            DummyMenu(9,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
        )
        val docTopPicAdapter = HospitalDoctorAdapter(
            dummyDoc, context,activity,this@LabDetailsFragment)
        binding.topDocRv.adapter = docTopPicAdapter
        binding.topDocRv.layoutManager = GridLayoutManager(requireContext(), 4) // Adjust the span count as needed

    }

    override fun getAdapterPosition(
        Position: Int,
        ValueArray: ArrayList<CommonDBaseModel>,
        Mode: String?
    ) {

    }

}
