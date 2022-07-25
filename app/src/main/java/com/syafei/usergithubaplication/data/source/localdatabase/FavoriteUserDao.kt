package com.syafei.usergithubaplication.data.source.localdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addToFavorite(favoriteUserEntity: FavoriteUserEntity)

    @Query("SELECT * FROM user_favorite")
    fun getFavorite() : LiveData<List<FavoriteUserEntity>>

    @Query("SELECT count(*) FROM user_favorite WHERE user_favorite.id = :id")
    fun CheckCountUser(id: Int) : Int

    @Query("DELETE FROM user_favorite WHERE user_favorite.id = :id")
    fun deleteFromFavorite(id: Int) : Int

}