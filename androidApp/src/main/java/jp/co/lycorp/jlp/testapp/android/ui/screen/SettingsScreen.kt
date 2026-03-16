package jp.co.lycorp.jlp.testapp.android.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.co.lycorp.jlp.testapp.android.R
import jp.co.lycorp.jlp.testapp.android.ui.JlpTestAppTheme
import jp.co.lycorp.jlp.testapp.android.ui.viewmodel.SettingsScreenUiState
import jp.co.lycorp.jlp.testapp.android.ui.viewmodel.SettingsScreenViewModel

@Composable
internal fun SettingsScreen(
    viewModel: SettingsScreenViewModel,
    onClickBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingsScreen(
        uiState = uiState,
        onEditValueChange = viewModel::onEditValueChanged,
        onSaveClick = viewModel::onSaveAppId,
        onClickBack = onClickBack,
    )
}

@Composable
internal fun SettingsScreen(
    uiState: SettingsScreenUiState,
    onClickBack: () -> Unit,
    onEditValueChange: (String) -> Unit,
    onSaveClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            SettingsScreenTopAppBar(onClickBack = onClickBack)
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(vertical = 16.dp, horizontal = 8.dp)
                .fillMaxWidth(),
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                ) {
                    Text(stringResource(R.string.settings_screen_app_id), style = MaterialTheme.typography.titleMedium)

                    Spacer(Modifier.height(8.dp))

                    AppIdInputField(
                        editingAppId = uiState.editingAppId,
                        isSaveEnabled = uiState.isSaveEnabled,
                        onEditValueChange = onEditValueChange,
                        onSaveClick = onSaveClick,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsScreenTopAppBar(onClickBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.settings_screen_title),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        navigationIcon = {
            IconButton(onClick = onClickBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    )
}

@Composable
private fun AppIdInputField(
    editingAppId: String,
    isSaveEnabled: Boolean,
    onEditValueChange: (String) -> Unit,
    onSaveClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            value = editingAppId,
            onValueChange = onEditValueChange,
            modifier = Modifier
                .weight(1f),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            placeholder = { Text(stringResource(R.string.settings_screen_app_id_example)) },
        )

        Spacer(Modifier.width(8.dp))

        Button(
            onClick = onSaveClick,
            enabled = isSaveEnabled,
        ) {
            Text(stringResource(R.string.settings_screen_save))
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    JlpTestAppTheme {
        SettingsScreen(
            uiState = SettingsScreenUiState(
                savedAppId = "",
                editingAppId = "",
                isSaveEnabled = true,
            ),
            {},
            {},
            {},
        )
    }
}

@Preview
@Composable
private fun SettingsScreenPreviewWithAppId() {
    JlpTestAppTheme {
        SettingsScreen(
            uiState = SettingsScreenUiState(
                savedAppId = "",
                editingAppId = "sample",
                isSaveEnabled = true,
            ),
            {},
            {},
            {},
        )
    }
}
