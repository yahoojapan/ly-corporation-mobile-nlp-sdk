package jp.co.lycorp.jlp.testapp.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.co.lycorp.jlp.testapp.android.R
import jp.co.lycorp.jlp.testapp.android.ui.JlpTestAppTheme

@Composable
fun JlpRequestCard(
    methods: List<String>,
    methodIndex: Int,
    inputText: String,
    sampleText: String,
    onClickMethod: (Int) -> Unit,
    onTextFieldChanged: (String) -> Unit,
    onDoneInput: () -> Unit,
    doAnalyze: () -> Unit,
) {
    Column(
        Modifier
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(25.dp),
    ) {
        ColumnBox(stringResource(R.string.jlp_request_card_method_title)) {
            DropdownMenuBox(methods, methodIndex, onClickItem = onClickMethod)
        }
        ColumnBox(stringResource(R.string.jlp_request_card_input_title)) {
            InputTextField(text = inputText, sampleText = sampleText, onValueChanged = onTextFieldChanged, onDone = onDoneInput)
        }
        Button(
            onClick = doAnalyze,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(id = R.string.jlp_request_card_analyze_button))
        }
    }
}

@Composable
private fun ColumnBox(
    title: String,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(text = title, style = MaterialTheme.typography.titleSmall)
        content()
    }
}

@Composable
@Preview(showBackground = true)
private fun JlpRequestCardPreview() {
    JlpTestAppTheme {
        JlpRequestCard(
            listOf("日本語形態素解析"),
            methodIndex = 0,
            inputText = "こんにちは",
            sampleText = "",
            onClickMethod = {},
            onTextFieldChanged = {},
            onDoneInput = {},
            doAnalyze = {},
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun JlpRequestCardSamplePreview() {
    JlpTestAppTheme {
        JlpRequestCard(
            listOf("日本語形態素解析"),
            methodIndex = 0,
            inputText = "",
            sampleText = "サンプルテキスト",
            onClickMethod = {},
            onTextFieldChanged = {},
            onDoneInput = {},
            doAnalyze = {},
        )
    }
}
