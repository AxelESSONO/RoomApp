package com.supinfo.roomapp

import com.supinfo.roomapp.model.UserEntity

interface ClickOnItemListener {

    fun onItemLClicked(userEntity: UserEntity)

    fun onUserInserted(userEntity: UserEntity)

}