package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.SkillDao
import com.example.laboralex.database.dao.UserSkillDao
import com.example.laboralex.database.entity.Skill
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSkillsViewModel @Inject constructor(
    private val skillDao: SkillDao,
    userSkillDao: UserSkillDao
) :
    ViewModel() {

    val userSkills = userSkillDao.getAllAsFlow()
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )
    val allSkills = skillDao.getAllAsFlow()
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    private val _skill = MutableStateFlow("")
    val skill = _skill.asStateFlow()

    fun changeSkill(newSkill: String) {
        _skill.value = newSkill
    }

    fun addSkill(newSkill: String = skill.value) {
        val skillToInsert = Skill(newSkill)
        viewModelScope.launch {
            skillDao.insert(skillToInsert)
            changeSkill("")
        }
    }
}