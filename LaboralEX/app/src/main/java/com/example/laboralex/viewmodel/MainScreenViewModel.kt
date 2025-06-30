package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.CompanyDao
import com.example.laboralex.database.dao.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    companyDao: CompanyDao,
    userDao: UserDao
) : ViewModel() {
    val allCompanies = companyDao.getCompaniesAsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    val userFlow = userDao.getAllAsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
}