package com.chillarcards.britto.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chillarcards.britto.data.repository.AuthRepository
import com.chillarcards.britto.data.model.OTPModel
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
class OTPViewModel(
    private val authRepository: AuthRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val _otpData = MutableLiveData<Resource<OTPModel>?>()
    val otpData: LiveData<Resource<OTPModel>?> get() = _otpData
    private val _reOtpData = MutableLiveData<Resource<RegisterModel>?>()

    var mob = MutableLiveData<String>()
    var otp = MutableLiveData<String>()

    fun verifyOTP() {
        viewModelScope.launch(NonCancellable) {
            try {
                _otpData.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    authRepository.verifyOtp(
                        mob.value.toString(),
                        otp.value.toString()
                    ).let {
                        if (it.isSuccessful) {
                            _otpData.postValue(Resource.success(it.body()))
                        } else {
                            _otpData.postValue(Resource.error(it.errorBody().toString(), null))
                        }
                    }
                } else {
                    _otpData.postValue(Resource.error("No Internet Connection", null))
                }
            } catch (e: Exception) {
                Log.e("abc_otp", "verifyOTP: ", e)
            }
        }
    }

    fun resendOTP() {
        viewModelScope.launch(NonCancellable) {
            try {
                _reOtpData.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    authRepository.resendOtp(
                        mob.value.toString(),
                    ).let {
                        if (it.isSuccessful) {
                            _reOtpData.postValue(Resource.success(it.body()))
                        } else {
                            _reOtpData.postValue(Resource.error(it.errorBody().toString(), null))
                        }
                    }
                } else {
                    _reOtpData.postValue(Resource.error("No Internet Connection", null))
                }
            } catch (e: Exception) {
                Log.e("abc_otp", "verifyOTP: ", e)
            }
        }
    }


    fun clear() {
        _otpData.value = null
    }
}