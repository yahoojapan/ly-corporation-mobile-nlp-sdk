package jp.co.lycorp.jlp.testapp.android.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import jp.co.lycorp.jlp.mobilenlp.kit.JlpHandlerType
import jp.co.lycorp.jlp.mobilenlp.kit.JlpResponseParser
import jp.co.lycorp.jlp.mobilenlp.kit.MobileJlpHandler
import jp.co.lycorp.jlp.mobilenlp.kit.MobileJlpHandlerFactory
import jp.co.lycorp.jlp.mobilenlp.kit.model.JlpMethod
import jp.co.lycorp.jlp.testapp.AppSecrets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainScreenViewModel(private val jlpHandler: MobileJlpHandler, private val parser: JlpResponseParser) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras,
            ): T = MainScreenViewModel(
                jlpHandler = MobileJlpHandlerFactory.create(JlpHandlerType.Online(AppSecrets.APPID)),
                parser = JlpResponseParser(),
            ) as T
        }

        val TAG = MainScreenViewModel::class.simpleName
    }

    var uiState by mutableStateOf(MainScreenUiState(JlpMethod.entries, 0, "", JlpMethod.getSampleText(0) ?: "", "", ""))
        private set

    fun analyze() {
        val method = uiState.methods.getOrNull(uiState.methodIndex) ?: run {
            Log.w(TAG, "メソッドのindexが存在しません: ${uiState.methodIndex}")
            return
        }

        if (uiState.input.isEmpty()) {
            uiState = uiState.copy(input = uiState.sampleText)
        }

        viewModelScope.launch(Dispatchers.IO) {
            val response = jlpHandler.analyze(method, uiState.input)
            val (errorMsg, result) = parser.parseResponse(response)
            withContext(Dispatchers.Main) {
                uiState = uiState.copy(responseError = errorMsg ?: "", response = result)
            }
        }
    }

    fun selectMethod(itemIndex: Int) {
        uiState = uiState.copy(
            methodIndex = itemIndex,
            input = if (uiState.input == uiState.sampleText) "" else uiState.input,
            sampleText = JlpMethod.getSampleText(itemIndex) ?: "",
            response = "",
            responseError = "",
        )
    }

    fun onInputTextChanged(text: String) {
        uiState = uiState.copy(input = text)
    }
}
