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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCompanyViewModel @Inject constructor(
    private val companyDao: CompanyDao,
    private val skillDao: SkillDao,
    private val companySkillDao: CompanySkillDao
) : ViewModel() {

    val skillsFlow = skillDao.getAllAsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val companySkills = mutableStateListOf<String>()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _requiredName = MutableStateFlow(false)
    val requiredName = _requiredName.asStateFlow()

    private val _emptySkill = MutableStateFlow(false)
    val emptySkill = _emptySkill.asStateFlow()

    fun changeName(newName: String) {
        _name.value = newName
    }

    fun clearName() {
        _name.value = ""
    }

    fun changeRequired() {
        _requiredName.value = false
    }

    fun changeEmptySkill() {
        _emptySkill.value = !_emptySkill.value
    }

    fun onContinuePressed(): Boolean {
        val nameBlank = _name.value.isBlank()
        if (nameBlank) _requiredName.value = true
        return !nameBlank
    }

    fun onCompanyAdded() {
        viewModelScope.launch {
            saveCompany()
            companySkills.clear()
        }
        changeName("")
    }

    private suspend fun saveCompany() {
        val companyToInsert = Company(name = name.value)
        val companyId = companyDao.insert(companyToInsert)

        val newSkills =
            companySkills.filter { skill -> skill !in skillsFlow.value.map { it.name } }
                .map { name -> Skill(name = name) }

        val existingSkills = skillsFlow.value.filter { it.name in companySkills }
            .map { skill -> skill.id }

        val skillsIds =
            skillDao.insertAll(*newSkills.toTypedArray()) + existingSkills

        val companySkillsToInsert = skillsIds.map {
            CompanySkill(companyId = companyId, skillId = it)
        }

        companySkillDao.insertAll(*companySkillsToInsert.toTypedArray())

    }
}