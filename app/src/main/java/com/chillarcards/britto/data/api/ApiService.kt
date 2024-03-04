package com.chillarcards.britto.data.api

import com.chillarcards.britto.data.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @Author: Sherin Jaison
 * @Date: 20-02-2024$
 * Chillar
 */
interface ApiService {

    @POST("auth/register")
    suspend fun verifyMobile(
        @Body reqModel: RegisterRequestModel
    ): Response<RegisterModel>


}