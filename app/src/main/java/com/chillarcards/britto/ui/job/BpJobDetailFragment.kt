package com.chillarcards.britto.ui.job

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentJobDtlInnerBinding
import com.chillarcards.britto.databinding.FragmentMainInnerBinding
import com.chillarcards.britto.databinding.FragmentOrderInnerBinding
import com.chillarcards.britto.ui.DummyItem
import com.chillarcards.britto.ui.DummyMenu
import com.chillarcards.britto.ui.adapter.AllPharmacyAdapter
import com.chillarcards.britto.ui.adapter.BpJobViewAdapter
import com.chillarcards.britto.ui.adapter.OrderAdapter
import com.chillarcards.britto.ui.adapter.OrderViewAdapter
import com.chillarcards.britto.ui.home.HomeFragmentDirections
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.chillarcards.britto.utills.PrefManager

open class BpJobDetailFragment : Fragment(), IAdapterViewUtills {

    lateinit var binding: FragmentJobDtlInnerBinding
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_dtl_inner, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())
        setToolbar()
        val dummyOrd = listOf(
            DummyMenu(1,"Malik","just now","2years"),
            DummyMenu(2,"Eva Maria Linson","2 days","3years"),
            DummyMenu(3,"Aaliyah Omar","2 days","1years"),
            DummyMenu(4,"Layla Muhammed","1 week ago","6 mnth"),
            DummyMenu(5,"Ali Nazzar","1 month","fresher")
            )

        val orderPicAdapter = BpJobViewAdapter(
            dummyOrd, context,activity,"order",this@BpJobDetailFragment)
        binding.topOptionRv.adapter = orderPicAdapter

        binding.cancel.setOnClickListener{
            findNavController().popBackStack()
        }
        binding.complete.setOnClickListener{
//            findNavController().navigate(
//                OrderDetailFragmentDirections.actionOrderViewFragmentToSuccessFragment("BP")
//            )
        }
    }

    private fun setToolbar() {
        binding.toolbar.toolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.toolbarTitle.text = getString(R.string.job)
    }

    override fun getAdapterPosition(
        Position: Int,
        ValueArray: ArrayList<CommonDBaseModel>,
        Mode: String?
    ) {
        if(Mode.equals("VIEW")) {
            val selectedPdfUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"

            findNavController().navigate(
                BpJobDetailFragmentDirections.actionJobDetailFragmentToResumeviewFragment(selectedPdfUrl
                )
            )
        }
    }

}
