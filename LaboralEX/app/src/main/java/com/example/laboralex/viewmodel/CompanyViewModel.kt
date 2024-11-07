package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import com.example.laboralex.database.dao.CompanyDao
import com.example.laboralex.database.entity.Company
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CompanyViewModel(private val companyDao: CompanyDao): ViewModel() {
    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    fun onNameChanged(newName: String) {
        _name.value = newName
    }

    fun save() {
        companyDao.insertAll(Company(name = name.value))
    }
}