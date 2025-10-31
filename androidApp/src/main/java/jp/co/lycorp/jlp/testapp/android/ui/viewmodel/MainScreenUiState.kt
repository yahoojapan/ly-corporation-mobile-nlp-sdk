package jp.co.lycorp.jlp.testapp.android.ui.viewmodel

import jp.co.lycorp.jlp.mobilenlp.kit.model.JlpMethod

data class MainScreenUiState(
    val methods: List<JlpMethod>,
    val methodIndex: Int,
    val input: String,
    val sampleText: String,
    val response: String,
    val responseError: String,
)
