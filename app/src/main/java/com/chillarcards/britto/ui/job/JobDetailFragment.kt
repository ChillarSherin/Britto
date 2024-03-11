package com.chillarcards.britto.ui.job

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentJobDetailBinding
import com.chillarcards.britto.ui.DummyImage
import com.chillarcards.britto.ui.adapter.SliderPagerAdapter
import com.chillarcards.britto.ui.adapter.SpecialtyAdapter
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.chillarcards.britto.utills.PrefManager
import com.google.android.material.bottomsheet.BottomSheetDialog

open class JobDetailFragment : Fragment(), IAdapterViewUtills {

    lateinit var binding: FragmentJobDetailBinding
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarBack.setOnClickListener{
            findNavController().popBackStack()
        }

        binding.applyBtn.setOnClickListener{
            setBottomSheet(ArrayList<CommonDBaseModel>())
        }

        binding.loginBtn.setOnClickListener{
            setBottomSheet(ArrayList<CommonDBaseModel>())
        }

        val dummyItem = listOf(
            DummyImage(1,"https://cdn.townweb.com/cityofmineralpoint.com/wp-content/uploads/2021/01/Job-Openings-we-are-hiring.jpg"),
            DummyImage(2,"https://assets.truemeds.in/Images/tr:orig-true/mobikwik-500cashback.jpg?tr=cm-pad_resize,bg-FFFFFF,lo-true,w-724"),
            DummyImage(3,"https://assets.truemeds.in/Images/dwebbanner3.jpeg?tr=cm-pad_resize,bg-FFFFFF,lo-true,w-724"),
        )

        val myCustomPagerAdapter = SliderPagerAdapter( requireContext(), dummyItem)
        binding.viewPager.adapter = myCustomPagerAdapter
        binding.viewPager.setScrollDurationFactor(1.0)

        val originalText ="We are currently seeking a highly skilled and experienced Senior Pharmacist to join our team in Oman. As a Senior Pharmacist, you will play a crucial role in providing pharmaceutical care and ensuring the safe and effective use of medications within our healthcare facility."
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

        val requirements = """
    • Bachelor's degree in Pharmacy from a recognized institution. A Master's degree in Clinical Pharmacy or a related field is preferred.
    • Valid professional license to practice pharmacy in Oman.
    • Minimum of 5 years of experience as a licensed pharmacist, with at least 2 years in a senior or supervisory role.
    • Strong clinical knowledge and understanding of pharmacotherapy principles.
    • Excellent communication and interpersonal skills with the ability to collaborate effectively with healthcare professionals and patients.
    • Proficiency in pharmacy management software and computerized prescription systems.
    • Commitment to continuous professional development and staying abreast of advancements in pharmacy practice.
    • Demonstrated leadership abilities and a proactive approach to problem-solving.
    • Fluency in English is required. Proficiency in Arabic is an advantage.
""".trimIndent()
        binding.tvreqMessage.text=requirements

        val medicalSpecialties = listOf(
            "Clinical Oversight", "Medication Management", "Patient Care", "Quality Assurance",
            "Training and Development", "Inventory Management", "Documentation and Reporting"
        )

        binding.departRv.layoutManager = GridLayoutManager(requireContext(), 4) // Adjust the span count as needed
        binding.departRv.adapter = SpecialtyAdapter(medicalSpecialties,this@JobDetailFragment)

    }


    override fun getAdapterPosition(
        Position: Int,
        ValueArray: ArrayList<CommonDBaseModel>,
        Mode: String?
    ) {
        if(Mode.equals("PHAR")) {

        }
    }

    private fun setBottomSheet(selectedData: ArrayList<CommonDBaseModel>) {

        val bottomSheetView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_service, null)
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomSheetView)

        val actionButton: LinearLayout = bottomSheetView.findViewById(R.id.action_btn)
        val customerTV: TextView = bottomSheetView.findViewById(R.id.employeertv)
        customerTV.text = "Oman International Hospital "

        val completeButton: TextView = bottomSheetView.findViewById(R.id.completedButton)

        val callButton: TextView = bottomSheetView.findViewById(R.id.callButton)
        callButton.setOnClickListener {


            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()

    }

}
