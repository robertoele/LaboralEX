package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.AppStateRepository
import com.example.laboralex.database.dao.CompanyDao
import com.example.laboralex.database.dao.CompanySkillDao
import com.example.laboralex.database.dao.SkillDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsertCompaniesViewModel @Inject constructor(
    companyDao: CompanyDao,
    skillDao: SkillDao,
    companySkillDao: CompanySkillDao,
    private val appStateRepository: AppStateRepository
) :
    ViewModel() {
    val appStateFlow = appStateRepository.formMadeFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, AppStateRepository.AppState(false))
    val companiesAddedFlow = companyDao.getCompaniesAsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    val skillsFlow = skillDao.getAllAsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    val companySkillsFlow = companySkillDao.getAllAsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun finishForm() {
        viewModelScope.launch {
            appStateRepository.updateFormMade(true)
        }
    }
}