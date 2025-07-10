package com.example.laboralex.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.laboralex.R
import com.example.laboralex.viewmodel.CreateSkillViewModel

@Composable
fun CreateSkill(viewModel: CreateSkillViewModel, backNavigation: () -> Unit) {
    val skillField = viewModel.skillField.collectAsStateWithLifecycle()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.which_skill))
            TextField(value = skillField.value, onValueChange = viewModel::changeSkillField)
            Row {
                Button(
                    modifier = Modifier
                        .padding(3.dp),
                    onClick = backNavigation
                ) {
                    Text(stringResource(R.string.cancel))
                }
                Button(
                    modifier = Modifier
                        .padding(3.dp),
                    onClick = {
                        viewModel.addSkill()
                        viewModel.changeSkillField("")
                        backNavigation()
                    }
                ) {
                    Text(stringResource(R.string.add))
                }
            }
        }
    }
}