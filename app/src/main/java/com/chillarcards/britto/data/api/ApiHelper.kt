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

    suspend fun verifyOtp(
        phone: String,
        otp: String
    ): Response<OTPModel>
    suspend fun resendOtp(
        phone: String
    ): Response<RegisterModel>
    suspend fun getGeneralMenu():  Response<BusinessModel>
    suspend fun getProvince():  Response<ProvinceModel>
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
    ): Response<BusinessRegisterModel>
    suspend fun regProfile(
        picture: String,
        businessUuid: String
    ): Response<BusinessProfileModel>
    suspend fun getCategory(): Response<PharmacyItemCategoryModel>
    suspend fun getBrand(): Response<PharmacyItemBrandModel>
    suspend fun getBusinessLand(
        businessUuid: String
    ): Response<BusinessPartnerResponseModel>
    suspend fun getItemList(
        businessUuid: String
    ): Response<ItemResponseModel>
    suspend fun getAddItemList(
        businessUuid: String,
        itemName: String,
        brandUuid: String,
        categoryUuid: String,
        itemPrice: String,
        itemDiscount: String,
    ): Response<AddItemResponseModel>
}