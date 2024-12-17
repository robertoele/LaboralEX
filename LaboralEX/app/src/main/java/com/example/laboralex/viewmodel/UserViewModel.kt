package com.example.laboralex.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.SpecialityDao
import com.example.laboralex.database.dao.UserDao
import com.example.laboralex.database.dao.UserSpecialityDao
import com.example.laboralex.database.entity.Speciality
import com.example.laboralex.database.entity.User
import com.example.laboralex.database.entity.UserSpeciality
import com.example.laboralex.ui.components.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userDao: UserDao,
    private val specialityDao: SpecialityDao,
    private val userSpecialityDao: UserSpecialityDao
) : ViewModel() {

    val possibleSpecialities = mutableListOf<Speciality>()
    val userSpecialities = mutableStateListOf<String>()

    init {
        viewModelScope.launch {
            possibleSpecialities.addAll(specialityDao.getAll())
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
            val userId = userDao.insert(
                User(
                    firstName = name.value,
                    surnames = surnames.value,
                    profilePictureId = null
                )
            )

            val specialitiesNames = userSpecialities.filter {
                it !in specialityDao.getAll().map { speciality -> speciality.name }
            }

            val specialitiesToAdd = specialitiesNames.map { Speciality(name = it) }

            val specialityIds = specialityDao.insertAll(*specialitiesToAdd.toTypedArray())
            val userSpecialities =
                specialityIds.map { UserSpeciality(userId = userId, specialityId = it) }
            userSpecialityDao.insertAll(*userSpecialities.toTypedArray())
        }
    }
}