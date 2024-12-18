package com.example.laboralex.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.CompanyDao
import com.example.laboralex.database.dao.SpecialityDao
import com.example.laboralex.database.entity.Company
import com.example.laboralex.database.entity.CompanySpeciality
import com.example.laboralex.database.entity.Speciality
import com.example.laboralex.ui.components.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsertCompaniesViewModel @Inject constructor(
    private val specialityDao: SpecialityDao,
    private val companyDao: CompanyDao
) : ViewModel() {
    val possibleSpecialities = mutableListOf<Speciality>()
    val displayed = mutableStateListOf<Company>()
    val companySpeciality = mutableStateListOf<CompanySpeciality>()

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

    fun onNameChanged(newName: String) {
        _name.value = newName
    }

    fun saveCompany() {
        viewModelScope.launch {
            val id = companyDao.insert(Company(name = name.value))
            val companyInserted = Company(id = id, name = name.value)

            displayed.add(companyInserted)
            //companySpeciality.add()
        }
    }
}