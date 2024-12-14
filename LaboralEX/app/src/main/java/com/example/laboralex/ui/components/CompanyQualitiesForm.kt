package com.example.laboralex.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.laboralex.database.entity.Speciality
import com.example.laboralex.viewmodel.CompanyViewModel
import com.example.laboralex.viewmodel.SpecialityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownTextField(
    companyViewModel: CompanyViewModel,
    specialityViewModel: SpecialityViewModel,
    specialities: List<Speciality>,
    onSpecialityAdd: (speciality: String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            val name = specialityViewModel.name.collectAsStateWithLifecycle()
            val filteredSpecialities =
                specialities.filter { it.name.contains(name.value, ignoreCase = true) }
            TextField(
                value = name.value,
                onValueChange = {
                    specialityViewModel.changeName(it)
                    expanded = specialities.isNotEmpty()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryEditable),
                trailingIcon = {
                    Button(onClick = { onSpecialityAdd(name.value) }) {
                        Icon(Icons.Default.Add, contentDescription = null)
                    }
                }
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                filteredSpecialities.forEach {
                    DropdownMenuItem(
                        text = { Text(it.name) },
                        onClick = { }
                    )
                }
            }
        }
    }
}