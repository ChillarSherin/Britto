package com.chillarcards.britto.ui.register.business


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.chillarcards.britto.R
import com.chillarcards.britto.data.model.ProvinceData
import com.chillarcards.britto.databinding.FragmentRegisterProfileBinding
import com.chillarcards.britto.ui.adapter.SpinnerSingleAdapter
import com.chillarcards.britto.utills.Const
import com.chillarcards.britto.utills.PrefManager
import com.chillarcards.britto.utills.Status
import com.chillarcards.britto.utills.toSpinnerItmBaseModel
import com.chillarcards.britto.viewmodel.BusinessRegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream


class PhotoFragment : Fragment() {

    lateinit var binding: FragmentRegisterProfileBinding
    private val CAMERA_PERMISSION_CODE = 101
    private val CAMERA_INTENT_CODE = 102
    private var custImage = ""
    private val businessRegisterViewModel by viewModel<BusinessRegisterViewModel>()
    private lateinit var prefManager: PrefManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())

        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.uploadpic.setOnClickListener {
            val hasCamera = requireContext().packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
            if (hasCamera) {
                if (checkCameraPermission()) {
                    startCameraIntent()
                } else {
                    requestCameraPermission()
                }
            } else {
                Const.shortToast(requireContext(),getString(R.string.no_camera))
            }
        }

        binding.clickLink.setOnClickListener {
            val hasCamera = requireContext().packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
            if (hasCamera) {
                if (checkCameraPermission()) {
                    startCameraIntent()
                } else {
                    requestCameraPermission()
                }
            } else {
                Const.shortToast(requireContext(),getString(R.string.no_camera))
            }
        }

        binding.completedButton.setOnClickListener {
            if(custImage!=""){
                callRegAPI()
            }else{
                Const.shortToast(requireContext(), getString(R.string.no_profile_pic))
            }
        }

    }

    fun callRegAPI(){
        businessRegisterViewModel.run {
            businessProfile.value = custImage
            businessUuid.value = prefManager.getBusinessID()
            regBusinessProfile()
            setUpObserver()
        }
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_CODE
        )
    }

    private fun startCameraIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_INTENT_CODE)
    }
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERMISSION_CODE && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            startCameraIntent()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_INTENT_CODE && resultCode == Activity.RESULT_OK) {
            val thumbnail = data?.extras?.get("data") as Bitmap?
            binding.uploadpic.setImageBitmap(thumbnail)

            custImage = thumbnail?.let { bitMapToString(it) }.toString()

        }
    }
    private fun bitMapToString(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
        val imageString: String = Base64.encodeToString(imageBytes, Base64.DEFAULT)
        //RegData.custImage = imageString

        return imageString
    }

    private fun setUpObserver() {
        try {
            businessRegisterViewModel.busPicData.observe(viewLifecycleOwner) { it ->
                if (it != null) {
                    when (it.status) {
                        Status.SUCCESS -> {
                            it.data?.let { businessData ->
                                when (businessData.code) {
                                    "200" -> {
                                        Const.shortToast(requireContext(),businessData.business_profile_picture_upload)
                                    }
                                    else -> Const.shortToast(requireContext(), businessData.msg)
                                }
                            }
                        }
                        Status.LOADING -> {
                        }
                        Status.ERROR -> {
                            Const.shortToast(requireContext(), it.message.toString())
                        }
                    }
                }
            }

        } catch (e: Exception) {
            Log.e("abc_otp", "setUpObserver: ", e)
        }
    }

}
