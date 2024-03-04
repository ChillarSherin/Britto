package com.chillarcards.britto.ui.hospital

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentHospitalBinding
import com.chillarcards.britto.ui.DummyImage
import com.chillarcards.britto.ui.DummyMenu
import com.chillarcards.britto.ui.adapter.HospitalDoctorAdapter
import com.chillarcards.britto.ui.adapter.SliderPagerAdapter
import com.chillarcards.britto.ui.adapter.SpecialtyAdapter
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.chillarcards.britto.utills.PrefManager

open class HospitalFragment : Fragment(), IAdapterViewUtills {

    lateinit var binding: FragmentHospitalBinding
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hospital, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())

        binding.toolbarBack.setOnClickListener{
            findNavController().popBackStack()
        }

        val dummyItem = listOf(
            DummyImage(1,"https://assets.truemeds.in/Images/dwebbanner2.jpeg?tr=cm-pad_resize,bg-FFFFFF,lo-true,w-724"),
            DummyImage(2,"https://assets.truemeds.in/Images/tr:orig-true/mobikwik-500cashback.jpg?tr=cm-pad_resize,bg-FFFFFF,lo-true,w-724"),
            DummyImage(3,"https://assets.truemeds.in/Images/dwebbanner3.jpeg?tr=cm-pad_resize,bg-FFFFFF,lo-true,w-724"),
        )

        val myCustomPagerAdapter = SliderPagerAdapter( requireContext(), dummyItem)
        binding.viewPager.adapter = myCustomPagerAdapter
        binding.viewPager.setScrollDurationFactor(1.0)

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

        val medicalSpecialties = listOf(
            "ENT", "Cardiology", "Gastroenterology", "Endocrinology", "Orthopedic", "Oncology", "Rheumatology", "Neonatology", "Pediatric"
        )

        binding.departRv.layoutManager = GridLayoutManager(requireContext(), 4) // Adjust the span count as needed
        binding.departRv.adapter = SpecialtyAdapter(medicalSpecialties,this@HospitalFragment)

        val dummyDoc= listOf(
            DummyMenu(1,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
            DummyMenu(2,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
            DummyMenu(3,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
            DummyMenu(4,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
            DummyMenu(5,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
            DummyMenu(6,"https://img.freepik.com/free-photo/pleased-young-female-doctor-wearing-medical-robe-stethoscope-around-neck-standing-with-closed-posture_409827-254.jpg","Dr Isha","Cardiologist"),
        )
        val docTopPicAdapter = HospitalDoctorAdapter(
            dummyDoc, context,activity,this@HospitalFragment)
        binding.topDocRv.adapter = docTopPicAdapter
        binding.topDocRv.layoutManager = GridLayoutManager(requireContext(), 3) // Adjust the span count as needed

    }

    override fun getAdapterPosition(
        Position: Int,
        ValueArray: ArrayList<CommonDBaseModel>,
        Mode: String?
    ) {
        if(Mode.equals("PHAR")) {
            val pharmacyId = ValueArray[0].mastIDs
            val pharmacyName = ValueArray[0].itmName
            findNavController().navigate(
                HospitalFragmentDirections.actionHospFragmentToDetailsFragment(pharmacyId,pharmacyName)
            )
        }
        if(Mode.equals("DOC")) {
            val pharmacyId = ValueArray[0].mastIDs
            val pharmacyName = ValueArray[0].itmName
            findNavController().navigate(
                HospitalFragmentDirections.actionHospitalFragmentToDocDetailsFragment(pharmacyId,pharmacyName)
            )
        }

    }
}
