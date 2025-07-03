package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.CourseDao
import com.example.laboralex.database.dao.CourseSkillDao
import com.example.laboralex.database.dao.SkillDao
import com.example.laboralex.database.entity.Skill
import com.example.laboralex.database.entity.course.Course
import com.example.laboralex.database.entity.course.CourseSkill
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCourseViewModel @Inject constructor(
    private val courseDao: CourseDao,
    private val skillDao: SkillDao,
    private val courseSkillDao: CourseSkillDao
) : ViewModel() {
    private val _nameField = MutableStateFlow("")
    val nameField = _nameField.asStateFlow()

    private val _linkField = MutableStateFlow("")
    val linkField = _linkField.asStateFlow()

    private val _skillField = MutableStateFlow("")
    val skillField = _skillField.asStateFlow()

    val allSkills = skillDao.getAllAsFlow().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    private val _existingAddedSkills = MutableStateFlow(emptyList<Skill>())
    private val _newAddedSkills = MutableStateFlow(emptyList<Skill>())

    val displayedSkills =
        _existingAddedSkills
            .combine(_newAddedSkills) { existing, new -> existing + new }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun changeNameField(newValue: String) {
        _nameField.value = newValue
    }

    fun clearNameField() = changeNameField("")

    fun changeLinkField(newValue: String) {
        _linkField.value = newValue
    }

    fun clearLinkField() = changeLinkField("")

    fun changeSkillField(newValue: String) {
        _skillField.value = newValue
    }

    fun clearSkillField() = changeSkillField("")

    fun addSkill() {
        val skill = Skill(_skillField.value)
        val skillInDB = _skillField.value in allSkills.value.map { it.name }
        if (skillInDB) {
            val newList = _existingAddedSkills.value + skill
            _existingAddedSkills.value = newList
        } else {
            val newList = _newAddedSkills.value + skill
            _newAddedSkills.value = newList
        }
        _skillField.value = ""
    }

    fun addSkill(skill: Skill) {
        val newList = _existingAddedSkills.value.toMutableList()
        newList.add(skill)
        _existingAddedSkills.value = newList
    }

    fun addCourse() {
        val course = Course(name = _nameField.value, reference = _linkField.value)
        viewModelScope.launch {
            val newIds = skillDao.insertAll(*_newAddedSkills.value.toTypedArray())
            val courseId = courseDao.insert(course)
            val courseSkills = _existingAddedSkills.value
                .map { CourseSkill(courseId, it.skillId) } +
                    newIds.map { CourseSkill(courseId, it) }
            courseSkillDao.insertAll(*courseSkills.toTypedArray())
            clearNameField()
            clearLinkField()
            clearSkillField()
            _existingAddedSkills.value = emptyList()
        }
    }
}