package com.chillarcards.britto.ui.register.business

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentGeneralHomeBinding
import com.chillarcards.britto.ui.adapter.GeneralMenuAdapter
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.chillarcards.britto.utills.Const
import com.chillarcards.britto.utills.PrefManager
import com.chillarcards.britto.utills.Status
import com.chillarcards.britto.viewmodel.BusinessViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

open class GeneralHomeFragment : Fragment(),IAdapterViewUtills {

    lateinit var binding: FragmentGeneralHomeBinding
    private lateinit var prefManager: PrefManager
    private val businessViewModel by viewModel<BusinessViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_general_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())
        businessViewModel.run { getMenu() }

        setUpObserver()

    }

    private fun setUpObserver() {
        try {
            businessViewModel.menuData.observe(viewLifecycleOwner) {
                if (it != null) {
                    when (it.status) {
                        Status.SUCCESS -> {
                            hideProgress()
                            it.data?.let { menueData ->
                                when (menueData.code) {
                                    "200" -> {
                                        if(menueData.data.isNotEmpty()) {
                                            val menuAdapter = GeneralMenuAdapter(
                                                menueData.data, requireContext(),this@GeneralHomeFragment
                                            )
                                            binding.menuRv.adapter = menuAdapter
                                            binding.menuRv.layoutManager =
                                                GridLayoutManager(context, 2)
                                        }
                                    }
                                    else -> Const.shortToast(requireContext(), menueData.msg)
                                }
                            }
                        }
                        Status.LOADING -> {
                            showProgress()
                        }
                        Status.ERROR -> {
                            hideProgress()
                            Const.shortToast(requireContext(), it.message.toString())
                        }
                    }
                }
            }

        } catch (e: Exception) {
            Log.e("abc_otp", "setUpObserver: ", e)
        }
    }
    private fun showProgress() {
        binding.pageProgress.visibility = View.VISIBLE
    }
    private fun hideProgress() {
        binding.pageProgress.visibility = View.GONE
    }
    private fun navigateToRegisterFragment(argument: String) {
        if(argument == "1"){

        } else if(argument == "2"){
            findNavController().navigate(
                GeneralHomeFragmentDirections.actionGnerlFragmentToRegisterFragment(argument)
            )
        }else if(argument == "3"){
            findNavController().navigate(
                GeneralHomeFragmentDirections.actionGnerlFragmentToRegisterFragment(argument)
            )
        }else if(argument == "4"){

        }
    }

    override fun getAdapterPosition(
        Position: Int,
        ValueArray: ArrayList<CommonDBaseModel>,
        Mode: String?
    ) {
        if(Mode.equals("MENU")){
            if(ValueArray[0].itmName.equals("Pharmacy")){
                findNavController().navigate(
                    GeneralHomeFragmentDirections.actionGnerlFragmentToRegisterFragment(ValueArray[0].mastIDs)
                )
            }else if(ValueArray[0].itmName.equals("Lab")){
                findNavController().navigate(
                    GeneralHomeFragmentDirections.actionGnerlFragmentToRegisterFragment(ValueArray[0].mastIDs)
                )
            }else if(ValueArray[0].itmName.equals("Hospital")){
                findNavController().navigate(
                    GeneralHomeFragmentDirections.actionGnerlFragmentToRegisterFragment(ValueArray[0].mastIDs)
                )
            }else if(ValueArray[0].itmName.equals("Doctor")){
                findNavController().navigate(
                    GeneralHomeFragmentDirections.actionGnerlFragmentToDoctorFragment(ValueArray[0].mastIDs)
                )            }
        }

    }

}
