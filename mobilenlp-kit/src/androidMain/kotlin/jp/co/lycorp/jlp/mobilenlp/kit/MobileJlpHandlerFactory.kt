// SPDX-FileCopyrightText: 2025 LY Corporation
// SPDX-License-Identifier: BSD-3-Clause

package jp.co.lycorp.jlp.mobilenlp.kit

import jp.co.lycorp.jlp.mobilenlp.kit.network.JlpApiClient
import jp.co.lycorp.jlp.mobilenlp.kit.online.OnlineMobileJlpHandler

sealed interface JlpHandlerType {
    data class Online(val appid: String) : JlpHandlerType
}

object MobileJlpHandlerFactory {
    fun create(type: JlpHandlerType): MobileJlpHandler = when (type) {
        is JlpHandlerType.Online -> OnlineMobileJlpHandler(JlpApiClient(type.appid))
    }
}
