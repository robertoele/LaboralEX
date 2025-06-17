package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.CompanyDao
import com.example.laboralex.database.entity.Company
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val companyDao: CompanyDao
) : ViewModel() {

    private val _allCompanies = MutableStateFlow<List<Company>>(emptyList())
    val allCompanies: StateFlow<List<Company>> = _allCompanies

    init {
        viewModelScope.launch {
            companyDao.getCompaniesAsFlow().collect { _allCompanies.value = it }
        }
    }
}