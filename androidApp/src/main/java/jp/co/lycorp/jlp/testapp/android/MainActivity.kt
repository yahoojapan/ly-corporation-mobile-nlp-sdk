package jp.co.lycorp.jlp.testapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import jp.co.lycorp.jlp.testapp.android.ui.JlpTestApp
import jp.co.lycorp.jlp.testapp.android.ui.JlpTestAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JlpTestAppTheme {
                JlpTestApp()
            }
        }
    }
}
