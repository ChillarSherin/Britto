package com.chillarcards.britto.utills

import com.chillarcards.britto.data.model.PharmacyItemBrand
import com.chillarcards.britto.data.model.PharmacyItemCategory
import com.chillarcards.britto.data.model.ProvinceData
import com.chillarcards.britto.data.model.WorkSchedule
import com.google.gson.annotations.SerializedName
/**
 * @Author: Sherin Jaison
 * @Date: 20-02-2024$
 * Chillar
 */
data class CommonDBaseModel  (

    @SerializedName("MastIDs"   ) var mastIDs   : String?    = null,
    @SerializedName("ItmName"   ) var itmName   : String? = null,
    @SerializedName("Mobile" ) var mobile : String? = null,
    @SerializedName("ValueStr1" ) var valueStr1 : String? = null,
    @SerializedName("ValueStr2" ) var valueStr2 : String? = null,
    @SerializedName("ValueStr3" ) var valueStr3 : String? = null,
    @SerializedName("ValueStr4" ) var valueStr4 : String? = null,
    @SerializedName("Position" ) var positionVal : Int? = null,
    @SerializedName("ListData" ) var listData :  List<WorkSchedule>? = null,
    @SerializedName("UuMastIDs"   ) var uuidMastIDs   : Long?    = null,

    )
fun ProvinceData.toSpinnerItmBaseModel() :CommonDBaseModel {
    return CommonDBaseModel(
        mastIDs = this.province_uuid,
        itmName = this.province_name
    )
}

fun ProvinceData.toSpinnerApiItmBaseModel() :CommonDBaseModel {
    return CommonDBaseModel(
        mastIDs = this.province_uuid,
        itmName = this.province_name
    )
}
fun PharmacyItemCategory.toSpinnerItmBaseModel() :CommonDBaseModel {
    return CommonDBaseModel(
        mastIDs = this.category_uuid,
        itmName = this.category_name
    )
}

fun PharmacyItemCategory.toSpinnerApiItmBaseModel() :CommonDBaseModel {
    return CommonDBaseModel(
        mastIDs = this.category_uuid,
        itmName = this.category_name
    )
}

fun PharmacyItemBrand.toSpinnerItmBaseModel() :CommonDBaseModel {
    return CommonDBaseModel(
        mastIDs = this.brand_uuid,
        itmName = this.brand_name
    )
}

fun PharmacyItemBrand.toSpinnerApiItmBaseModel() :CommonDBaseModel {
    return CommonDBaseModel(
        mastIDs = this.brand_uuid,
        itmName = this.brand_name
    )
}