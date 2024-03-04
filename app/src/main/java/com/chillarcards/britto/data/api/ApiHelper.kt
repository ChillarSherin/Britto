package com.chillarcards.britto.data.api

import com.chillarcards.britto.data.model.*
import com.chillarcards.britto.data.model.RegisterModel
import retrofit2.Response

/**
 * @Author: Sherin Jaison
 * @Date: 20-02-2024$
 * Chillar
 */

interface ApiHelper {

    suspend fun verifyMobile(
        phone: String
    ): Response<RegisterModel>


}