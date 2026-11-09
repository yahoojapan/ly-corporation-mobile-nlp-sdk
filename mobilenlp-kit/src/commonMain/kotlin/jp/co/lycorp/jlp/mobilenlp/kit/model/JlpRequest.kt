// SPDX-FileCopyrightText: 2025 LY Corporation
// SPDX-License-Identifier: BSD-3-Clause

package jp.co.lycorp.jlp.mobilenlp.kit.model

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JlpRequestParams(val q: String)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class JlpRequest(
    val id: String,
    @SerialName("jsonrpc")
    @EncodeDefault(EncodeDefault.Mode.ALWAYS)
    val jsonRpc: String = "2.0",
    val method: String,
    val params: JlpRequestParams,
)
