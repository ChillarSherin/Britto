package com.chillarcards.britto.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chillarcards.britto.data.model.AddItemBrandResponse
import com.chillarcards.britto.data.model.AddItemResponseModel
import com.chillarcards.britto.data.model.ItemResponseModel
import com.chillarcards.britto.data.model.StatusItemRequestModel
import com.chillarcards.britto.data.model.StatusItemResponseModel
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

    private val _statusItemData = MutableLiveData<Resource<StatusItemResponseModel>?>()
    val statusItemData: LiveData<Resource<StatusItemResponseModel>?> get() = _statusItemData

    private val _addBrandData = MutableLiveData<Resource<AddItemBrandResponse>?>()
    val addBrandData: LiveData<Resource<AddItemBrandResponse>?> get() = _addBrandData

    var businessUuid = MutableLiveData<String>()
    var brandName = MutableLiveData<String>()
    var itemUuid = MutableLiveData<String>()
    var itemName = MutableLiveData<String>()
    var brandUuid = MutableLiveData<String>()
    var categoryUuid = MutableLiveData<String>()
    var itemPrice = MutableLiveData<String>()
    var itemDiscount = MutableLiveData<String>()


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
    fun updateItem() {
        viewModelScope.launch(NonCancellable) {
            try {
                _addItemData.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    authRepository.getUpdateItemList(
                        itemUuid.value.toString(),
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
    fun statusItem() {
        viewModelScope.launch(NonCancellable) {
            try {
                _statusItemData.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    authRepository.updateItem(
                        itemUuid.value.toString(),
                    ).let {
                        if (it.isSuccessful) {
                            _statusItemData.postValue(Resource.success(it.body()))
                        } else {
                            _statusItemData.postValue(Resource.error(it.errorBody().toString(), null))
                        }
                    }
                } else {
                    _statusItemData.postValue(Resource.error("No Internet Connection", null))
                }
            } catch (e: Exception) {
                Log.e("abc_otp", "verifyOTP: ", e)
            }
        }
    }
    fun addBrand() {
        viewModelScope.launch(NonCancellable) {
            try {
                _addBrandData.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    authRepository.addBrand(
                        businessUuid.value.toString(),
                        brandName.value.toString()
                    ).let {
                        if (it.isSuccessful) {
                            _addBrandData.postValue(Resource.success(it.body()))
                        } else {
                            _addBrandData.postValue(Resource.error(it.errorBody().toString(), null))
                        }
                    }
                } else {
                    _addBrandData.postValue(Resource.error("No Internet Connection", null))
                }
            } catch (e: Exception) {
                Log.e("abc_otp", "verifyOTP: ", e)
            }
        }
    }
    fun clear() {
        _itemData.value = null
        _addItemData.value = null
        _statusItemData.value = null
        _addBrandData.value = null
    }
}