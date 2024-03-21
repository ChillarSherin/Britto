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
    suspend fun verifyOtp(mobileNumber: String,otp: String) =
        apiHelper.verifyOtp(mobileNumber,otp)

    suspend fun resendOtp(mobileNumber: String) =
        apiHelper.resendOtp(mobileNumber)
    suspend fun getGeneralMenu() =
        apiHelper.getGeneralMenu()
    suspend fun getProvince() =
        apiHelper.getProvince()
    suspend fun regBusiness(
        businessTypeUuid: String,
        businessName: String,
        userUuid: String,
        businessLatitude: Double,
        businessLongitude: Double,
        businessAddress1: String,
        businessAddress2: String,
        businessCity: String,
        provinceUuid: String,
        businessZip: String,
        businessPhone: String,
        businessEmail: String
        )=
        apiHelper.regBusiness(businessTypeUuid,businessName,userUuid,businessLatitude,businessLongitude,
            businessAddress1,businessAddress2,businessCity,provinceUuid,businessZip,businessPhone,businessEmail)
    suspend fun regProfile(picture: String,businessUuid: String) =
        apiHelper.regProfile(picture,businessUuid)
    suspend fun getCategory() =
        apiHelper.getCategory()
    suspend fun getBrand() =
        apiHelper.getBrand()
    suspend fun getBusinessLand(businessUuid: String) =
        apiHelper.getBusinessLand(businessUuid)
    suspend fun getItemList(businessUuid: String) =
        apiHelper.getItemList(businessUuid)
    suspend fun getAddItemList(businessUuid: String,
           itemName: String,
           brandUuid: String,
           categoryUuid: String,
           itemPrice: String,
           itemDiscount: String) =
        apiHelper.getAddItemList(businessUuid,itemName,brandUuid,categoryUuid,itemPrice,itemDiscount)

}

