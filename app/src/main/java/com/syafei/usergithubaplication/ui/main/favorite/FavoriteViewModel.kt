package com.syafei.usergithubaplication.ui.main.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.syafei.usergithubaplication.data.source.localdatabase.FavoriteUserDao
import com.syafei.usergithubaplication.data.source.localdatabase.FavoriteUserEntity
import com.syafei.usergithubaplication.data.source.localdatabase.UserDatabase

class FavoriteViewModel(app: Application) : AndroidViewModel(app) {

    private var userDao: FavoriteUserDao?
    private var userDatabase: UserDatabase? = UserDatabase.getDatabase(app)

    init {
        userDao = userDatabase?.favoriteDao()
    }

    fun getUserFavorite(): LiveData<List<FavoriteUserEntity>>? {
        return userDao?.getFavorite()
    }
}