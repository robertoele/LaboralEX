package com.example.laboralex.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
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
    placeHolder: @Composable (() -> Unit)? = null,
    errorIcon: @Composable (() -> Unit) = {
        Icon(
            Icons.Default.Info,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error
        )
    },
    interactionSource: MutableInteractionSource? = null,
    isError: Boolean = false,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    supportingText: String = ""
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        interactionSource = interactionSource,
        label = label,
        placeholder = placeHolder,
        trailingIcon = {
            if (isError) errorIcon()
            else if (value.isNotEmpty()) {
                IconButton(onClick = onClearPressed) {
                    Icon(
                        Icons.Rounded.Clear,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }
        },
        isError = isError,
        supportingText = {
            if (isError) Text(supportingText)
        },
        colors = colors
    )

}
