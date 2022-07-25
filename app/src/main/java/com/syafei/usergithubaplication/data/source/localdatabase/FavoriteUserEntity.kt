package com.syafei.usergithubaplication.data.source.localdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.syafei.usergithubaplication.data.model.User
import java.io.Serializable


@Entity(tableName = "user_favorite")
data class FavoriteUserEntity (
    val username: String,
    val avatarUrl: String,
    val htmlUrl: String,
    @PrimaryKey
    val id: Int,
    val listSearchResult: ArrayList<String>
    ) : Serializable