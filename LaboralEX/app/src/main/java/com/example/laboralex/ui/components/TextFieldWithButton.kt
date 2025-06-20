package com.example.laboralex.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TextFieldWithButton(
    modifier: Modifier = Modifier,
    value: String,
    label: String = "",
    onValueChanged: (value: String) -> Unit,
    onValueSelected: (value: String) -> Unit
) {
    OutlinedTextField(
        value = value,
        label = { Text(label) },
        onValueChange = onValueChanged,
        modifier = modifier,
        trailingIcon = {
            Button(onClick = { onValueSelected(value) }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    )
}