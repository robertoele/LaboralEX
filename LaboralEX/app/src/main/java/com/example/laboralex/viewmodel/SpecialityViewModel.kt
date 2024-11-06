package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.SpecialityDao
import com.example.laboralex.database.entity.Speciality
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SpecialityViewModel(private val specialityDao: SpecialityDao): ViewModel() {

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    fun changeName(newName: String) {
        _name.value = newName
    }

    fun save() {
        viewModelScope.launch {
            specialityDao.insertAll(Speciality(name = _name.value))
        }
    }

    fun delete(speciality: Speciality) {
        viewModelScope.launch {
            specialityDao.delete(speciality)
        }
    }
}