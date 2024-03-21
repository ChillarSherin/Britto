package com.chillarcards.britto.ui.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentProfileBinding
import com.chillarcards.britto.ui.DummyImage
import com.chillarcards.britto.ui.adapter.SliderPagerAdapter
import com.chillarcards.britto.utills.PrefManager


open class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())

        binding.toolbarBack.setOnClickListener{
            findNavController().popBackStack()
        }

        binding.locationFrm.setOnClickListener{
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentLocationFragment(
                )
            )
        }
        binding.orderFrm.setOnClickListener{
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentOrdHistFragment(
                )
            )
        }
        binding.jobFrm.setOnClickListener{
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentJobFragment(
                )
            )
        }
        val dummyItem = listOf(
            DummyImage(3,"https://assets.truemeds.in/Images/dwebbanner3.jpeg?tr=cm-pad_resize,bg-FFFFFF,lo-true,w-724"),
            DummyImage(1,"https://assets.truemeds.in/Images/dwebbanner2.jpeg?tr=cm-pad_resize,bg-FFFFFF,lo-true,w-724"),
            DummyImage(2,"https://assets.truemeds.in/Images/tr:orig-true/mobikwik-500cashback.jpg?tr=cm-pad_resize,bg-FFFFFF,lo-true,w-724"),
        )
        val myCustomPagerAdapter = SliderPagerAdapter( requireContext(), dummyItem)
        binding.viewPager.adapter = myCustomPagerAdapter
        binding.viewPager.setScrollDurationFactor(1.0)

    }


}
