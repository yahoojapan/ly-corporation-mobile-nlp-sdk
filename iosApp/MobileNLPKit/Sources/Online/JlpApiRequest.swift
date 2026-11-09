// SPDX-FileCopyrightText: 2026 LY Corporation
// SPDX-License-Identifier: BSD-3-Clause

import MobileNLPKitCore

public protocol JlpApiRequest {
    func fetch(method: JlpMethod,
               query: String,
               id: String,
               onResponse: @escaping (any JlpResponse) -> Void,
               onCancel: @escaping () -> Void)

    func cancel()
}

class JlpApiRequestImpl: JlpApiRequest {
    private let executor: JlpApiRequestExecutor

    init(_ executor: JlpApiRequestExecutor) {
        self.executor = executor
    }

    func fetch(method: JlpMethod,
               query: String,
               id: String,
               onResponse: @escaping (any JlpResponse) -> Void,
               onCancel: @escaping () -> Void) {
        executor.request(jlpMethod: method, query: query, id: id, onResponse: onResponse, onCancel: onCancel)
    }

    func cancel() {
        executor.cancel()
    }
}

public protocol JlpApiRequestFactory {
    func create() -> JlpApiRequest
}

extension JlpApiClient: JlpApiRequestFactory {
    public func create() -> any JlpApiRequest {
        JlpApiRequestImpl(createExecutor())
    }
}
