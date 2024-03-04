package com.chillarcards.britto.ui.item

import PharmacyItemAdapter
import StockItemAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentMainInnerBinding
import com.chillarcards.britto.ui.DummyItem
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.ui.interfaces.OnCallBackListner
import com.chillarcards.britto.utills.CommonDBaseModel
import com.chillarcards.britto.utills.Const.Companion.cartItems
import com.chillarcards.britto.utills.PrefManager

open class ItemFragment : Fragment(), IAdapterViewUtills {

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
        setDummyUI()

    }

    override fun onResume() {
        super.onResume()
    }


    private fun setToolbar() {
        binding.toolbar.toolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.toolbarTitle.text = getString(R.string.stock)
    }

    private fun setDummyUI() {

        val dummyPhar = listOf(
            DummyItem(1,"Acne-UV Sunscreen with Broad Spectrum UVA/UVB Protection | Oil Free & Water Resistant | Gel SPF 50","Ipca Laboratories Ltd","ر.ع.","823","757","757",  "8","0"),
            DummyItem(2,"Cetaphil Moisturizing Lotion Normal to Combination - Sensitive Skin 250 ml ","Ipca Laboratories Ltd","ر.ع.","965.00","704.45","704.45", "27","0"),
            DummyItem(3,"CETAPHIL SUN SPF 50+ UVB/UVA VERY HIGH PROTECTION LIGHT Gel 50ml","Ipca Laboratories Ltd","ر.ع.","1080.00","950.40", "950.40", "12","0"),
            DummyItem(4,"DEBONER Ear Drops 10ml","Ipca Laboratories Ltd","ر.ع.","80","", "80","","0"),
            DummyItem(5,"Accu-Chek Active Test Strip 100's","Ipca Laboratories Ltd","ر.ع.","1778","1678.32","1678.32",  "12","0"),
            DummyItem(6,"Maybelline New York Colossal Kajal, 24 Hr Smudge Proof Waterproof Deep Black 0.35gm","Ipca Laboratories Ltd","ر.ع.","250","195", "195","8","0"),
            DummyItem(7,"L'Oreal Paris Total Repair 5 Repairing Shampoo 4% Concentrate with Keratin XS Damage Hair 650ml","Ipca Laboratories Ltd","ر.ع.","859.00", "","859.00","","0"),
            DummyItem(8,"Pure Nutrition Glutathione 500 mg with Vitamin C & Saffron Effervescent Tablet - Orange 15's","Ipca Laboratories Ltd","ر.ع.","999","779.22", "779.22","22","0"),
        )

        val commonAdapter = StockItemAdapter(
            dummyPhar, context,this@ItemFragment)
        binding.topOptionRv.adapter = commonAdapter
        binding.topOptionRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }

    override fun getAdapterPosition(
        Position: Int,
        ValueArray: ArrayList<CommonDBaseModel>,
        Mode: String?
    ) {
//        if(Mode.equals("VIEW")) {
//            findNavController().navigate(
//                AllPharmacyFragmentDirections.actionAllpharmcyToItemPharmFragment(
//                    ValueArray[0].mastIDs
//                )
//            )
//        }
    }


}
