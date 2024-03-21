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
    override suspend fun verifyOtp(
        phone: String,
        otp: String
    ): Response<OTPModel> = apiService.verifyOtp(
        OTPRequestModel(phone,otp)
    )

    override suspend fun resendOtp(
        phone: String
    ): Response<RegisterModel> = apiService.resendOtp(
        RegisterRequestModel(phone)
    )
    override suspend fun getGeneralMenu(): Response<BusinessModel> =
        apiService.getGeneralMenu()
    override suspend fun getProvince(): Response<ProvinceModel> =
        apiService.getProvince()
    override suspend fun regBusiness(
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
    ): Response<BusinessRegisterModel> = apiService.regBusiness(
        BusinessRegisterResponseModel(businessTypeUuid,businessName,userUuid,businessLatitude,businessLongitude,
            businessAddress1,businessAddress2,businessCity,provinceUuid,businessZip,businessPhone,businessEmail)
    )
    override suspend fun regProfile(
        picture: String,
        businessUuid: String
    ): Response<BusinessProfileModel> = apiService.regProfile(picture, businessUuid)

    override suspend fun getCategory(): Response<PharmacyItemCategoryModel> =
        apiService.getCategory()
    override suspend fun getBrand(): Response<PharmacyItemBrandModel> =
        apiService.getBrand()
    override suspend fun getBusinessLand(
        businessUuid: String
    ): Response<BusinessPartnerResponseModel> = apiService.getBusinessLand(
        BusinessPartnerRequestModel(businessUuid)
    )
    override suspend fun getItemList(
        businessUuid: String
    ): Response<ItemResponseModel> = apiService.getItemList(
        ItemRequestModel(businessUuid)
    )

    override suspend fun getAddItemList(
        businessUuid: String,
        itemName: String,
        brandUuid: String,
        categoryUuid: String,
        itemPrice: String,
        itemDiscount: String,
    ): Response<AddItemResponseModel> = apiService.getAddItemList(
        AddItemRequestModel(businessUuid,itemName,brandUuid,categoryUuid,itemPrice,itemDiscount)
    )
}