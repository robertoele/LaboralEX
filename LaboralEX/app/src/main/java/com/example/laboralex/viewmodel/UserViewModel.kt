package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import com.example.laboralex.database.dao.UserDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserViewModel(userDao: UserDao): ViewModel() {
    private val _userName = MutableStateFlow("userDao.getById(0).firstName")
    val userName = _userName.asStateFlow()

    fun changeName(newName: String) {
        _userName.value = newName
    }
}