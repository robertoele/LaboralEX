package com.example.laboralex.ui.components

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.em

private const val id = "unId"
private val text = buildAnnotatedString {
    appendInlineContent(id, "[myBox]")
    append("Este campo es obligatorio")
}

val inlineContent =
    mapOf(
        Pair(
            id,
            InlineTextContent(
                placeholder = Placeholder(
                    width = 1.em,
                    height = 1.em,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                )
            ) {
                Icon(Icons.Default.Clear, contentDescription = null, tint = Color.Red)
            }
        )
    )

@Composable
fun TextRequiredField() {
    Text(text = text, inlineContent = inlineContent)
}