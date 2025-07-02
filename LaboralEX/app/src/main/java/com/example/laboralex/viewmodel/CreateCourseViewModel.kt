package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.CourseDao
import com.example.laboralex.database.dao.CourseSkillDao
import com.example.laboralex.database.dao.SkillDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CreateCourseViewModel @Inject constructor(
    courseDao: CourseDao,
    skillDao: SkillDao,
    courseSkillDao: CourseSkillDao
) : ViewModel() {
    private val _nameField = MutableStateFlow("")
    val nameField = _nameField.value

    private val _linkField = MutableStateFlow("")
    val linkField = _linkField.value

    val allSkills = skillDao.getAllAsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun changeNameField(newValue: String) {
        _nameField.value = newValue
    }

    fun clearNameField() = changeNameField("")

    fun changeLinkField(newValue: String) {
        _linkField.value = newValue
    }

    fun clearLinkField() = changeNameField("")
}