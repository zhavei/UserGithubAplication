package com.syafei.usergithubaplication.ui.details.fragment.following

import RetrofitClient
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syafei.usergithubaplication.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<User>>()

    fun setFollowingList(username: String) {
        RetrofitClient.apiInstance.getFollowing(username)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d(TAG, t.message.toString())
                }

            })
    }

    fun getListFollowings(): LiveData<ArrayList<User>> {
        return listFollowing
    }


    companion object {
        const val TAG = "this Following View Model"
    }

}