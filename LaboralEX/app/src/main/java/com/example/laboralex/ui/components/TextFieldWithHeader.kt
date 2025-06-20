package com.example.laboralex.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldWithHeader(
    value: String,
    name: String,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource? = null,
    onValueChanged: (String) -> Unit
) {
    Text(
        name,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSecondaryContainer
    )
    OutlinedTextField(
        modifier = modifier.clip(RoundedCornerShape(3.dp)),
        value = value,
        onValueChange = onValueChanged,
        interactionSource = interactionSource,
        label = { Text(name) }
    )
}