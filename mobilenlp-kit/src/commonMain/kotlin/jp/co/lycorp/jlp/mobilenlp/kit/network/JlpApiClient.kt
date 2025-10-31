// SPDX-FileCopyrightText: 2025 LY Corporation
// SPDX-License-Identifier: BSD-3-Clause

package jp.co.lycorp.jlp.mobilenlp.kit.network

import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import jp.co.lycorp.jlp.mobilenlp.kit.model.JlpMethod
import jp.co.lycorp.jlp.mobilenlp.kit.network.model.JlpRequest
import jp.co.lycorp.jlp.mobilenlp.kit.network.model.JlpRequestParams
import jp.co.lycorp.jlp.mobilenlp.kit.network.model.JlpResponse
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

class JlpApiClient(private val client: HttpClient, private val appid: String) {
    companion object {
        private const val URL = "https://jlp.yahooapis.jp/"
    }

    constructor(appid: String) : this(HttpClient(), appid)

    private inline fun <reified T> T.toJson(): String = Json.encodeToString(this)

    suspend fun request(
        jlpMethod: JlpMethod,
        query: String,
        id: String,
    ): JlpResponse = kotlin
        .runCatching {
            requestInner(id = id, jlpMethod = jlpMethod, query = query)
        }.getOrElse {
            JlpResponse.Error(errorMsg = it.message)
        }

    private suspend fun requestInner(id: String, jlpMethod: JlpMethod, query: String): JlpResponse {
        val requestBody = JlpRequest(id = id, method = jlpMethod.methodName, params = JlpRequestParams(q = query)).toJson()

        val response = client.post(URL) {
            contentType(ContentType.Application.Json)
            headers {
                append(HttpHeaders.UserAgent, "Yahoo AppID: $appid")
            }
            url {
                appendPathSegments(jlpMethod.path)
            }
            setBody(requestBody)
        }

        return if (response.status.isSuccess()) {
            parseResponse(response.bodyAsText())
        } else {
            JlpResponse.HttpError(status = response.status.value, description = response.status.description)
        }
    }

    private fun parseResponse(jsonString: String): JlpResponse {
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
