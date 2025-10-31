package jp.co.lycorp.jlp.testapp.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.co.lycorp.jlp.testapp.android.R
import jp.co.lycorp.jlp.testapp.android.ui.JlpTestAppTheme

@Composable
fun JlpResponseCard(modifier: Modifier = Modifier, errorMsg: String, response: String) {
    Column(
        modifier = modifier
            .background(Color.White)
            .padding(vertical = 24.dp, horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        val isError by remember(errorMsg) {
            derivedStateOf { errorMsg.isNotEmpty() }
        }
        Text(text = stringResource(R.string.jlp_response_card_title), style = MaterialTheme.typography.titleSmall)
        if (isError) {
            Text(text = errorMsg, fontSize = 13.sp, color = MaterialTheme.colorScheme.error)
        }
        ScrollableText(modifier = Modifier.padding(horizontal = 16.dp), result = response, isError = isError)
    }
}

@Preview(showBackground = true)
@Composable
private fun JlpResponseCardPreview() {
    val response =
        """
        {
          "id": "1234-1",
          "jsonrpc": "2.0",
          "result": {
            "tokens": [
              [
                "美しい",
                "うつくしい",
                "美しい",
                "形容詞",
                "*",
                "イ形容詞イ段",
                "基本形"
              ],
        """.trimIndent()
    JlpTestAppTheme {
        JlpResponseCard(errorMsg = "", response = response)
    }
}

@Preview(showBackground = true)
@Composable
private fun JlpResponseCardErrorPreview() {
    val errorResponse =
        """
        {
          "id": "1234-1",
          "jsonrpc": "2.0",
          "error": {
            "code": -32601,
            "message": "Method not found"
          }
        }
        """.trimIndent()
    JlpTestAppTheme {
        JlpResponseCard(errorMsg = "解析エラーが起こりました。", response = errorResponse)
    }
}
