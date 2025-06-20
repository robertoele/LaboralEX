package com.example.laboralex.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun FormTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onClearPressed: () -> Unit,
    label: @Composable (() -> Unit)? = null,
    interactionSource: MutableInteractionSource? = null
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        interactionSource = interactionSource,
        label = label,
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = onClearPressed) {
                    Icon(
                        Icons.Rounded.Clear,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }
        }
    )

}
