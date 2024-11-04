package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import com.example.laboralex.database.dao.UserDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserViewModel(userDao: UserDao): ViewModel() {
    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _surname = MutableStateFlow("")
    val surname = _surname.asStateFlow()

    private val _userSurname2 = MutableStateFlow("")
    val secondSurname = _userSurname2.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    fun changeName(newName: String) {
        _name.value = newName
    }

    fun changeSurname(newSurname: String) {
        _surname.value = newSurname
    }

    fun changeSecondSurname(newSurname: String) {
        _userSurname2.value = newSurname
    }

    fun changeDescription(newDescription: String) {
        _description.value = newDescription
    }
}