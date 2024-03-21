package com.chillarcards.britto.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chillarcards.britto.data.model.BusinessModel
import com.chillarcards.britto.data.repository.AuthRepository
import com.chillarcards.britto.data.model.OTPModel
import com.chillarcards.britto.data.model.ProvinceModel
import com.chillarcards.britto.data.model.RegisterModel
import com.chillarcards.britto.utills.NetworkHelper
import com.chillarcards.britto.utills.Resource
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch

/**
 * @Author: Sherin Jaison
 * @Date: 19-03-2024$
 * Chillar
 */
class ProvinceViewModel(
    private val authRepository: AuthRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val _provinceData = MutableLiveData<Resource<ProvinceModel>?>()
    val provinceData: LiveData<Resource<ProvinceModel>?> get() = _provinceData
    

    fun getProvince() {
        viewModelScope.launch(NonCancellable) {
            try {
                _provinceData.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    authRepository.getProvince(
                    ).let {
                        if (it.isSuccessful) {
                            _provinceData.postValue(Resource.success(it.body()))
                        } else {
                            _provinceData.postValue(Resource.error(it.errorBody().toString(), null))
                        }
                    }
                } else {
                    _provinceData.postValue(Resource.error("No Internet Connection", null))
                }
            } catch (e: Exception) {
                Log.e("abc_otp", "verifyOTP: ", e)
            }
        }
    }
    

    fun clear() {
        _provinceData.value = null
    }
}