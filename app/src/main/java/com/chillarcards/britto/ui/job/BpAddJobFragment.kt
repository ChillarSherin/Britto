package com.chillarcards.britto.ui.job

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentAddJobBinding
import com.chillarcards.britto.utills.PrefManager

open class BpAddJobFragment : Fragment() {

    lateinit var binding: FragmentAddJobBinding
    private lateinit var prefManager: PrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_job, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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

        val medicalSpecialties = listOf(
            "Clinical Oversight", "Medication Management", "Patient Care", "Quality Assurance",
            "Training and Development", "Inventory Management", "Documentation and Reporting"
        )


    }


}
