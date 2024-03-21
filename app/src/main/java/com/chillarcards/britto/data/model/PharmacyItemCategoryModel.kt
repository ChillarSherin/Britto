package com.chillarcards.britto.data.model

import com.google.gson.annotations.SerializedName


/**
 * Created by Sherin on 21-03-2024.
 */

data class PharmacyItemCategoryModel(
    val msg: String,
    val list_pharmacy_item_category: String,
    val code: String,
    val data: List<PharmacyItemCategory>
)

data class PharmacyItemCategory(
    val category_uuid: String,
    val category_name: String,
    val category_status: Int
)

data class PharmacyItemBrandModel(
    val msg: String,
    val list_pharmacy_item_brand: String,
    val code: String,
    val data: List<PharmacyItemBrand>
)

data class PharmacyItemBrand(
    val brand_uuid: String,
    val brand_name : String,
    val brand_status: Int
)

