package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.CourseDao
import com.example.laboralex.database.dao.CourseSkillDao
import com.example.laboralex.database.dao.SkillDao
import com.example.laboralex.ui.screens.SkillSelect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CreateCourseViewModel @Inject constructor(
    courseDao: CourseDao,
    skillDao: SkillDao,
    courseSkillDao: CourseSkillDao
) : ViewModel() {
    private val _nameField = MutableStateFlow("")
    val nameField = _nameField.asStateFlow()

    private val _linkField = MutableStateFlow("")
    val linkField = _linkField.asStateFlow()

    val allSkills = skillDao.getAllAsFlow().map { it.map { skill -> SkillSelect(skill) } }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    fun changeNameField(newValue: String) {
        _nameField.value = newValue
    }

    fun clearNameField() = changeNameField("")

    fun changeLinkField(newValue: String) {
        _linkField.value = newValue
    }

    fun clearLinkField() = changeLinkField("")

    fun addCourse() {

    }
}