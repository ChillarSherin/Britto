package com.chillarcards.britto.data.model

data class WorkHrsResponseModel(
    val msg: String,
    val get_working_hours: String,
    val code: Int,
    val data: List<DaySchedule>
)
data class DaySchedule(
    val dayStatus: Int,
    val day: String,
    val workSchedule: List<WorkSchedule>
)
data class WorkSchedule(
    val work_schedule_uuid: String,
    val day: String,
    val startTime: String,
    val session: String,
    val endTime: String,
    val status: Int
)


data class WorkHrsRequestModel(
     val business_uuid: String
)

data class WorkHoursRequest(
    val business_uuid: String,
    val monday: List<WorkHour>
)

data class WorkHour(
    val start_time: String,
    val end_time: String,
    val session: String
)