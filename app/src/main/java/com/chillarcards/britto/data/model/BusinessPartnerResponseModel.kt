package com.chillarcards.britto.data.model


data class BusinessPartnerResponseModel(
    val msg: String,
    val business_partner: String,
    val code: String,
    val data: List<BusinessData>
)

data class BusinessData(
    val business_type: String,
    val business_name: String,
    val user_uuid: String,
    val business_uuid: String,
    val business_status: Int,
    val business_address1: String,
    val business_address2: String,
    val business_city: String,
    val business_zip: String,
    val business_phone: String,
    val business_email: String
)


data class BusinessPartnerRequestModel(
  val business_user_uuid: String
)