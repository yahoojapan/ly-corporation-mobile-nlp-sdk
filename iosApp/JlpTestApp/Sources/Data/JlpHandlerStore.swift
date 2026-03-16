import MobileNLPKit

@MainActor
protocol JlpHandlerStoreProtocol {
    func getHandler() -> MobileJlpHandler

    func updateAppId()
}

@MainActor
class JlpHandlerStore: JlpHandlerStoreProtocol {
    static let shared = JlpHandlerStore()

    private var jlpHandler: MobileJlpHandler
    private let settingsStore: SettingsStore

    init(settingsStore: SettingsStore = .shared) {
        self.settingsStore = settingsStore
        jlpHandler = Self.createHandler(appId: settingsStore.appId)
    }

    func getHandler() -> MobileJlpHandler {
        jlpHandler
    }

    func updateAppId() {
        jlpHandler = Self.createHandler(appId: settingsStore.appId)
    }

    private static func createHandler(appId: String) -> MobileJlpHandler {
        return MobileJlpHandlerFactory.create(JlpHandlerType.online(appid: appId))
    }
}
