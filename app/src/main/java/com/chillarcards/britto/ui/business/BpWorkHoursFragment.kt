package com.chillarcards.britto.ui.business

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chillarcards.britto.R
import com.chillarcards.britto.data.model.WorkHour
import com.chillarcards.britto.data.model.WorkHoursRequest
import com.chillarcards.britto.data.model.WorkSchedule
import com.chillarcards.britto.databinding.FragmentWorkHoursAllBinding
import com.chillarcards.britto.ui.adapter.UpdateSubWorkHoursAdapter
import com.chillarcards.britto.ui.adapter.WorkHoursAdapter
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.chillarcards.britto.utills.Const
import com.chillarcards.britto.utills.PrefManager
import com.chillarcards.britto.utills.Status
import com.chillarcards.britto.viewmodel.BusinessViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel


class BpWorkHoursFragment : Fragment(),IAdapterViewUtills {

    lateinit var binding: FragmentWorkHoursAllBinding
    lateinit var workHoursAdapter: WorkHoursAdapter
    private lateinit var prefManager: PrefManager
    private val businessViewModel by viewModel<BusinessViewModel>()
    private var day ="Select a day"
    private var session ="Session"
    var previousDayPosition = 0
    var previousSessionPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_work_hours_all, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager(requireContext())
        businessViewModel.run {
//            businessUuid.value = prefManager.getBusUUID()
            businessUuid.value = "04f2af0a8c3f4ce8af72babba63ef1e2"
            getWorkHours()
        }
        Const.enableButton(binding.confirmBtn)

        setUpObserver()

        binding.confirmBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        val daysOfWeek = arrayOf("Select a day", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, daysOfWeek)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.daySpinner.adapter = adapter

