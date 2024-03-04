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
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentMobileBinding
import com.chillarcards.britto.utills.Const
import com.google.firebase.FirebaseApp


class MobileFragment : Fragment() {

    lateinit var binding: FragmentMobileBinding

    private val mobileRegex = "^[7869]\\d{9}$".toRegex()
    private val textRegex = "^[A-Z][a-z]*$|^[a-z]*$".toRegex()

    private var statusTrue: Boolean = false
    private var tempMobileNo: String = ""
    private var selectedItemId: Int = -1


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
//                    binding.waitingBtn.visibility =View.VISIBLE
                    showProgress()
                    try {
                        findNavController().navigate(
                            MobileFragmentDirections.actionMobileFragmentToOTPFragment(input
                            )
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }

        binding.waitingBtn.setOnClickListener{
            Const.shortToast(requireContext(),"Please check your message inbox")
        }

        setTextColorForTerms()

    }



    private fun setUpObserver() {

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
            val s = "Terms and Conditions"
            val wordToSpan: Spannable = SpannableString(s)
            val click: ClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    val browserIntent =
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://www.chillarpayments.com/terms-and-conditions.html")
                        )
                    startActivity(browserIntent)

                }
            }
            wordToSpan.setSpan(
                click, s.indexOf("Terms"), s.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            wordToSpan.setSpan(
                StyleSpan(Typeface.BOLD), s.indexOf("Terms"), s.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            wordToSpan.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                ),
                s.indexOf("Terms"), s.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            binding.terms1.text = wordToSpan
            binding.terms1.movementMethod = LinkMovementMethod.getInstance()
        } catch (e: Exception) {
            Log.e("abc_mobile", "setTextColorForTerms: msg: ", e)
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("abc_mob", "onStop: ")
        // mobileViewModel.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("abc_mob", "onDestroy: ")
    }

    companion object {
        private const val TAG = "MobileFragment"
    }
}