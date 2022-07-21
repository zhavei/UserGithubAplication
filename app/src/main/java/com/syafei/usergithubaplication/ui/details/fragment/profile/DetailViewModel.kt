package com.syafei.usergithubaplication.ui.details.fragment.profile

import RetrofitClient
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.syafei.usergithubaplication.data.model.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(apps: Application) : AndroidViewModel(apps) {

    val userViewModel = MutableLiveData<DetailUserResponse>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _onFailure = MutableLiveData<Boolean>()
    val onFailure: LiveData<Boolean> = _onFailure

    fun setupUserDetails(name: String) {
        _onFailure.value = false
        _isLoading.value = true

        RetrofitClient.apiInstance.getUserDetail(name)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    _isLoading.value = false
                    _onFailure.value = false
                    if (response.isSuccessful) {
                        userViewModel.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    _isLoading.value = false
                    _onFailure.value = true
                    Log.d(TAG, t.message.toString())
                }

            })
    }

    fun getDetailuser(): LiveData<DetailUserResponse>{
        return userViewModel
    }

    companion object {
        const val TAG = "This Details View Model"
    }

}