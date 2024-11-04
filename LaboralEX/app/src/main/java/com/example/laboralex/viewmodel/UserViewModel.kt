package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.UserDao
import com.example.laboralex.database.entity.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val userDao: UserDao): ViewModel() {
    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _firstSurname = MutableStateFlow("")
    val firstSurname = _firstSurname.asStateFlow()

    private val _secondSurname = MutableStateFlow("")
    val secondSurname = _secondSurname.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    fun changeName(newName: String) {
        _name.value = newName
    }

    fun changeSurname(newSurname: String) {
        _firstSurname.value = newSurname
    }

    fun changeSecondSurname(newSurname: String) {
        _secondSurname.value = newSurname
    }

    fun changeDescription(newDescription: String) {
        _description.value = newDescription
    }

    fun saveUser() {
        viewModelScope.launch {
            userDao.insertAll(
                User(
                    firstName = name.value,
                    firstSurname = firstSurname.value,
                    secondSurname = secondSurname.value,
                    profilePictureId = null
                )
            )
        }
    }
}