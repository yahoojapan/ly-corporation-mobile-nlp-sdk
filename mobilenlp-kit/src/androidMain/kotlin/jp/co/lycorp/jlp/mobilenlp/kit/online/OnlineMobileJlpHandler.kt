// SPDX-FileCopyrightText: 2025 LY Corporation
// SPDX-License-Identifier: BSD-3-Clause

package jp.co.lycorp.jlp.mobilenlp.kit.online

import jp.co.lycorp.jlp.mobilenlp.kit.MobileJlpHandler
import jp.co.lycorp.jlp.mobilenlp.kit.model.JlpMethod
import jp.co.lycorp.jlp.mobilenlp.kit.network.JlpApiClient
import jp.co.lycorp.jlp.mobilenlp.kit.network.model.JlpResponse

class OnlineMobileJlpHandler(private val client: JlpApiClient) : MobileJlpHandler {
    override suspend fun analyze(jlpMethod: JlpMethod, query: String, id: String): JlpResponse = client.request(jlpMethod, query, id)
}
