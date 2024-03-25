package com.chillarcards.britto.ui.register

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentMobileBinding
import com.chillarcards.britto.utills.Const
import com.chillarcards.britto.utills.Status
import com.chillarcards.britto.viewmodel.RegisterViewModel
import com.google.firebase.FirebaseApp
import org.koin.androidx.viewmodel.ext.android.viewModel


class MobileFragment : Fragment() {

    lateinit var binding: FragmentMobileBinding
    private val mobileViewModel by viewModel<RegisterViewModel>()

    private val mobileRegex = "^[7869]\\d{9}$".toRegex()
    private val textRegex = "^[A-Z][a-z]*$|^[a-z]*$".toRegex()
    private val args: MobileFragmentArgs by navArgs()

    private var tempMobileNo: String = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMobileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pInfo =
            activity?.let { activity?.packageManager!!.getPackageInfo(it.packageName, PackageManager.GET_ACTIVITIES) }
        val versionName = pInfo?.versionName //Version Name
        FirebaseApp.initializeApp(this.requireContext())


        binding.version.text = "${getString(R.string.version)}" + Const.ver_title + versionName

        binding.nameEt.addTextChangedListener {
            val input = it.toString()
            if (input.isNotEmpty()) {
                if (!input.matches(textRegex)|| input.length < 3 ) {
                    binding.name.error = "Are you sure you entered your name correctly?"
                    Const.disableButton(binding.loginBtn)
                }else {
                    binding.name.error = null
                    binding.name.isErrorEnabled = false
                    Const.enableButton(binding.loginBtn)
                }
            }
            else {
                binding.name.error = null
                binding.name.isErrorEnabled = false
            }
        }

        binding.mobileEt.addTextChangedListener {
            val input = it.toString()
            if (input.isNotEmpty()) {
                if (!input.matches(mobileRegex)) {
                    binding.mobile.error = "Enter a valid mobile number"
                    Const.disableButton(binding.loginBtn)
                } else if (input.length == 10 && input.matches(mobileRegex)) {
                    binding.mobile.error = null
                    binding.mobile.isErrorEnabled = false
                    Const.enableButton(binding.loginBtn)
                    tempMobileNo = input
                    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                } else {
                    binding.mobile.error = null
                    binding.mobile.isErrorEnabled = false
                    Const.enableButton(binding.loginBtn)
                    tempMobileNo = input
                }
            }
            else {
                binding.mobile.error = null
                binding.mobile.isErrorEnabled = false
            }
        }

        setUpObserver()

        binding.loginBtn.setOnClickListener {
            val input = binding.mobileEt.text.toString()
            val nameInput = binding.nameEt.text.toString()
            when {
                !mobileRegex.containsMatchIn(input) -> {
                    binding.mobile.error = getString(R.string.mob_validation)
                }
                !textRegex.containsMatchIn(nameInput)  -> {
                    binding.name.error = getString(R.string.name_validation)
                }
                else -> {
                    binding.loginBtn.visibility =View.GONE
                    showProgress()
                    mobileVerify()
                }
            }
        }

        binding.waitingBtn.setOnClickListener{
            Const.shortToast(requireContext(),"Please check your message inbox")
        }

        setTextColorForTerms()

    }

    private fun mobileVerify() {
        mobileViewModel.run {
            mob.value = tempMobileNo
            verifyMobile()
        }
    }
    private fun setUpObserver() {
        try {
            mobileViewModel.regData.observe(viewLifecycleOwner) {
                if (it != null) {
                    when (it.status) {
                        Status.SUCCESS -> {
                            hideProgress()
                            it.data?.let { mobileData ->
                                when (mobileData.code) {
                                    "200" -> {
                                        Const.shortToast(requireContext(), mobileData.create_otp)
                                        val otpPattern = Regex("\\b\\d{6}\\b")
                                        val matchResult = otpPattern.find(mobileData.create_otp)

                                        val otp = matchResult?.value
                                        if (otp != null) {
                                            try {
                                                findNavController().navigate(
                                                    MobileFragmentDirections.actionMobileFragmentToOTPFragment(
                                                        tempMobileNo,args.seleId,otp
                                                    )
                                                )
                                            } catch (e: Exception) {
                                                e.printStackTrace()
                                            }
                                        } else {
                                            Const.shortToast(requireContext(), "OTP not found in the text.")
                                        }

                                    }
                                    else -> Const.shortToast(requireContext(), mobileData.msg)
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
        binding.loginProgress.visibility = View.VISIBLE
        binding.loginBtn.visibility = View.INVISIBLE
    }

    private fun hideProgress() {
        binding.loginProgress.visibility = View.GONE
        binding.loginBtn.visibility = View.VISIBLE
    }

    private fun setTextColorForTerms() {
        try {
            val text = "Terms & conditions and Privacy Policy"

            val wordToSpan: Spannable = SpannableString(text)

            // Clickable span for "Terms & conditions"
            val termsClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.chillarpayments.com/terms-and-conditions.html"))
                    startActivity(browserIntent)
                }
            }

            // Clickable span for "Privacy Policy"
            val privacyPolicyClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.chillarpayments.com/aboutus.html"))
                    startActivity(browserIntent)
                }
            }

            // Set clickable spans for "Terms & conditions"
            wordToSpan.setSpan(
                termsClickableSpan,
                text.indexOf("Terms & conditions"),
                text.indexOf("Terms & conditions") + "Terms & conditions".length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            // Set clickable spans for "Privacy Policy"
            wordToSpan.setSpan(
                privacyPolicyClickableSpan,
                text.indexOf("Privacy Policy"),
                text.indexOf("Privacy Policy") + "Privacy Policy".length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            // Make "Terms & conditions" and "Privacy Policy" bold
            wordToSpan.setSpan(
                StyleSpan(Typeface.BOLD),
                text.indexOf("Terms & conditions"),
                text.indexOf("Terms & conditions") + "Terms & conditions".length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            wordToSpan.setSpan(
                StyleSpan(Typeface.BOLD),
                text.indexOf("Privacy Policy"),
                text.indexOf("Privacy Policy") + "Privacy Policy".length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            // Set color for both links
            wordToSpan.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.black)),
                text.indexOf("Terms & conditions"),
                text.indexOf("Terms & conditions") + "Terms & conditions".length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            wordToSpan.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.black)),
                text.indexOf("Privacy Policy"),
                text.indexOf("Privacy Policy") + "Privacy Policy".length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            // Set the text and movement method
            binding.terms1.text = wordToSpan
            binding.terms1.movementMethod = LinkMovementMethod.getInstance()
        } catch (e: Exception) {
            Log.e("abc_mobile", "setTextColorForTerms: msg: ", e)
        }
    }
    fun onLoadSMS(){
        // on the below line we are creating a try and catch block
        try {

            val message ="858585 is your verification OTP for accessing the BHC. Do not share this OTP or your number with anyone.yaMqX9A+vNH"
            val uri: Uri = Uri.parse("smsto:+919744496378")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.putExtra("sms_body", message)
            startActivity(intent)

        } catch (e: Exception) {
            // on catch block we are displaying toast message for error.
        }
    }
    override fun onStop() {
        super.onStop()
        Log.d("abc_mob", "onStop: ")
         mobileViewModel.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("abc_mob", "onDestroy: ")
    }

    companion object {
        private const val TAG = "MobileFragment"
    }
}