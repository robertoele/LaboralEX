package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.CompanyDao
import com.example.laboralex.database.dao.SpecialityDao
import com.example.laboralex.database.entity.Company
import com.example.laboralex.database.entity.Speciality
import com.example.laboralex.ui.components.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyViewModel @Inject constructor(
    private val companyDao: CompanyDao,
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

    fun onNameChanged(newName: String) {
        _name.value = newName
    }

    fun save() {
        viewModelScope.launch {
            companyDao.insertAll(Company(name = name.value))
        }
    }
}