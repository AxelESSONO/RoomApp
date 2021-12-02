package com.supinfo.roomapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.supinfo.roomapp.dao.UserEntityDao
import com.supinfo.roomapp.model.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userEntityDao(): UserEntityDao

}