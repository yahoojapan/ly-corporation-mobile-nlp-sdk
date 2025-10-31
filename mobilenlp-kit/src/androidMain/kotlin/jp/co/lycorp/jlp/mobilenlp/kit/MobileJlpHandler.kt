// SPDX-FileCopyrightText: 2025 LY Corporation
// SPDX-License-Identifier: BSD-3-Clause

package jp.co.lycorp.jlp.mobilenlp.kit

import jp.co.lycorp.jlp.mobilenlp.kit.model.JlpMethod
import jp.co.lycorp.jlp.mobilenlp.kit.network.model.JlpResponse

interface MobileJlpHandler {
    suspend fun analyze(jlpMethod: JlpMethod, query: String, id: String = "1234-1"): JlpResponse
}
