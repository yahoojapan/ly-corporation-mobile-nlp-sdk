// SPDX-FileCopyrightText: 2025 LY Corporation
// SPDX-License-Identifier: BSD-3-Clause

import MobileNLPKitCore

public class OnlineMobileJlpHandler: MobileJlpHandler {
    let requestFactory: JlpApiRequestFactory
    private static let CANCEL_RESPONSE = JlpResponseError(errorMsg: "リクエスト処理がキャンセルされました")

    public init(requestFactory: JlpApiRequestFactory) {
        self.requestFactory = requestFactory
    }

    public func analyze(method: JlpMethod, query: String, id: String) async -> (any JlpResponse) {
        let request = requestFactory.create()

        return await withTaskCancellationHandler {
            await withCheckedContinuation { cont in
                request.fetch(method: method,
                               query: query,
                               id: id,
                               onResponse: { cont.resume(returning: $0) },
                               onCancel: { cont.resume(returning: OnlineMobileJlpHandler.CANCEL_RESPONSE) })
            }
        } onCancel: { request.cancel() }
    }
}
