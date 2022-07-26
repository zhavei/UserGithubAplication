package com.syafei.usergithubaplication.data.model
import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("login")
    val username: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("id")
    val id: Int
)