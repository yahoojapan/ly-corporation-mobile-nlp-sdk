// SPDX-FileCopyrightText: 2025 LY Corporation
// SPDX-License-Identifier: BSD-3-Clause

import MobileNLPKitCore

public class OnlineMobileJlpHandler: MobileJlpHandler {
    let client: JlpApiClient

    public init(client: JlpApiClient) {
        self.client = client
    }

    public func analyze(
        method: JlpMethod,
        query: String,
        id: String,
        completionHandler: @escaping ((any JlpResponse)?, (any Error)?) -> Void
    ) {
        client.request(jlpMethod: method, query: query, id: id, completionHandler: completionHandler)
    }
}
