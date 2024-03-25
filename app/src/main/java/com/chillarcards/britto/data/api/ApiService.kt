package com.chillarcards.britto.data.api

import com.chillarcards.britto.data.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * @Author: Sherin Jaison
 * @Date: 20-02-2024$
 * Chillar
 */
interface ApiService {

    @POST("login/create_otp")
    suspend fun verifyMobile(
        @Body reqModel: RegisterRequestModel
    ): Response<RegisterModel>


    @POST("login/verify_otp")
    suspend fun verifyOtp(
        @Body otpModel: OTPRequestModel
    ): Response<OTPModel>

    @POST("login/resend_otp")
    suspend fun resendOtp(
        @Body reqModel: RegisterRequestModel
    ): Response<RegisterModel>
    @GET("login/list_business_types")
    suspend fun getGeneralMenu(): Response<BusinessModel>
    @GET("business/list_province")
    suspend fun getProvince(): Response<ProvinceModel>

    @POST("business/register_business")
    suspend fun regBusiness(
        @Body reqModel: BusinessRegisterResponseModel
    ): Response<BusinessRegisterModel>
    @FormUrlEncoded
    @POST("business/business_profile_picture_upload")
    suspend fun regProfile(
        @Field("file_base64") paramValue1: String,
        @Field("business_user_uuid") paramValue2: String
    ): Response<BusinessProfileModel>

    @GET("business/list_pharmacy_item_category")
    suspend fun getCategory(): Response<PharmacyItemCategoryModel>
    @GET("business/list_pharmacy_item_brand")
    suspend fun getBrand(): Response<PharmacyItemBrandModel>

    @POST("business/business_partner")
    suspend fun getBusinessLand(
        @Body reqModel: BusinessPartnerRequestModel
    ): Response<BusinessPartnerResponseModel>
    @POST("business/list_pharmacy_items")
    suspend fun getItemList(
        @Body reqModel: ItemRequestModel
    ): Response<ItemResponseModel>
    @POST("business/add_pharmacy_item")
    suspend fun getAddItemList(
        @Body reqModel: AddItemRequestModel
    ): Response<AddItemResponseModel>
    @POST("business/edit_pharmacy_item")
    suspend fun getUpdateItemList(
        @Body reqModel: UpdateItemRequestModel
    ): Response<AddItemResponseModel>
    @POST("business/delete_pharmacy_item")
    suspend fun updateItem(
        @Body reqModel: StatusItemRequestModel
    ): Response<StatusItemResponseModel>
    @POST("business/get_working_hours")
    suspend fun getWorkHrs(
        @Body reqModel: WorkHrsRequestModel
    ): Response<WorkHrsResponseModel>

    //JSON PARAMETER FORM
    @POST("business/working_hours")
    suspend fun addWorkHrs(
        @Body jsonString: String
    ): Response<WorkHrsResponseModel>
    @POST("business/add_item_brand")
    suspend fun addBrand(
        @Body reqModel: AddItemBrandRequest
    ): Response<AddItemBrandResponse>

}