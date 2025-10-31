// SPDX-FileCopyrightText: 2025 LY Corporation
// SPDX-License-Identifier: BSD-3-Clause

import MobileNLPKitCore

public enum JlpHandlerType {
    case online(appid: String)
}

public class MobileJlpHandlerFactory {
    public static func create(_ type: JlpHandlerType) -> MobileJlpHandler {
        switch type {
        case let .online(appid):
            return OnlineMobileJlpHandler(client: JlpApiClient(appid: appid))
        }
    }
}
