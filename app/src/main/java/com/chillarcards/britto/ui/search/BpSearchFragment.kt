package com.chillarcards.britto.ui.search

import PharmacyItemAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentSearchBinding
import com.chillarcards.britto.ui.DummyImage
import com.chillarcards.britto.ui.DummyItem
import com.chillarcards.britto.ui.DummyMenu
import com.chillarcards.britto.ui.adapter.CategoryAdapter
import com.chillarcards.britto.ui.adapter.PharmacyAdapter
import com.chillarcards.britto.ui.adapter.SliderPagerAdapter
import com.chillarcards.britto.ui.home.HomeFragmentDirections
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.ui.interfaces.OnCallBackListner
import com.chillarcards.britto.utills.CommonDBaseModel
import com.chillarcards.britto.utills.Const
import com.chillarcards.britto.utills.PrefManager

open class BpSearchFragment : Fragment(), IAdapterViewUtills, OnCallBackListner {

    lateinit var binding: FragmentSearchBinding
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())
        binding.idPharmacy.visibility =View.GONE

        binding.toolbarBack.setOnClickListener{
            findNavController().popBackStack()
        }

        binding.viewPhar.setOnClickListener{
            findNavController().navigate(
                BpSearchFragmentDirections.actionSearchFragmentToAllpharmFragment(
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

        val myCustomPagerAdapter = SliderPagerAdapter( requireContext(), dummyItem)
        binding.viewPager.adapter = myCustomPagerAdapter
        binding.viewPager.setScrollDurationFactor(1.0)

        val pharmTopPicAdapter = PharmacyAdapter(
            dummyPhar, context,activity,this@BpSearchFragment)
        binding.topPharRv.adapter = pharmTopPicAdapter
        binding.topPharRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val medicalSpecialties = listOf(
            "Pharmacy", "Hospital", "Doctor", "Lab"
        )

        binding.departRv.adapter = CategoryAdapter(medicalSpecialties)

        val dummySearch = listOf(
            DummyItem(1,"Acne-UV Sunscreen with Broad Spectrum UVA/UVB Protection | Oil Free & Water Resistant | Gel SPF 50","Ipca Laboratories Ltd","ر.ع.","823","757","757",  "8","0"),
            DummyItem(2,"Cetaphil Moisturizing Lotion Normal to Combination - Sensitive Skin 250 ml ","Ipca Laboratories Ltd","ر.ع.","965.00","704.45","704.45", "27","0"),
            DummyItem(3,"CETAPHIL SUN SPF 50+ UVB/UVA VERY HIGH PROTECTION LIGHT Gel 50ml","Ipca Laboratories Ltd","ر.ع.","1080.00","950.40", "950.40", "12","0"),
      )

        val commonAdapter = PharmacyItemAdapter(
            dummySearch, context,this@BpSearchFragment)
        binding.topSearchRv.adapter = commonAdapter

    }


    override fun getAdapterPosition(
        Position: Int,
        ValueArray: ArrayList<CommonDBaseModel>,
        Mode: String?
    ) {
        if(Mode.equals("PHAR")){
            val pharmacyId = ValueArray[0].mastIDs
            val pharmacyName = ValueArray[0].itmName
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToItemPharmacyFragment(
                    pharmacyId,
                    pharmacyName
                )
            )
        }
        else if(Mode.equals("CAT")) {
            if(ValueArray[0].itmName.equals("Pharmacy")){
                val pharmacyId = ValueArray[0].mastIDs
                val pharmacyName = ValueArray[0].itmName
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToAllpharmFragment(
                        pharmacyId,
                        pharmacyName
                    )
                )
            }
            if(ValueArray[0].itmName.equals("Hospital")){
                val mastId = ValueArray[0].mastIDs
                val itmName = ValueArray[0].itmName
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToAllhospitalFragment(
                        mastId,
                        itmName
                    )
                )
            }
            if(ValueArray[0].itmName.equals("Doctor")){
                val mastId = ValueArray[0].mastIDs
                val itmName = ValueArray[0].itmName
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToAlldocFragment(mastId, itmName)
                )
            }
            if(ValueArray[0].itmName.equals("Lab")){
                val mastId = ValueArray[0].mastIDs
                val itmName = ValueArray[0].itmName
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToAllpharmFragment(mastId, itmName)
                )
            }

        }else{
            Const.shortToast(requireContext(),"Wait")
        }

    }

    override fun onAddtocartCallback() {

    }


}
