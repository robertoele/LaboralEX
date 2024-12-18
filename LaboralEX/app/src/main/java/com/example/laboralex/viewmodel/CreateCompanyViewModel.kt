package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import com.example.laboralex.ui.components.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreateCompanyViewModel : ViewModel() {

    private val _loadingState = MutableStateFlow(State.LOADING)
    val loadingState = _loadingState.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    fun onNameChanged(newName: String) {
        _name.value = newName
    }
}