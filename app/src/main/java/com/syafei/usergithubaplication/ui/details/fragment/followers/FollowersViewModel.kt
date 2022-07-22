package com.syafei.usergithubaplication.ui.details.fragment.followers

import RetrofitClient
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syafei.usergithubaplication.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<User>>()

    fun setListFollowers(name: String) {
        RetrofitClient.apiInstance.getFollowers(name).enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if (response.isSuccessful) {
                    listFollowers.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d(TAG, t.message.toString())
            }

        })
    }

    fun getListFollowers(): LiveData<ArrayList<User>>{
        return listFollowers
    }

    companion object {
        const val TAG = "this Followers View Model"
    }
}