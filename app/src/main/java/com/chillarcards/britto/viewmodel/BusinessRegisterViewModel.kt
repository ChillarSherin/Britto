package com.chillarcards.britto.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chillarcards.britto.data.model.BusinessPartnerResponseModel
import com.chillarcards.britto.data.model.BusinessProfileModel
import com.chillarcards.britto.data.model.BusinessRegisterModel
import com.chillarcards.britto.data.repository.AuthRepository
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
class BusinessRegisterViewModel(
    private val authRepository: AuthRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val _busRegData = MutableLiveData<Resource<BusinessRegisterModel>?>()
    val busRegData: LiveData<Resource<BusinessRegisterModel>?> get() = _busRegData
    private val _busPicData = MutableLiveData<Resource<BusinessProfileModel>?>()
    val busPicData: LiveData<Resource<BusinessProfileModel>?> get() = _busPicData
    private val _busLandData = MutableLiveData<Resource<BusinessPartnerResponseModel>?>()
    val busLandData: LiveData<Resource<BusinessPartnerResponseModel>?> get() = _busLandData

    val businessTypeUuid = MutableLiveData<String>()
    val businessName = MutableLiveData<String>()
    val userUuid = MutableLiveData<String>()
    val businessLatitude = MutableLiveData<Double>()
    val businessLongitude = MutableLiveData<Double>()
    val businessAddress1 = MutableLiveData<String>()
    val businessAddress2 = MutableLiveData<String>()
    val businessCity = MutableLiveData<String>()
    val provinceUuid = MutableLiveData<String>()
    val businessZip = MutableLiveData<String>()
    val businessPhone = MutableLiveData<String>()
    val businessEmail = MutableLiveData<String>()
    val businessProfile = MutableLiveData<String>()
    val businessUuid = MutableLiveData<String>()
    val businessUserUuid = MutableLiveData<String>()

    fun regBusiness() {
        viewModelScope.launch(NonCancellable) {
            try {
                _busRegData.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    authRepository.regBusiness(
                        businessTypeUuid.value.toString(),
                        businessName.value.toString(),
                        userUuid.value.toString(),
                        businessLatitude.value!!.toDouble(),
                        businessLongitude.value!!.toDouble(),
                        businessAddress1.value.toString(),
                        businessAddress2.value.toString(),
                        businessCity.value.toString(),
                        provinceUuid.value.toString(),
                        businessZip.value.toString(),
                        businessPhone.value.toString(),
                        businessEmail.value.toString(),
                    ).let {
                        if (it.isSuccessful) {
                            _busRegData.postValue(Resource.success(it.body()))
                        } else {
                            _busRegData.postValue(Resource.error(it.errorBody().toString(), null))
                        }
                    }
                } else {
                    _busRegData.postValue(Resource.error("No Internet Connection", null))
                }
            } catch (e: Exception) {
                Log.e("abc_otp", "verifyOTP: ", e)
            }
        }
    }

    fun regBusinessProfile() {
        viewModelScope.launch(NonCancellable) {
            try {
                _busPicData.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    authRepository.regProfile(
                        businessProfile.value.toString(),
                        businessUuid.value.toString(),
                    ).let {
                        if (it.isSuccessful) {
                            _busPicData.postValue(Resource.success(it.body()))
                        } else {
                            _busPicData.postValue(Resource.error(it.errorBody().toString(), null))
                        }
                    }
                } else {
                    _busPicData.postValue(Resource.error("No Internet Connection", null))
                }
            } catch (e: Exception) {
                Log.e("abc_otp", "verifyOTP: ", e)
            }
        }
    }

    fun regBusinessLand() {
        viewModelScope.launch(NonCancellable) {
            try {
                _busLandData.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    authRepository.getBusinessLand(
                        businessUserUuid.value.toString()
                    ).let {
                        if (it.isSuccessful) {
                            _busLandData.postValue(Resource.success(it.body()))
                        } else {
                            _busLandData.postValue(Resource.error(it.errorBody().toString(), null))
                        }
                    }
                } else {
                    _busLandData.postValue(Resource.error("No Internet Connection", null))
                }
            } catch (e: Exception) {
                Log.e("abc_otp", "verifyOTP: ", e)
            }
        }
    }
    fun clear() {
        _busRegData.value = null
        _busPicData.value = null
        _busLandData.value = null
    }
}