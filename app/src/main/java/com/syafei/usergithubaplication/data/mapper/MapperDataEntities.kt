package com.syafei.usergithubaplication.data.mapper

import com.syafei.usergithubaplication.data.model.User
import com.syafei.usergithubaplication.data.source.localdatabase.UserEntity


object MapperDataEntities {

    private fun mapUserToEntity(user: User): UserEntity {
        return UserEntity(
            user.username,
            user.avatarUrl,
            user.htmlUrl,
            user.id
        )
    }

    fun responeToEntities(respone: List<User>): List<UserEntity> {
        return respone.map { user ->
            mapUserToEntity(user)
        }
    }
}