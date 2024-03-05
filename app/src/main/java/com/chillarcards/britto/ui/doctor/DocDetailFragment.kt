package com.chillarcards.britto.ui.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentDoctorDetailBinding
import com.chillarcards.britto.databinding.FragmentHospitalDocBinding
import com.chillarcards.britto.ui.DummyMenu
import com.chillarcards.britto.ui.adapter.HospitalDoctorAdapter
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.chillarcards.britto.utills.PrefManager

open class DocDetailFragment : Fragment(), IAdapterViewUtills {

    lateinit var binding: FragmentDoctorDetailBinding
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_doctor_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())

        val requestOptions = RequestOptions().transform(RoundedCorners(20))
        Glide.with(requireActivity())
            .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS_UNr3cjVDdhBBVS4R7vSEsWnSuHBuReWJew&usqp=CAU")
            .apply(requestOptions)
            .into(binding.imPic)

        val originalText = "Welcome to Al Hayat International Hospital. We are a locally owned and operated medical center in operation since 1995. Over the years, we’ve grown from a single doctor cardiology practice to a multi-specialty hospital extending services for the whole family. Since our inception, we have endeavored to bring the most qualified and accomplished doctors to Oman. We are glad that we have succeeded in this mission and are today, home to about 80 internationally qualified senior consultants offering their expertise in almost all branches of medicine. Founded by a U.K.-trained cardiologist, we are particularly renowned for our expertise in interventional cardiology, orthopedic surgery, laparoscopic surgery, cosmetic surgery, sleep medicine, neurology, and diabetes. Over the years, we have built an enviable reputation for effective treatment and patient education and we look forward to serving you."
        val wordLimit = 20
        val words = originalText.split(" ")
        val truncatedText = words.take(wordLimit).joinToString(" ")
        val truncatedMessage = if (words.size > wordLimit) {
            val lastSentence = originalText.substringAfterLast('.')
            "$truncatedText ...$lastSentence"
        } else {
            truncatedText
        }

        binding.tvAboutUsMessage.text = truncatedMessage
        binding.tvAboutUsMessage.setOnClickListener {
            if (binding.tvAboutUsMessage.text.toString() == truncatedMessage) {
                binding.tvAboutUsMessage.text = originalText
            } else {
                binding.tvAboutUsMessage.text = truncatedMessage
            }
        }
        binding.toolbarBack.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    override fun getAdapterPosition(
        Position: Int,
        ValueArray: ArrayList<CommonDBaseModel>,
        Mode: String?
    ) {
        TODO("Not yet implemented")
    }

}
