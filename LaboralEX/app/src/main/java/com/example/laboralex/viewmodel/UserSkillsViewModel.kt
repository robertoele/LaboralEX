package com.example.laboralex.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.SkillDao
import com.example.laboralex.database.dao.UserDao
import com.example.laboralex.database.dao.UserSkillDao
import com.example.laboralex.database.entity.Skill
import com.example.laboralex.ui.components.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSkillsViewModel @Inject constructor(
    private val userDao: UserDao,
    private val skillDao: SkillDao,
    private val userSkillDao: UserSkillDao
) : ViewModel() {

    val allSkills = mutableListOf<Skill>()
    val userSkills = mutableStateListOf<String>()

    private val _loadingState = MutableStateFlow(State.LOADING)
    val loadingState = _loadingState.asStateFlow()

    private val _skill = MutableStateFlow("")
    val skill = _skill.asStateFlow()

    init {
        viewModelScope.launch {
            allSkills.addAll(skillDao.getAll())
            _loadingState.value = State.LOADED
        }
    }

    fun changeSkill(newSkill: String) {
        _skill.value = newSkill
    }
}