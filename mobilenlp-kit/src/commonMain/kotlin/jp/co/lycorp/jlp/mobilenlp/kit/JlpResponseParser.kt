// SPDX-FileCopyrightText: 2025 LY Corporation
// SPDX-License-Identifier: BSD-3-Clause

package jp.co.lycorp.jlp.mobilenlp.kit

import jp.co.lycorp.jlp.mobilenlp.kit.network.model.JlpResponse
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

class JlpResponseParser {
    @OptIn(ExperimentalSerializationApi::class)
    private val prettyJson = Json {
        prettyPrint = true
        prettyPrintIndent = "  "
    }

    private inline fun <reified T : JlpResponse> T.toJsonPretty(): String = prettyJson.encodeToString(this)

    private fun String.toJsonPretty(): String {
        val jsonElement = Json.parseToJsonElement(this)
        return prettyJson.encodeToString(jsonElement)
    }

    private fun JlpResponse.Success.toJsonPretty(): String = this.jsonResponse.toJsonPretty()

    private fun JlpResponse.Failed.toJsonPretty(): String = this.errorJsonResponse.toJsonPretty()

    fun parseResponse(response: JlpResponse): Pair<String?, String> = when (response) {
        is JlpResponse.Success -> null to response.toJsonPretty()
        is JlpResponse.Failed -> "解析処理に問題が発生しました。" to response.toJsonPretty()
        is JlpResponse.HttpError -> "リクエスト処理中にエラーが発生しました。" to response.toJsonPretty()
        is JlpResponse.Error -> "リクエスト処理に失敗しました。" to response.toJsonPretty()
    }
}
