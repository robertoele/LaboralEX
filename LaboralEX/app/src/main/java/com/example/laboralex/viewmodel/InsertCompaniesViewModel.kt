package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.AppStateRepository
import com.example.laboralex.database.dao.CompanyDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsertCompaniesViewModel @Inject constructor(
    companyDao: CompanyDao,
    private val appStateRepository: AppStateRepository
) : ViewModel() {
    val companiesAddedFlow = companyDao.getCompaniesWithSkills()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun finishForm() {
        viewModelScope.launch {
            appStateRepository.updateFormMade(true)
        }
    }
}