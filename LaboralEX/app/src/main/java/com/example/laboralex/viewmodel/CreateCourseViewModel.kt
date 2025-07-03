package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.CourseDao
import com.example.laboralex.database.dao.CourseSkillDao
import com.example.laboralex.database.dao.SkillDao
import com.example.laboralex.database.entity.Course
import com.example.laboralex.database.entity.CourseSkill
import com.example.laboralex.database.entity.Skill
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
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

    private val _addedSkills = MutableStateFlow(emptyList<Skill>())
    val addedSkills = _addedSkills.asStateFlow()

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
        val newList = _addedSkills.value.toMutableList()
        newList.add(skill)
        _addedSkills.value = newList
        _skillField.value = ""
    }

    fun addSkill(skill: Skill) {
        val newList = _addedSkills.value.toMutableList()
        newList.add(skill)
        _addedSkills.value = newList
    }

    fun addCourse() {
        val newSkills = _addedSkills.value.filter { it.notInDB() }
        val course = Course(name = _nameField.value, reference = _linkField.value)
        viewModelScope.launch {
            skillDao.insertAll(*newSkills.toTypedArray())
            val courseId = courseDao.insert(course)
            val courseSkills = _addedSkills.value.map { CourseSkill(courseId, it.id) }
            courseSkillDao.insertAll(*courseSkills.toTypedArray())
        }
        clearNameField()
        clearLinkField()
        clearSkillField()
        _addedSkills.value = emptyList()
    }
}