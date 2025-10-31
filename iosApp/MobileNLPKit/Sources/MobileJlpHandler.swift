// SPDX-FileCopyrightText: 2025 LY Corporation
// SPDX-License-Identifier: BSD-3-Clause

@_exported import MobileNLPKitCore

public protocol MobileJlpHandler {
    func analyze(method: JlpMethod,
                 query: String,
                 id: String,
                 completionHandler: @escaping ((any JlpResponse)?, (any Error)?) -> Void)
}
