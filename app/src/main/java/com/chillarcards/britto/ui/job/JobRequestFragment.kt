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
import com.chillarcards.britto.databinding.FragmentJobDetailBinding
import com.chillarcards.britto.databinding.FragmentJobInnerBinding
import com.chillarcards.britto.databinding.FragmentMainInnerBinding
import com.chillarcards.britto.ui.DummyJob
import com.chillarcards.britto.ui.DummyMenu
import com.chillarcards.britto.ui.adapter.AllJobAdapter
import com.chillarcards.britto.ui.adapter.AllPharmacyAdapter
import com.chillarcards.britto.ui.adapter.CategoryAdapter
import com.chillarcards.britto.ui.home.HomeFragmentDirections
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.chillarcards.britto.utills.PrefManager

open class JobRequestFragment : Fragment(), IAdapterViewUtills {

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
        binding.topPicRv.visibility = View.VISIBLE

        //1= Applied 2 =inprogress
        val dummyPhar = listOf(
            DummyJob(1,"https://www.searcharabia.com/uploads/ef863b7b4f.jpg","Sr.Pharmacist","Mazoon Pharmacy, Oman Commercial Center","Muscat, Oman","19 Mar 2024","1"),
            DummyJob(2,"https://www.searcharabia.com/uploads/ef863b7b4f.jpg","Sr.Pharmacist","Mazoon Pharmacy, Oman Commercial Center","Muscat, Oman","11 Mar 2024","1"),
            DummyJob(3,"https://www.searcharabia.com/uploads/ef863b7b4f.jpg","Sr.Pharmacist","Mazoon Pharmacy, Oman Commercial Center","Muscat, Oman","06 Mar 2024","1"),
            DummyJob(4,"https://www.searcharabia.com/uploads/ef863b7b4f.jpg","Sr.Pharmacist","Mazoon Pharmacy, Oman Commercial Center","Muscat, Oman","05 Feb 2024","2"),
            DummyJob(5,"https://www.searcharabia.com/uploads/ef863b7b4f.jpg","Sr.Pharmacist","Mazoon Pharmacy, Oman Commercial Center","Muscat, Oman","23 Feb 2024","2"),
       )

        val commonAdapter = AllJobAdapter(
            dummyPhar, context,activity,this@JobRequestFragment)
        binding.topOptionRv.adapter = commonAdapter
        binding.topOptionRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val medicalSpecialties = listOf(
            "In Progress", "Applied"
        )
        binding.topPicRv.adapter = CategoryAdapter(medicalSpecialties,this@JobRequestFragment)

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
            val pharmacyId = ValueArray[0].mastIDs
            findNavController().navigate(
                JobRequestFragmentDirections.actionJobRequestFragmentToJobView(pharmacyId
                )
            )
        }
    }

}
