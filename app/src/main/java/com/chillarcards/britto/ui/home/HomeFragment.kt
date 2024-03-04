package com.chillarcards.britto.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentHomeBinding
import com.chillarcards.britto.ui.DummyImage
import com.chillarcards.britto.ui.DummyMenu
import com.chillarcards.britto.ui.adapter.DoctorAdapter
import com.chillarcards.britto.ui.adapter.HospitalAdapter
import com.chillarcards.britto.ui.adapter.PharmacyAdapter
import com.chillarcards.britto.ui.adapter.SliderPagerAdapter
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.chillarcards.britto.utills.PrefManager

open class HomeFragment : Fragment(), IAdapterViewUtills {

    lateinit var binding: FragmentHomeBinding
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())
        binding.logo.setOnClickListener{
            findNavController().navigate(
                HomeFragmentDirections.actionGenhomeFragmentToBphomeFragment(
                )
            )
        }

        binding.idPharmacy.setOnClickListener{
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToAllpharmFragment(
                )
            )
        }
        binding.idHospital.setOnClickListener{
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToAllhospitalFragment(
                )
            )
        }
        binding.idDoctor.setOnClickListener{
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToAlldocFragment(
                )
            )
        }
        binding.idMore.setOnClickListener{
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToMenuFragment(
                )
            )
        }
        binding.viewPhar.setOnClickListener{
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToAllpharmFragment(
                )
            )
        }
        binding.viewDoc.setOnClickListener{
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToAlldocFragment(
                )
            )
        }
        binding.viewHosp.setOnClickListener{
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToAllhospitalFragment(
                )
            )
        }
        binding.searchEt.setOnClickListener{
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToSearchFragment(
                )
            )
        }

        val dummyItem = listOf(
            DummyImage(1,"https://assets.truemeds.in/Images/dwebbanner2.jpeg?tr=cm-pad_resize,bg-FFFFFF,lo-true,w-724"),
            DummyImage(2,"https://assets.truemeds.in/Images/tr:orig-true/mobikwik-500cashback.jpg?tr=cm-pad_resize,bg-FFFFFF,lo-true,w-724"),
            DummyImage(3,"https://assets.truemeds.in/Images/dwebbanner3.jpeg?tr=cm-pad_resize,bg-FFFFFF,lo-true,w-724"),
        )
        val dummyPhar = listOf(
            DummyMenu(1,"https://lh3.googleusercontent.com/p/AF1QipMRixWWkhlF-8Xgw5nE98k2h1Mds3jla2c87oWV=s1360-w1360-h1020","Mazoon Pharmacy","Oman"),
            DummyMenu(2,"https://www.kimshealth.om/media/filer_public/96/52/96527546-c594-4410-8edf-0719c28db223/1920x500_3.jpg","Mazoon Pharmacy","Oman"),
            DummyMenu(3,"https://www.searcharabia.com/uploads/39a9defee9.png","Mazoon Pharmacy","Oman"),
            DummyMenu(4,"https://www.searcharabia.com/uploads/ef863b7b4f.jpg","Mazoon Pharmacy","Oman"),
            DummyMenu(5,"https://assets.truemeds.in/Images/dwebbanner2.jpeg?tr=cm-pad_resize,bg-FFFFFF,lo-true,w-724","Mazoon Pharmacy","Oman"),
            DummyMenu(6,"https://assets.truemeds.in/Images/dwebbanner2.jpeg?tr=cm-pad_resize,bg-FFFFFF,lo-true,w-724","Mazoon Pharmacy","Oman"),
          )
        val dummyDoc= listOf(
            DummyMenu(1,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
            DummyMenu(2,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
            DummyMenu(3,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
            DummyMenu(4,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
            DummyMenu(5,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
            DummyMenu(6,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
        )

        val myCustomPagerAdapter = SliderPagerAdapter( requireContext(), dummyItem)
        binding.viewPager.adapter = myCustomPagerAdapter
        binding.viewPager.setScrollDurationFactor(1.0)

        val pharmTopPicAdapter = PharmacyAdapter(
            dummyPhar, context,activity,this@HomeFragment)
        binding.topPicRv.adapter = pharmTopPicAdapter
        binding.topPicRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val docTopPicAdapter = DoctorAdapter(
            dummyDoc, context,activity,this@HomeFragment)
        binding.topDocRv.adapter = docTopPicAdapter
        binding.topDocRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val hospTopPicAdapter = HospitalAdapter(
            dummyPhar, context,activity,this@HomeFragment)
        binding.topHospRv.adapter = hospTopPicAdapter
        binding.topHospRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

    }


    override fun getAdapterPosition(
        Position: Int,
        ValueArray: ArrayList<CommonDBaseModel>,
        Mode: String?
    ) {
        if(Mode.equals("PHAR")) {
            val pharmacyId = ValueArray[0].mastIDs
            val pharmacyName = ValueArray[0].itmName
            findNavController().navigate(
                HomeFragmentDirections.actionAllpharmcyToItemPharmFragment(pharmacyId,pharmacyName)
            )
        }
        if(Mode.equals("HOSP")) {
            val hospitalId = ValueArray[0].mastIDs
            val hospitalName = ValueArray[0].itmName
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToHospitalFragment(hospitalId,hospitalName)
            )
        }
        if(Mode.equals("DOC")) {
            val hospitalId = ValueArray[0].mastIDs
            val hospitalName = ValueArray[0].itmName
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailsFragment(hospitalId,hospitalName)
            )
        }
    }

}
