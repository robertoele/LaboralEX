package com.example.laboralex.ui.components

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> ChipFlowRow(chipsList: List<T>, onSelected: (T) -> Unit) {
    FlowRow {
        chipsList.forEach {
            SuggestionChip(
                modifier = Modifier.padding(5.dp),
                onClick = { onSelected(it) },
                label = { Text(it.toString()) }
            )
        }
    }
}