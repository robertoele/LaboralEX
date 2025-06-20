package com.example.laboralex.viewmodel

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.ApplicationDao
import com.example.laboralex.database.dao.CompanyDao
import com.example.laboralex.database.entity.Application
import com.example.laboralex.database.entity.Company
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val companyDao: CompanyDao,
    private val applicationDao: ApplicationDao
) : ViewModel() {
    private val _allCompanies = MutableStateFlow<List<Company>>(emptyList())
    val allCompanies: StateFlow<List<Company>> = _allCompanies

    private val _applications = MutableStateFlow<List<Application>>(emptyList())
    val applications: StateFlow<List<Application>> = _applications

    init {
        viewModelScope.launch {
            companyDao.getCompaniesAsFlow().collect { _allCompanies.value = it }
            applicationDao.getAsFlow().collect { _applications.value = it }
        }
    }

    fun createJobApplication(company: Company) {
        val requestDate = Calendar.getInstance().time
        val application =
            Application(
                SimpleDateFormat.getDateInstance().format(requestDate),
                Application.ApplicationState.OPENED.ordinal,
                company.id
            )
        viewModelScope.launch { applicationDao.insert(application) }
    }
}