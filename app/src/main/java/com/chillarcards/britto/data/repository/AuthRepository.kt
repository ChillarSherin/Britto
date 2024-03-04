package com.chillarcards.britto.data.repository

import com.chillarcards.britto.data.api.ApiHelper

/**
 * @Author: Sherin Jaison
 * @Date: 20-02-2024
 * Chillar
 */
class AuthRepository(private val apiHelper: ApiHelper) {
    suspend fun verifyMobile(mobileNumber: String) =
        apiHelper.verifyMobile(mobileNumber)

}