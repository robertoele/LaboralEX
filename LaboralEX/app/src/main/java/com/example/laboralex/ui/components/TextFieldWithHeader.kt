package com.example.laboralex.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TextFieldWithHeader(
    value: String,
    name: String,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource? = null,
    onValueChanged: (String) -> Unit
) {
    Text(name, style = MaterialTheme.typography.titleMedium)
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChanged,
        interactionSource = interactionSource,
        label = { Text(name) }
    )
}