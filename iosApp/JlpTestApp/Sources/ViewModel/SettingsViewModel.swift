import Combine
import Foundation
import SwiftUI

@MainActor
class SettingsViewModel: ObservableObject {
    @Published var savedAppId: String = ""
    @Published var editingAppId: String = ""
    private let settingsStore: SettingsStore
    private let jlpHandlerStore: JlpHandlerStoreProtocol

    var canSaveAppId: Bool {
        let newId = editingAppId.trimmingCharacters(in: .whitespacesAndNewlines)
        return !newId.isEmpty && newId != savedAppId
    }

    init(settingsStore: SettingsStore, jlpHandlerStore: JlpHandlerStoreProtocol) {
        self.settingsStore = settingsStore
        self.jlpHandlerStore = jlpHandlerStore
        load()
    }

    private func load() {
        savedAppId = settingsStore.appId
        editingAppId = settingsStore.appId
    }

    func onSaveAppId() {
        guard canSaveAppId else {
            return
        }
        let newId = editingAppId.trimmingCharacters(in: .whitespacesAndNewlines)
        settingsStore.appId = newId
        savedAppId = newId
        jlpHandlerStore.updateAppId()
    }
}
