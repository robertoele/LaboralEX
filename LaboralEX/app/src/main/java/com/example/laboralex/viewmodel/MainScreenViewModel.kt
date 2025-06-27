package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.CompanyDao
import com.example.laboralex.database.dao.UserDao
import com.example.laboralex.database.entity.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    companyDao: CompanyDao,
    private val userDao: UserDao
) : ViewModel() {
    val allCompanies = companyDao.getCompaniesAsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    var user: User? = null
    val loading = MutableStateFlow(true)

    init {
        viewModelScope.launch {
            user = userDao.getAll().firstOrNull()
            loading.value = false
        }
    }
}