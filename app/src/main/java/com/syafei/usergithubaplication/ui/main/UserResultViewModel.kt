package com.syafei.usergithubaplication.ui.main

import RetrofitClient
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syafei.usergithubaplication.data.model.SearchUserResponse
import com.syafei.usergithubaplication.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserResultViewModel : ViewModel() {

    private val list = MutableLiveData<ArrayList<User>>()

    fun searchUsers(query: String) {
        RetrofitClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<SearchUserResponse> {
                override fun onResponse(
                    call: Call<SearchUserResponse>,
                    response: Response<SearchUserResponse>
                ) {
                    if (response.isSuccessful) {
                        list.postValue(response.body()?.listSearchResult)
                    }
                }

                override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }

            })
    }

    fun getSearchUser(): LiveData<ArrayList<User>> {
        return list
    }

    companion object {
        const val TAG = "SEARCH_USER_VIEWMODEL"
    }
}