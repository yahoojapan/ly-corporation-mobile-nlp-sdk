// SPDX-FileCopyrightText: 2025 LY Corporation
// SPDX-License-Identifier: BSD-3-Clause

package jp.co.lycorp.jlp.mobilenlp.kit.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface JlpResponse {
    @Serializable
    data class Success(val jsonResponse: String) : JlpResponse

    @Serializable
    data class Failed(val errorJsonResponse: String) : JlpResponse

    @Serializable
    data class HttpError(val status: Int, val description: String) : JlpResponse

    @Serializable
    data class Error(val errorMsg: String?) : JlpResponse
}
