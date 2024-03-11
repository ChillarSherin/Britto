package com.chillarcards.britto.ui.partners

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
import com.chillarcards.britto.databinding.FragmentOrderInnerBinding
import com.chillarcards.britto.ui.DummyItem
import com.chillarcards.britto.ui.DummyMenu
import com.chillarcards.britto.ui.adapter.AllPharmacyAdapter
import com.chillarcards.britto.ui.adapter.OrderAdapter
import com.chillarcards.britto.ui.adapter.OrderViewAdapter
import com.chillarcards.britto.ui.home.HomeFragmentDirections
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.chillarcards.britto.utills.PrefManager

open class OrderDetailFragment : Fragment(), IAdapterViewUtills {

    lateinit var binding: FragmentOrderInnerBinding
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_inner, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())
        setToolbar()
        val dummyOrd = listOf(
            DummyItem(1,"Acne-UV Sunscreen with Broad Spectrum UVA/UVB Protection | Oil Free & Water Resistant | Gel SPF 50","Ipca Laboratories Ltd","ر.ع.","823","757","757",  "8","2"),
            DummyItem(2,"Cetaphil Moisturizing Lotion Normal to Combination - Sensitive Skin 250 ml ","Ipca Laboratories Ltd","ر.ع.","965.00","704.45","704.45", "27","1"),
            DummyItem(3,"CETAPHIL SUN SPF 50+ UVB/UVA VERY HIGH PROTECTION LIGHT Gel 50ml","Ipca Laboratories Ltd","ر.ع.","1080.00","950.40", "950.40", "12","4"),
            DummyItem(4,"DEBONER Ear Drops 10ml","Ipca Laboratories Ltd","ر.ع.","80","", "80","","1"),
            DummyItem(5,"Accu-Chek Active Test Strip 100's","Ipca Laboratories Ltd","ر.ع.","1778","1678.32","1678.32",  "12","1"),
            DummyItem(6,"Maybelline New York Colossal Kajal, 24 Hr Smudge Proof Waterproof Deep Black 0.35gm","Ipca Laboratories Ltd","ر.ع.","250","195", "195","8","1"),
            DummyItem(7,"L'Oreal Paris Total Repair 5 Repairing Shampoo 4% Concentrate with Keratin XS Damage Hair 650ml","Ipca Laboratories Ltd","ر.ع.","859.00", "","859.00","","1"),
            DummyItem(8,"Pure Nutrition Glutathione 500 mg with Vitamin C & Saffron Effervescent Tablet - Orange 15's","Ipca Laboratories Ltd","ر.ع.","999","779.22", "779.22","22","1"),

            )

        val orderPicAdapter = OrderViewAdapter(
            dummyOrd, context,activity,"order",this@OrderDetailFragment)
        binding.topOptionRv.adapter = orderPicAdapter

        binding.cancel.setOnClickListener{
            findNavController().popBackStack()
        }
        binding.complete.setOnClickListener{
            findNavController().navigate(
                OrderDetailFragmentDirections.actionOrderViewFragmentToSuccessFragment("BP")
            )
        }
    }

    private fun setToolbar() {
        binding.toolbar.toolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.toolbarTitle.text = getString(R.string.ord)
    }

    override fun getAdapterPosition(
        Position: Int,
        ValueArray: ArrayList<CommonDBaseModel>,
        Mode: String?
    ) {
//        if(Mode.equals("VIEW")) {
//            val pharmacyId = ValueArray[0].mastIDs
//            val pharmacyName = ValueArray[0].itmName
//            findNavController().navigate(
//                OrderDetailFragmentDirections.actionOrderViewFragmentToSuccessFragment(pharmacyId,pharmacyName
//                )
//            )
//        }
    }

}
