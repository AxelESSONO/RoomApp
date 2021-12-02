package com.supinfo.roomapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.supinfo.roomapp.model.UserEntity

@Dao
interface UserEntityDao {

    @Query("SELECT * FROM userentity")
    fun getAll(): MutableList<UserEntity>

    //@Query("SELECT * FROM userentity WHERE uid IN (:userIds)")
    //fun loadAllByIds(userIds: IntArray): List<UserEntity>

    @Query("SELECT * FROM userentity WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): UserEntity

    @Insert
    fun insertAll(vararg users: UserEntity)

    @Delete
    fun delete(user: UserEntity)

}