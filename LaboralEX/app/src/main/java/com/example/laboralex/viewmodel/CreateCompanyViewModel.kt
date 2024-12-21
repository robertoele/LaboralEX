package com.example.laboralex.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.CompanyDao
import com.example.laboralex.database.dao.CompanySpecialityDao
import com.example.laboralex.database.dao.SpecialityDao
import com.example.laboralex.database.entity.Company
import com.example.laboralex.database.entity.CompanySpeciality
import com.example.laboralex.database.entity.Speciality
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCompanyViewModel @Inject constructor(
    private val companyDao: CompanyDao,
    private val specialityDao: SpecialityDao,
    private val companySpecialityDao: CompanySpecialityDao
) : ViewModel() {

    val possibleSpecialities = mutableListOf<Speciality>()
    val specialitiesAdded = mutableStateListOf<Speciality>()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    init {
        viewModelScope.launch {
            possibleSpecialities.addAll(specialityDao.getAll())
        }
    }

    fun changeName(newName: String) {
        _name.value = newName
    }

    fun saveCompany() {
        val companyToInsert = Company(name = name.value)
        viewModelScope.launch {
            val companyId = companyDao.insert(companyToInsert)
            val companySpeciality = specialitiesAdded.map {
                CompanySpeciality(companyId = companyId, specialityId = it.id)
            }
            companySpecialityDao.insertAll(*companySpeciality.toTypedArray())
        }
    }

    fun addSpeciality(speciality: Speciality) = specialitiesAdded.add(speciality)
}