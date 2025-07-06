package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.CompanyDao
import com.example.laboralex.database.dao.CourseDao
import com.example.laboralex.database.dao.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    userDao: UserDao,
    courseDao: CourseDao,
    companyDao: CompanyDao
) : ViewModel() {
    val userFlow = userDao.getAllAsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val notFinishedCoursesFlow = courseDao.getAsFlow()
        .map { courses -> courses.filter { !it.finished } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val sortedCompaniesFlow = companyDao.getCompaniesWithSkills()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
}