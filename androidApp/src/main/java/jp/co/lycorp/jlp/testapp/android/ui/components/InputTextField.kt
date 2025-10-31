package jp.co.lycorp.jlp.testapp.android.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.co.lycorp.jlp.testapp.android.ui.JlpTestAppTheme

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    text: String,
    sampleText: String,
    onValueChanged: (text: String) -> Unit,
    onDone: () -> Unit,
) {
    Box {
        OutlinedTextField(
            modifier = modifier
                .requiredHeight(160.dp)
                .fillMaxWidth(),
            value = text,
            onValueChange = onValueChanged,
            placeholder = {
                Text(text = sampleText, color = Color(0xFF8E8E93))
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { onDone() }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                unfocusedBorderColor = Color.Black,
            ),
            trailingIcon = {
                if (text.isNotEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.TopCenter,
                    ) {
                        IconButton(
                            modifier = Modifier.padding(top = 4.dp),
                            onClick = { onValueChanged("") },
                        ) {
                            Icon(
                                Icons.Filled.Clear,
                                contentDescription = "テキスト入力の削除",
                                tint = Color.Black,
                            )
                        }
                    }
                }
            },
        )
    }
}

@Preview
@Composable
private fun InputTextFieldPreview() {
    JlpTestAppTheme {
        InputTextField(text = "六本木から東京ミッドタウンまで徒歩10分程度で着きます。", sampleText = "", onValueChanged = {}, onDone = {})
    }
}

@Preview
@Composable
private fun InputTextFieldSamplePreview() {
    JlpTestAppTheme {
        InputTextField(text = "", sampleText = "六本木から東京ミッドタウンまで徒歩10分程度で着きます。", onValueChanged = {}, onDone = {})
    }
}
