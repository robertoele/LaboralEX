package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.CourseDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor(courseDao: CourseDao) :
    ViewModel() {
    val courseList =
        courseDao.getAllWithSkills()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
}