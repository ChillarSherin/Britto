package com.chillarcards.britto.data.api

import com.chillarcards.britto.data.model.*
import retrofit2.Response

/**
 * @Author: Sherin Jaison
 * @Date: 20-02-2024$
 * Chillar
 */
class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    override suspend fun verifyMobile(
        phone: String
    ): Response<RegisterModel> = apiService.verifyMobile(
        RegisterRequestModel(phone)
    )

}