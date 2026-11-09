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
import jp.co.lycorp.jlp.mobilenlp.kit.JlpJsonMapper
import jp.co.lycorp.jlp.mobilenlp.kit.model.JlpMethod
import jp.co.lycorp.jlp.mobilenlp.kit.model.JlpResponse
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.concurrent.atomics.AtomicBoolean
import kotlin.concurrent.atomics.ExperimentalAtomicApi

@OptIn(ExperimentalAtomicApi::class)
class JlpApiRequestExecutor(
    private val scope: CoroutineScope,
    private val exec: suspend (jlpMethod: JlpMethod, query: String, id: String) -> JlpResponse,
) {
    private val isCompleted = AtomicBoolean(false)

    fun request(
        jlpMethod: JlpMethod,
        query: String,
        id: String,
        onResponse: (JlpResponse) -> Unit,
        onCancel: () -> Unit,
    ) {
        scope.launch {
            val response = exec(jlpMethod, query, id)
            complete { onResponse(response) }
        }.invokeOnCompletion { cause ->
            if (cause is CancellationException) {
                complete(onCancel)
            }
        }
    }

    private fun complete(callback: () -> Unit) {
        if (isCompleted.compareAndSet(expectedValue = false, newValue = true)) {
            callback()
        }
    }

    fun cancel() {
        scope.cancel()
    }
}

class JlpApiClient(private val client: HttpClient, private val mapper: JlpJsonMapper, private val appid: String) {
    companion object {
        private const val URL = "https://jlp.yahooapis.jp/"
    }

    constructor(appid: String) : this(HttpClient(), JlpJsonMapper(), appid)

    fun createExecutor(): JlpApiRequestExecutor = JlpApiRequestExecutor(scope = CoroutineScope(Dispatchers.IO), exec = ::request)

    suspend fun request(
        jlpMethod: JlpMethod,
        query: String,
        id: String,
    ): JlpResponse = kotlin.runCatching {
        requestInner(id = id, jlpMethod = jlpMethod, query = query)
    }.getOrElse {
        if (it is CancellationException) {
            throw it
        }
        JlpResponse.Error(errorMsg = it.message)
    }

    private suspend fun requestInner(id: String, jlpMethod: JlpMethod, query: String): JlpResponse {
        val requestBody = mapper.toRequest(id = id, methodName = jlpMethod.methodName, query = query)

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
            mapper.fromResponse(response.bodyAsText())
        } else {
            JlpResponse.HttpError(status = response.status.value, description = response.status.description)
        }
    }
}
