package com.chillarcards.britto.ui.pharmacis

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentSuccessBinding
import com.chillarcards.britto.utills.Const


class SuccessFragment : Fragment(){

    lateinit var binding: FragmentSuccessBinding
    private val args: SuccessFragmentArgs by navArgs()
    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuccessBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pInfo =
            activity?.let { activity?.packageManager!!.getPackageInfo(it.packageName, PackageManager.GET_ACTIVITIES) }

        binding.paybill.setAnimation(R.raw.ic_success)
        Const.enableButton(binding.bchHomeBtn)


        mRunnable = Runnable {
            redirect()
        }
        mHandler = Handler()
        mHandler.postDelayed(mRunnable, 4000)

        binding.bchHomeBtn.setOnClickListener {
            redirect()
        }
    }

    private fun redirect(){
        if(args.page.equals("BP")){
        findNavController().navigate(
            SuccessFragmentDirections.actionSuccessFragmentToBphomeFragment()
        )}
        if(args.page.equals("CM")){
        findNavController().navigate(
            SuccessFragmentDirections.actionSuccessFragmentToHomeFragment()
        )
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("abc_mob", "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("abc_mob", "onDestroy: ")
    }

}
