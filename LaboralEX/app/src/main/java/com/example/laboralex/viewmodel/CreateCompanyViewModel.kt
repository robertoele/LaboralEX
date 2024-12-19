package com.example.laboralex.viewmodel

import androidx.compose.runtime.mutableStateListOf
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
class CreateCompanyViewModel @Inject constructor(
    private val specialityDao: SpecialityDao
) : ViewModel() {

    val possibleSpecialities = mutableListOf<Speciality>()
    val displayed = mutableStateListOf<Speciality>()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    init {
        viewModelScope.launch {
            possibleSpecialities.addAll(specialityDao.getAll())
        }
    }

    fun onNameChanged(newName: String) {
        _name.value = newName
    }
}