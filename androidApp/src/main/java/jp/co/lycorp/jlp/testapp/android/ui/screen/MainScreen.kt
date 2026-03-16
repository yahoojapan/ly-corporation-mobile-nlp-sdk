package jp.co.lycorp.jlp.testapp.android.ui.screen

import android.text.TextUtils
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.co.lycorp.jlp.mobilenlp.kit.model.JlpMethod
import jp.co.lycorp.jlp.testapp.android.R
import jp.co.lycorp.jlp.testapp.android.ui.JlpTestAppTheme
import jp.co.lycorp.jlp.testapp.android.ui.components.JlpRequestCard
import jp.co.lycorp.jlp.testapp.android.ui.components.JlpResponseCard
import jp.co.lycorp.jlp.testapp.android.ui.viewmodel.MainScreenUiState
import jp.co.lycorp.jlp.testapp.android.ui.viewmodel.MainScreenViewModel

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
    toSettingsScreen: () -> Unit,
) {
    MainScreen(
        uiState = viewModel.uiState,
        toSettingsScreen = toSettingsScreen,
        selectMethod = viewModel::selectMethod,
        onInputTextChange = viewModel::onInputTextChanged,
        analyze = viewModel::analyze,
    )
}

@Composable
internal fun MainScreen(
    uiState: MainScreenUiState,
    toSettingsScreen: () -> Unit,
    selectMethod: (index: Int) -> Unit,
    onInputTextChange: (input: String) -> Unit,
    analyze: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val doAnalyze = {
        keyboardController?.hide()
        analyze()
    }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(vertical = 16.dp, horizontal = 8.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
            ) {
                TitleAndSub(
                    modifier = Modifier
                        .align(Alignment.Center),
                )
                SettingsIconButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    onClick = toSettingsScreen,
                )
            }
            JlpRequestCard(
                methods = uiState.methods.map { method -> method.displayName },
                methodIndex = uiState.methodIndex,
                inputText = uiState.input,
                sampleText = uiState.sampleText,
                onClickMethod = selectMethod,
                onTextFieldChanged = onInputTextChange,
                onDoneInput = doAnalyze,
                doAnalyze = doAnalyze,
            )
            if (!TextUtils.isEmpty(uiState.response)) {
                JlpResponseCard(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .sizeIn(maxHeight = 520.dp),
                    errorMsg = uiState.responseError,
                    response = uiState.response,
                )
            }
            Text(
                text = stringResource(R.string.main_screen_copyright),
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 4.dp),
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                color = Color(0x6E000000),
            )
        }
    }
}

@Composable
private fun TitleAndSub(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(text = stringResource(R.string.main_screen_title), fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
        Text(text = stringResource(R.string.main_screen_sub_title), fontSize = 13.sp)
    }
}

@Composable
private fun SettingsIconButton(
    modifier: Modifier,
    onClick: () -> Unit,
) {
    IconButton(modifier = modifier.padding(end = 4.dp), onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "settings",
        )
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    JlpTestAppTheme {
        MainScreen(
            uiState = MainScreenUiState(
                methods = JlpMethod.entries,
                methodIndex = 0,
                input = "",
                sampleText = "",
                response = "",
                responseError = "",
            ),
            toSettingsScreen = {},
            selectMethod = {},
            onInputTextChange = {},
            analyze = {},
        )
    }
}

@Preview
@Composable
private fun MainScreenResponsePreview() {
    JlpTestAppTheme {
        MainScreen(
            uiState = MainScreenUiState(
                methods = JlpMethod.entries,
                methodIndex = 0,
                input = "",
                sampleText = "",
                response = "result\n".repeat(50),
                responseError = "",
            ),
            toSettingsScreen = {},
            selectMethod = {},
            onInputTextChange = {},
            analyze = {},
        )
    }
}

@Preview
@Composable
private fun MainScreenResponseErrorPreview() {
    JlpTestAppTheme {
        MainScreen(
            uiState = MainScreenUiState(
                methods = JlpMethod.entries,
                methodIndex = 0,
                input = "",
                sampleText = "",
                response = "error",
                responseError = "メソッドの指定が誤っています。",
            ),
            toSettingsScreen = {},
            selectMethod = {},
            onInputTextChange = {},
            analyze = {},
        )
    }
}
