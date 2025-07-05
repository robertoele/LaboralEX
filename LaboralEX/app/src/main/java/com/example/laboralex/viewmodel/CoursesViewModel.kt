package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.CourseDao
import com.example.laboralex.database.entity.course.Course
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor(private val courseDao: CourseDao) :
    ViewModel() {
    val courseList =
        courseDao.getAllWithSkills()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun changeCourseFinished(course: Course) {
        viewModelScope.launch {
            courseDao.update(course)
        }
    }
}