package jp.co.lycorp.jlp.testapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import jp.co.lycorp.jlp.testapp.android.ui.JlpTestAppTheme
import jp.co.lycorp.jlp.testapp.android.ui.screen.MainScreen
import jp.co.lycorp.jlp.testapp.android.ui.viewmodel.MainScreenViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainScreenViewModel by viewModels { MainScreenViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JlpTestAppTheme {
                MainScreen(viewModel)
            }
        }
    }
}
