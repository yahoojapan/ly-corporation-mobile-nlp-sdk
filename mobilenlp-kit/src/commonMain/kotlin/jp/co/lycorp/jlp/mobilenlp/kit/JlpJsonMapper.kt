// SPDX-FileCopyrightText: 2026 LY Corporation
// SPDX-License-Identifier: BSD-3-Clause

package jp.co.lycorp.jlp.mobilenlp.kit
import jp.co.lycorp.jlp.mobilenlp.kit.model.JlpRequest
import jp.co.lycorp.jlp.mobilenlp.kit.model.JlpRequestParams
import jp.co.lycorp.jlp.mobilenlp.kit.model.JlpResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

class JlpJsonMapper {
    private inline fun <reified T> T.toJson(): String = Json.encodeToString(this)

    fun toRequest(id: String, methodName: String, query: String): String =
        JlpRequest(id = id, method = methodName, params = JlpRequestParams(q = query)).toJson()

    fun fromResponse(jsonString: String): JlpResponse {
        val element = Json.parseToJsonElement(jsonString)
        val response = if (isErrorResponse(element)) {
            JlpResponse::Failed
        } else {
            JlpResponse::Success
        }

        return response(jsonString)
    }

    private fun isErrorResponse(element: JsonElement) = element !is JsonObject || element.containsKey("error")
}
