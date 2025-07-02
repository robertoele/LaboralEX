package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.SkillDao
import com.example.laboralex.database.entity.Skill
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateSkillViewModel @Inject constructor(private val skillDao: SkillDao) : ViewModel() {

    private val _skillField = MutableStateFlow("")
    val skillField = _skillField.asStateFlow()

    fun changeSkillField(newString: String) {
        _skillField.value = newString
    }

    fun addSkill() {
        val newSkill = Skill(_skillField.value)
        viewModelScope.launch { skillDao.insert(newSkill) }
    }
}