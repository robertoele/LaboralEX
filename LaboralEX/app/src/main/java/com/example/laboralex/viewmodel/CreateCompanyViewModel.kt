package com.example.laboralex.viewmodel

import androidx.compose.runtime.mutableStateListOf
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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCompanyViewModel @Inject constructor(
    private val companyDao: CompanyDao,
    private val skillDao: SkillDao,
    private val companySkillDao: CompanySkillDao
) : ViewModel() {

    val allSkills = mutableListOf<Skill>()
    val companySkills = mutableStateListOf<String>()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    init {
        viewModelScope.launch {
            allSkills.addAll(skillDao.getAll())
        }
    }

    fun changeName(newName: String) {
        _name.value = newName
    }

    suspend fun saveCompany() {
        val companyToInsert = Company(name = name.value)
        val companyId = companyDao.insert(companyToInsert)

        val newSkills =
            companySkills.filter { skill -> skill !in allSkills.map { it.name } }
                .map { name -> Skill(name = name) }

        val existingSkills = allSkills.filter { it.name in companySkills }
            .map { skill -> skill.id }

        val skillsIds =
            skillDao.insertAll(*newSkills.toTypedArray()) + existingSkills

        val companySkillsToInsert = skillsIds.map {
            CompanySkill(companyId = companyId, skillId = it)
        }

        companySkillDao.insertAll(*companySkillsToInsert.toTypedArray())

    }

}