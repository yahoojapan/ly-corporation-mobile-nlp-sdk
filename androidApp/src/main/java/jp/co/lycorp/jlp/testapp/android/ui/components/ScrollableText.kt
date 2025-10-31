package jp.co.lycorp.jlp.testapp.android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import jp.co.lycorp.jlp.testapp.android.ui.JlpTestAppTheme

@Composable
fun ScrollableText(modifier: Modifier = Modifier, result: String, isError: Boolean = false) {
    val scroll = rememberScrollState(0)

    val disableToTopScroll = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource,
            ): Offset = if (available.y > 0f && scroll.value == 0) {
                Offset(0f, available.y)
            } else {
                Offset.Zero
            }

            override suspend fun onPostFling(
                consumed: Velocity,
                available: Velocity,
            ): Velocity = if (available.y > 0f && scroll.value == 0) {
                Velocity(x = 0f, y = available.y)
            } else {
                Velocity.Zero
            }
        }
    }

    Text(
        text = result,
        modifier = modifier
            .fillMaxWidth()
            .nestedScroll(disableToTopScroll)
            .verticalScroll(scroll)
            .background(MaterialTheme.colorScheme.background),
        color = if (isError) MaterialTheme.colorScheme.error else Color.Unspecified,
    )
}

@Preview(showBackground = true)
@Composable
private fun ScrollableTextPreview() {
    JlpTestAppTheme {
        ScrollableText(result = "テスト\n".repeat(20), modifier = Modifier.fillMaxWidth())
    }
}

@Preview(showBackground = true)
@Composable
private fun ScrollableTextErrorPreview() {
    JlpTestAppTheme {
        ScrollableText(result = "エラー\n".repeat(10), modifier = Modifier.fillMaxWidth(), isError = true)
    }
}
