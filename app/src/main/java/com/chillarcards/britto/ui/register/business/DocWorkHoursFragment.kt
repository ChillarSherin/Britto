package com.chillarcards.britto.ui.register.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chillarcards.britto.R
import com.chillarcards.britto.databinding.FragmentWorkHoursAllBinding
import com.chillarcards.britto.ui.adapter.UpdateSubWorkHoursAdapter
import com.chillarcards.britto.ui.adapter.WorkHoursAdapter
import com.chillarcards.britto.ui.interfaces.IAdapterViewUtills
import com.chillarcards.britto.utills.CommonDBaseModel
import com.chillarcards.britto.utills.Const
import com.chillarcards.britto.utills.PrefManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson


class DocWorkHoursFragment : Fragment(),IAdapterViewUtills {

    lateinit var binding: FragmentWorkHoursAllBinding
    lateinit var workHoursAdapter: WorkHoursAdapter
    private lateinit var prefManager: PrefManager


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

        Const.enableButton(binding.confirmBtn)

        setUpObserver()

        binding.confirmBtn.setOnClickListener {
            Const.shortToast(requireContext(),"Need a admin approval to complete the process")
            prefManager.setIsLoggedIn(true)
            prefManager.setRefToken("b2b")
            prefManager.setPage("2")

            findNavController().navigate(
                DocWorkHoursFragmentDirections.actionGenhomeFragmentToBphomeFragment()
            )
        }

        val daysOfWeek = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, daysOfWeek)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.daySpinner.adapter = adapter
        val sessionOfDay = arrayOf("AM", "PM")
        val adapterSession = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sessionOfDay)
        adapterSession.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sessionSpinner.adapter = adapterSession

    }



    private fun setUpObserver() {
        val gson = Gson()
//        val daySchedules = response.data.result.flatMap { it.workSchedule }
        val response = gson.fromJson(json, WorkResponseModel::class.java)
        val daySchedules = response.data.result
        workHoursAdapter = WorkHoursAdapter(requireContext(),this@DocWorkHoursFragment, daySchedules)
        binding.recycler.adapter = workHoursAdapter
        binding.recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
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

}
