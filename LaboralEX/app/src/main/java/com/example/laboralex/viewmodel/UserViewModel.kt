package com.example.laboralex.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.SkillDao
import com.example.laboralex.database.dao.UserDao
import com.example.laboralex.database.dao.UserSkillDao
import com.example.laboralex.database.entity.Skill
import com.example.laboralex.database.entity.User
import com.example.laboralex.database.entity.UserSkill
import com.example.laboralex.ui.components.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userDao: UserDao,
    private val skillDao: SkillDao,
    private val userSkillDao: UserSkillDao
) : ViewModel() {

    val allSkills = mutableListOf<Skill>()
    val userSkills = mutableStateListOf<String>()

    init {
        viewModelScope.launch {
            allSkills.addAll(skillDao.getAll())
            _loadingState.value = State.LOADED
        }
    }

    private val _loadingState = MutableStateFlow(State.LOADING)
    val loadingState = _loadingState.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _surnames = MutableStateFlow("")
    val surnames = _surnames.asStateFlow()

    private val _skill = MutableStateFlow("")
    val skill = _skill.asStateFlow()

    private val _requiredName = MutableStateFlow(false)
    val requiredName = _requiredName.asStateFlow()

    private val _requiredSurnames = MutableStateFlow(false)
    val requiredSurnames = _requiredSurnames.asStateFlow()

    fun changeName(newName: String) {
        _name.value = newName
    }

    fun changeRequiredName() {
        _requiredName.value = false
    }

    fun changeRequiredSurnames() {
        _requiredSurnames.value = false
    }

    fun changeSurnames(newSurname: String) {
        _surnames.value = newSurname
    }

    fun changeSkill(newSkill: String) {
        _skill.value = newSkill
    }

    fun onContinuePressed(): Boolean {
        val nameBlank = _name.value.isBlank()
        val surnamesBlank = _surnames.value.isBlank()
        if (nameBlank) _requiredName.value = true
        if (surnamesBlank) _requiredSurnames.value = true

        return !nameBlank && !surnamesBlank
    }

    fun saveUser() {
        viewModelScope.launch {
            val userId = userDao.insert(
                User(
                    firstName = name.value,
                    surnames = surnames.value,
                    profilePictureId = null
                )
            )

            val newSkills =
                userSkills.filter { it !in allSkills.map { skill -> skill.name } }
                    .map { name -> Skill(name = name) }

            val existingSkills =
                allSkills.filter { it.name in userSkills }.map { skill -> skill.id }

            val userSkillsIds = skillDao.insertAll(*newSkills.toTypedArray()) + existingSkills

            val userSkillsToInsert =
                userSkillsIds.map { UserSkill(userId = userId, skillId = it) }

            userSkillDao.insertAll(*userSkillsToInsert.toTypedArray())
        }
    }
}