        val sessionOfDay = arrayOf("Session", "AM", "PM")
        val adapterSession = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sessionOfDay)
        adapterSession.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sessionSpinner.adapter = adapterSession

        binding.daySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != previousDayPosition) { // Check if selection has changed
                    previousDayPosition = position // Update previous position
                    val selectedDay = daysOfWeek[position]
                    Log.d("SelectedDay", "Selected day: $selectedDay")
                    if (selectedDay == "Select a day") {
                        Const.shortToast(requireContext(), "Select a day")
                    } else {
                        day = selectedDay
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        binding.sessionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != previousSessionPosition) { // Check if selection has changed
                    previousSessionPosition = position // Update previous position
                    val selectedSession = sessionOfDay[position]
                    Log.d("Select session", "Selected session: $selectedSession")
                    if (selectedSession == "Session") {
                        Const.shortToast(requireContext(), "Select session")
                    } else {
                        session = selectedSession
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        binding.addData.setOnClickListener {
         when {
                day == "Select a day" -> {
                    Const.shortToast(requireContext(), "Select a day")
                }
                session == "Session" -> {
                    Const.shortToast(requireContext(), "Select session")
                }

                else -> {
                    businessViewModel.run {
                        businessUuid.value = prefManager.getBusUUID().trim()
                        wrkHrsDay.value = day.trim()
                        wrkHrsStartTime.value = binding.startTime.text.toString().trim()
                        wrkHrsEndTime.value = binding.endTime.text.toString().trim()
                        wrkHrsSession.value = session.trim()
                        addWorkHours()
                    }
                }
            }


        }

    }

    private fun setUpObserver() {
        try {
            businessViewModel.workHrsData.observe(viewLifecycleOwner) {
                if (it != null) {
                    when (it.status) {
                        Status.SUCCESS -> {
                            hideProgress()
                            it.data?.let { workData ->
                                when (workData.code) {
                                    200 -> {
                                        if (workData.data.isNotEmpty()) {
                                            binding.nodata.visibility=View.GONE
                                            binding.confirmBtn.visibility=View.VISIBLE
                                            binding.recycler.visibility=View.VISIBLE
                                            workHoursAdapter = WorkHoursAdapter(requireContext(), this@BpWorkHoursFragment,workData.data)
                                            binding.recycler.adapter = workHoursAdapter
                                            binding.recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                                        }
                                        else {
                                            binding.nodata.visibility=View.VISIBLE
                                            binding.confirmBtn.visibility=View.GONE
                                            binding.recycler.visibility=View.GONE
                                        }
                                    }
                                    else -> Const.shortToast(requireContext(), workData.get_working_hours)
                                }
                            }
                        }
                        Status.LOADING -> {
                            showProgress()
                        }
                        Status.ERROR -> {
                            hideProgress()
                        }
                    }
                }
            }

        } catch (e: Exception) {
            Log.e("abc_otp", "setUpObserver: ", e)
        }
    }



    private fun showProgress() {
        binding.confirmBtn.visibility = View.INVISIBLE
        binding.otpProgress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.confirmBtn.visibility = View.VISIBLE
        binding.otpProgress.visibility = View.GONE
    }
    // Assuming your JSON string is already defined somewhere in your code
    private val json = """
{
    "statusCode": 200,
    "message": "Successfully fetched data.",
    "data": {
        "result": [
            {
                "dayStatus": 1,
                "day": "Monday",
                "workSchedule": [
                    {
                        "work_schedule_id": 3,
                        "day": "Monday",
                        "entity_id": 1,
                        "startTime": "09:30",
                        "session": "Morning",
                        "endTime": "12:30",
                        "doctor_id": 1,
                        "status": 1,
                        "created_date_time": "2024-03-01T06:39:39.000Z",
                        "update_date_time": "2024-03-01T06:39:39.000Z",
                        "createdAt": "2024-03-01T06:39:39.000Z",
                        "updatedAt": "2024-03-01T06:39:39.000Z"
                    },
                    {
                        "work_schedule_id": 11,
                        "day": "Monday",
                        "entity_id": 1,
                        "startTime": "10:30",
                        "session": "Morning",
                        "endTime": "12:30",
                        "doctor_id": 1,
                        "status": 1,
                        "created_date_time": "2024-03-06T06:58:16.000Z",
                        "update_date_time": "2024-03-06T06:58:16.000Z",
                        "createdAt": "2024-03-06T06:58:16.000Z",
                        "updatedAt": "2024-03-06T06:58:16.000Z"
                    }
                ]
            },
            {
                "dayStatus": 1,
                "day": "Tuesday",
                "workSchedule": [
                    {
                        "work_schedule_id": 5,
                        "day": "Tuesday",
                        "entity_id": 1,
                        "startTime": "14:30",
                        "session": "Evening",
                        "endTime": "17:30",
                        "doctor_id": 1,
                        "status": 1,
                        "created_date_time": "2024-03-01T06:40:02.000Z",
                        "update_date_time": "2024-03-01T06:40:02.000Z",
                        "createdAt": "2024-03-01T06:40:02.000Z",
                        "updatedAt": "2024-03-01T06:40:02.000Z"
                    }
                ]
            },
            {
                "dayStatus": 1,
                "day": "Wednesday",
                "workSchedule": [
                    {
                        "work_schedule_id": 6,
                        "day": "Wednesday",
                        "entity_id": 1,
                        "startTime": "09:30",
                        "session": "Morning",
                        "endTime": "12:30",
                        "doctor_id": 1,
                        "status": 1,
                        "created_date_time": "2024-03-01T06:40:23.000Z",
                        "update_date_time": "2024-03-01T06:40:23.000Z",
                        "createdAt": "2024-03-01T06:40:23.000Z",
                        "updatedAt": "2024-03-01T06:40:23.000Z"
                    },
                    {
                        "work_schedule_id": 9,
                        "day": "Wednesday",
                        "entity_id": 1,
                        "startTime": "14:30",
                        "session": "Evening",
                        "endTime": "18:30",
                        "doctor_id": 1,
                        "status": 1,
                        "created_date_time": "2024-03-04T09:23:47.000Z",
                        "update_date_time": "2024-03-04T09:23:47.000Z",
                        "createdAt": "2024-03-04T09:23:47.000Z",
                        "updatedAt": "2024-03-04T09:23:47.000Z"
                    }
                ]
            },
            {
                "dayStatus": 1,
                "day": "Thursday",
                "workSchedule": [
                    {
                        "work_schedule_id": 7,
                        "day": "Thursday",
                        "entity_id": 1,
                        "startTime": "14:30",
                        "session": "Evening",
                        "endTime": "18:30",
                        "doctor_id": 1,
                        "status": 1,
                        "created_date_time": "2024-03-01T06:40:41.000Z",
                        "update_date_time": "2024-03-01T06:40:41.000Z",
                        "createdAt": "2024-03-01T06:40:41.000Z",
                        "updatedAt": "2024-03-01T06:40:41.000Z"
                    }
                ]
            },
            {
                "dayStatus": 1,
                "day": "Friday",
                "workSchedule": [
                    {
                        "work_schedule_id": 8,
                        "day": "Friday",
                        "entity_id": 1,
                        "startTime": "14:30",
                        "session": "Evening",
                        "endTime": "18:30",
                        "doctor_id": 1,
                        "status": 1,
                        "created_date_time": "2024-03-01T06:40:53.000Z",
                        "update_date_time": "2024-03-01T06:40:53.000Z",
                        "createdAt": "2024-03-01T06:40:53.000Z",
                        "updatedAt": "2024-03-01T06:40:53.000Z"
                    }
                ]
            },
            {
                "dayStatus": 1,
                "day": "Saturday",
                "workSchedule": [
                    {
                        "work_schedule_id": 10,
                        "day": "Saturday",
                        "entity_id": 1,
                        "startTime": "07:30",
                        "session": "Morning",
                        "endTime": "10:30",
                        "doctor_id": 1,
                        "status": 1,
                        "created_date_time": "2024-03-04T13:38:34.000Z",
                        "update_date_time": "2024-03-04T13:38:34.000Z",
                        "createdAt": "2024-03-04T13:38:34.000Z",
                        "updatedAt": "2024-03-05T18:16:34.000Z"
                    },
                    {
                        "work_schedule_id": 1,
                        "day": "Saturday",
                        "entity_id": 1,
                        "startTime": "14:30",
                        "session": "Evening",
                        "endTime": "18:30",
                        "doctor_id": 1,
                        "status": 1,
                        "created_date_time": "2024-03-01T06:39:20.000Z",
                        "update_date_time": "2024-03-01T06:39:20.000Z",
                        "createdAt": "2024-03-01T06:39:20.000Z",
                        "updatedAt": "2024-03-01T06:39:20.000Z"
                    }
                ]
            },
            {
                "dayStatus": 0,
                "day": "Sunday",
                "workSchedule": [
                    {
                        "day": "Sunday",
                        "status": 0,
                        "startTime": null,
                        "endTime": null,
                        "work_schedule_id": null,
                        "entity_id": 1,
                        "session": null,
                        "doctor_id": 1,
                        "created_date_time": null,
                        "update_date_time": null,
                        "createdAt": null,
                        "updatedAt": null
                    }
                ]
            }
        ]
    }
}
"""

    override fun getAdapterPosition(
        Position: Int,
        ValueArray: ArrayList<CommonDBaseModel>,
        Mode: String?
    ) {

        if(Mode.equals("EDIT")) {
            setBottomSheet(ValueArray[0].listData,ValueArray[0].itmName)
        }
    }
    private fun setBottomSheet(workSchedule: List<WorkSchedule>?, dayName: String?) {

        val bottomSheetView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_workhrs, null)
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomSheetView)

        val subAdapter: RecyclerView = bottomSheetView.findViewById(R.id.recycler)
        val customerTV: TextView = bottomSheetView.findViewById(R.id.dayName)
        customerTV.text = "Day: $dayName"

        subAdapter.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val subWorkHoursAdapter = UpdateSubWorkHoursAdapter(requireContext(), workSchedule!!)
        subAdapter.adapter = subWorkHoursAdapter

        val cancelButton: TextView = bottomSheetView.findViewById(R.id.cancelBtn)
        cancelButton.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        val completeButton: TextView = bottomSheetView.findViewById(R.id.completedBtn)
        completeButton.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()

    }
    override fun onStop() {
        super.onStop()
        Log.d("abc_mob", "onStop: ")
        businessViewModel.clear()
    }
}
