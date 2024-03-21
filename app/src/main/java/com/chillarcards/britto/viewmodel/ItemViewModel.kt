package com.chillarcards.britto.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chillarcards.britto.data.model.AddItemResponseModel
import com.chillarcards.britto.data.model.ItemResponseModel
import com.chillarcards.britto.data.repository.AuthRepository
import com.chillarcards.britto.utills.NetworkHelper
import com.chillarcards.britto.utills.Resource
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch

/**
 * @Author: Sherin Jaison
 * @Date: 21-03-2024$
 * Chillar
 */
class ItemViewModel(
    private val authRepository: AuthRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val _itemData = MutableLiveData<Resource<ItemResponseModel>?>()
    val itemData: LiveData<Resource<ItemResponseModel>?> get() = _itemData
    private val _addItemData = MutableLiveData<Resource<AddItemResponseModel>?>()
    val addItemData: LiveData<Resource<AddItemResponseModel>?> get() = _addItemData

    var businessUuid = MutableLiveData<String>()
    var itemName = MutableLiveData<String>()
    var brandUuid = MutableLiveData<String>()
    var categoryUuid = MutableLiveData<String>()
    var itemPrice = MutableLiveData<String>()
    var itemDiscount = MutableLiveData<String>()

//    {
//        "business_uuid": "5cc9c889578b42baaa620b767260e446",
//        "item_name": "Paracetamol",
//        "brand_uuid": "a64598da-e6ff-4444-8814-fea4ef9ee7b5",
//        "category_uuid": "19174969-c36b-4277-84fc-44989ac3341d",
//        "item_price": 250,
//        "item_discount": 50
//    }
    fun getItemList() {
        viewModelScope.launch(NonCancellable) {
            try {
                _itemData.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    authRepository.getItemList(
                        businessUuid.value.toString()
                    ).let {
                        if (it.isSuccessful) {
                            _itemData.postValue(Resource.success(it.body()))
                        } else {
                            _itemData.postValue(Resource.error(it.errorBody().toString(), null))
                        }
                    }
                } else {
                    _itemData.postValue(Resource.error("No Internet Connection", null))
                }
            } catch (e: Exception) {
                Log.e("abc_otp", "verifyOTP: ", e)
            }
        }
    }

    fun addItem() {
        viewModelScope.launch(NonCancellable) {
            try {
                _addItemData.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    authRepository.getAddItemList(
                        businessUuid.value.toString(),
                        itemName.value.toString(),
                        brandUuid.value.toString(),
                        categoryUuid.value.toString(),
                        itemPrice.value.toString(),
                        itemDiscount.value.toString(),
                    ).let {
                        if (it.isSuccessful) {
                            _addItemData.postValue(Resource.success(it.body()))
                        } else {
                            _addItemData.postValue(Resource.error(it.errorBody().toString(), null))
                        }
                    }
                } else {
                    _addItemData.postValue(Resource.error("No Internet Connection", null))
                }
            } catch (e: Exception) {
                Log.e("abc_otp", "verifyOTP: ", e)
            }
        }
    }

    fun clear() {
        _itemData.value = null
    }
}