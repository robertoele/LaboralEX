package com.example.laboralex.viewmodel

import androidx.lifecycle.ViewModel
import com.example.laboralex.database.entity.Company
import com.example.laboralex.database.entity.Speciality

class InsertCompaniesViewModel : ViewModel() {
    val companiesAdded = mutableMapOf<Company, List<Speciality>>()
}