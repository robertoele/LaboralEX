package com.example.laboralex.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.CompanyDao
import com.example.laboralex.database.dao.CompanySkillDao
import com.example.laboralex.database.dao.SkillDao
import com.example.laboralex.database.entity.Company
import com.example.laboralex.database.entity.CompanySkill
import com.example.laboralex.database.entity.Skill
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsertCompaniesViewModel @Inject constructor(
    private val companyDao: CompanyDao,
    private val skillDao: SkillDao,
    private val companySkillDao: CompanySkillDao
) :
    ViewModel() {
    private val _companiesAdded = MutableStateFlow<List<Company>>(emptyList())
    val companiesAdded: StateFlow<List<Company>> = _companiesAdded

    private val _companySkills = MutableStateFlow<List<CompanySkill>>(emptyList())
    val companySkills: StateFlow<List<CompanySkill>> = _companySkills

    private val _allSkills = MutableStateFlow<List<Skill>>(emptyList())
    val allSkills: StateFlow<List<Skill>> = _allSkills

    init {
        viewModelScope.launch {

            companyDao.getCompaniesAsFlow().collect {
                Log.i("Flows", "Companies flow insertCompanies")
                _companiesAdded.value = it
            }

            skillDao.getAllAsFlow().collect {
                Log.i("Flows", "Skills flow insertCompanies")
                _allSkills.value = it
            }

            companySkillDao.getAllAsFlow().collect {
                Log.i("Flows", "CompanySkills flow insertCompanies")
                _companySkills.value = it
            }
        }
    }
}