package com.syafei.usergithubaplication.data.source.localdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "user_favorite")
data class UserEntity(
    val username: String,
    val avatarUrl: String,
    val htmlUrl: String,
    @PrimaryKey
    val id: Int,
) : Serializable