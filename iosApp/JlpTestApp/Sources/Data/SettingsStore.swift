import Foundation

class SettingsStore {
    static let shared = SettingsStore()
    private init() {}

    private let defaults = UserDefaults.standard
    private enum Keys {
        static let appId = "appId"
    }

    var appId: String {
        get { defaults.string(forKey: Keys.appId) ?? AppSecrets.APPID }
        set { defaults.set(newValue, forKey: Keys.appId) }
    }
}
