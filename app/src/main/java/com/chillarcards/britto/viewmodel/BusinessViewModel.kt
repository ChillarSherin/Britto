package com.chillarcards.britto.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chillarcards.britto.data.model.BusinessModel
import com.chillarcards.britto.data.repository.AuthRepository
import com.chillarcards.britto.data.model.OTPModel
import com.chillarcards.britto.data.model.PharmacyItemBrandModel
import com.chillarcards.britto.data.model.PharmacyItemCategoryModel
import com.chillarcards.britto.data.model.RegisterModel
import com.chillarcards.britto.utills.NetworkHelper
import com.chillarcards.britto.utills.Resource
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch

/**
 * @Author: Sherin Jaison
 * @Date: 20-02-2024$
 * Chillar
 */
class BusinessViewModel(
    private val authRepository: AuthRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val _menuData = MutableLiveData<Resource<BusinessModel>?>()
    val menuData: LiveData<Resource<BusinessModel>?> get() = _menuData

    private val _cateData = MutableLiveData<Resource<PharmacyItemCategoryModel>?>()
    val cateData: LiveData<Resource<PharmacyItemCategoryModel>?> get() = _cateData

    private val _brandData = MutableLiveData<Resource<PharmacyItemBrandModel>?>()
    val brandData: LiveData<Resource<PharmacyItemBrandModel>?> get() = _brandData


    fun getMenu() {
        viewModelScope.launch(NonCancellable) {
            try {
                _menuData.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    authRepository.getGeneralMenu(
                    ).let {
                        if (it.isSuccessful) {
                            _menuData.postValue(Resource.success(it.body()))
                        } else {
                            _menuData.postValue(Resource.error(it.errorBody().toString(), null))
                        }
                    }
                } else {
                    _menuData.postValue(Resource.error("No Internet Connection", null))
                }
            } catch (e: Exception) {
                Log.e("abc_otp", "verifyOTP: ", e)
            }
        }
    }

    fun getCategory() {
        viewModelScope.launch(NonCancellable) {
            try {
                _cateData.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    authRepository.getCategory(
                    ).let {
                        if (it.isSuccessful) {
                            _cateData.postValue(Resource.success(it.body()))
                        } else {
                            _cateData.postValue(Resource.error(it.errorBody().toString(), null))
                        }
                    }
                } else {
                    _cateData.postValue(Resource.error("No Internet Connection", null))
                }
            } catch (e: Exception) {
                Log.e("abc_otp", "verifyOTP: ", e)
            }
        }
    }


    fun getBrand() {
        viewModelScope.launch(NonCancellable) {
            try {
                _brandData.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    authRepository.getBrand(
                    ).let {
                        if (it.isSuccessful) {
                            _brandData.postValue(Resource.success(it.body()))
                        } else {
                            _brandData.postValue(Resource.error(it.errorBody().toString(), null))
                        }
                    }
                } else {
                    _brandData.postValue(Resource.error("No Internet Connection", null))
                }
            } catch (e: Exception) {
                Log.e("abc_otp", "verifyOTP: ", e)
            }
        }
    }


    fun clear() {
        _menuData.value = null
    }
}