package com.chillarcards.britto.ui.partners

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentMainInnerBinding
import com.chillarcards.britto.databinding.FragmentOrderHistoryBinding
import com.chillarcards.britto.ui.DummyMenu
import com.chillarcards.britto.ui.adapter.AllPharmacyAdapter
import com.chillarcards.britto.ui.adapter.OrderAdapter
import com.chillarcards.britto.ui.home.HomeFragmentDirections
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.chillarcards.britto.utills.PrefManager
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

open class AllOrderFragment : Fragment(), IAdapterViewUtills {

    lateinit var binding: FragmentOrderHistoryBinding
    private lateinit var prefManager: PrefManager
    private var formattedDate = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_history, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())
        setToolbar()

        val currentDate = Calendar.getInstance().time
        formattedDate = SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(currentDate)
        binding.date.text = formattedDate

        val dummyPhar = listOf(
            DummyMenu(1,"0","23 Feb 2024","head"),
            DummyMenu(2,"1","Mazoon Pharmacy, Oman Commercial Center","ord"),
            DummyMenu(2,"1","Mazoon Pharmacy, Oman Commercial Center","ord"),
            DummyMenu(2,"0","Mazoon Pharmacy, Oman Commercial Center","ord"),
            DummyMenu(2,"0","Mazoon Pharmacy, Oman Commercial Center","ord"),
            DummyMenu(2,"1","Mazoon Pharmacy, Oman Commercial Center","ord"),
            DummyMenu(2,"1","Mazoon Pharmacy, Oman Commercial Center","ord"),
            DummyMenu(3,"https://www.searcharabia.com/uploads/39a9defee9.png","06 Mar 2024","head"),
            DummyMenu(4,"0","06 Mar 2024","ord"),
            DummyMenu(4,"0","06 Mar 2024","ord"),
            DummyMenu(4,"0","06 Mar 2024","ord"),
            DummyMenu(4,"1","06 Mar 2024","ord"),
            DummyMenu(4,"0","06 Mar 2024","ord"),
            DummyMenu(4,"1","06 Mar 2024","ord"),
            DummyMenu(4,"0","06 Mar 2024","ord")
        )

        val orderPicAdapter = OrderAdapter(
            dummyPhar, context,activity,"order",this@AllOrderFragment)
        binding.topOptionRv.adapter = orderPicAdapter

        binding.chooseDate.setOnClickListener {
            val calendar = Calendar.getInstance()

            // Set the maximum date to today's date
            val maxYear = calendar.get(Calendar.YEAR)
            val maxMonth = calendar.get(Calendar.MONTH)
            val maxDay = calendar.get(Calendar.DAY_OF_MONTH)

// Set the minimum date to 3 months ago
            calendar.add(Calendar.MONTH, -3)
            val minYear = calendar.get(Calendar.YEAR)
            val minMonth = calendar.get(Calendar.MONTH)
            val minDay = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    // Handle the selected date
                    val selectedDate = formatDate(day, month, year)
                    binding.date.text = selectedDate

                },
                maxYear,
                maxMonth,
                maxDay
            )

            datePickerDialog.datePicker.minDate = calendar.timeInMillis
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

            datePickerDialog.datePicker.init(
                maxYear, maxMonth, maxDay
            ) { _, year, month, day ->
                // Handle the date change
            }


            datePickerDialog.show()

        }
    }
    private fun formatDate(day: Int, month: Int, year: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val sdf = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
        return sdf.format(calendar.time)
    }
    private fun setToolbar() {
        binding.toolbar.toolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.toolbarTitle.text = getString(R.string.ord)
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
                AllOrderFragmentDirections.actionOrderFragmentToOrderviewFragment(pharmacyId,pharmacyName
                )
            )
        }
    }

}
