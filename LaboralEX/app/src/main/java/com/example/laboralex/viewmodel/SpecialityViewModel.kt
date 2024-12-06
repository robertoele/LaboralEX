package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.SpecialityDao
import com.example.laboralex.database.entity.Speciality
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpecialityViewModel @Inject constructor(private val specialityDao: SpecialityDao) :
    ViewModel() {

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()
    
    val specialities: MutableList<Speciality> = mutableListOf()

    init {
        viewModelScope.launch {
            specialities.addAll(getSpecialities())
        }
    }

    fun changeName(newName: String) {
        _name.value = newName
    }

    fun save(speciality: Speciality) {
        viewModelScope.launch {
            specialityDao.insertAll(speciality)
        }
    }

    fun delete(speciality: Speciality) {
        viewModelScope.launch {
            specialityDao.delete(speciality)
        }
    }

    suspend fun getSpecialities() = specialityDao.getAll()
}