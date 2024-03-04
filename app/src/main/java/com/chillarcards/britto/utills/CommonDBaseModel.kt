package com.chillarcards.britto.utills

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
    @SerializedName("Position" ) var positionVal : Int? = null,

)
