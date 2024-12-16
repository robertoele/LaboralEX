package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.SpecialityDao
import com.example.laboralex.database.dao.UserDao
import com.example.laboralex.database.entity.Speciality
import com.example.laboralex.database.entity.User
import com.example.laboralex.ui.components.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userDao: UserDao,
    private val specialityDao: SpecialityDao
) : ViewModel() {

    val specialities = mutableListOf<Speciality>()

    init {
        viewModelScope.launch {
            specialities.addAll(specialityDao.getAll())
            _loadingState.value = State.LOADED
        }
    }

    private val _loadingState = MutableStateFlow(State.LOADING)
    val loadingState = _loadingState.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _surnames = MutableStateFlow("")
    val surnames = _surnames.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    private val _skill = MutableStateFlow("")
    val skill = _skill.asStateFlow()

    fun changeName(newName: String) {
        _name.value = newName
    }

    fun changeSurnames(newSurname: String) {
        _surnames.value = newSurname
    }

    fun changeDescription(newDescription: String) {
        _description.value = newDescription
    }

    fun changeSkill(newSkill: String) {
        _skill.value = newSkill
    }

    fun saveUser() {
        viewModelScope.launch {
            userDao.insertAll(
                User(
                    firstName = name.value,
                    surnames = surnames.value,
                    profilePictureId = null
                )
            )
        }
    }
}