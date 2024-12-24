package com.example.laboralex.viewmodel

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

    val allSkills = mutableListOf<Skill>()

    init {
        viewModelScope.launch {
            allSkills.addAll(skillDao.getAll())

            companyDao.getCompaniesAsFlow().collect {
                _companiesAdded.value = it
            }

            companySkillDao.getAllAsFlow().collect { skills ->
                _companySkills.value = skills
            }
        }
    }
}