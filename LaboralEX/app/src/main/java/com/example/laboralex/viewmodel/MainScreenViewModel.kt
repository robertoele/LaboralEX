package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboralex.database.dao.CourseDao
import com.example.laboralex.database.dao.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    userDao: UserDao,
    courseDao: CourseDao
) : ViewModel() {
    val userFlow = userDao.getAllAsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    val unfinishedCoursesFlow =
        courseDao.getAsFlow().transform { list ->
            emit(list.filter { !it.finished })
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
}