import Combine
import Foundation
import MobileNLPKit

@MainActor
class MainViewModel: ObservableObject {
    @Published var inputText: String = ""
    @Published var response: String = ""
    @Published var selectedMethod: String = JlpMethod.entries[0].displayName
    @Published var sampleText: String = ""
    @Published var errorMsg: String = ""
    let methodDisplayNames: [String] = JlpMethod.entries.map { method in method.displayName }

    private let parser = JlpResponseParser()
    private let jlpHandlerStore: JlpHandlerStoreProtocol
    private var cancellable = Set<AnyCancellable>()
    private var requestTask: Task<Void, Never>?
    private var latestRequestID: UUID?

    init(jlpHandlerStore: JlpHandlerStoreProtocol) {
        self.jlpHandlerStore = jlpHandlerStore
        $selectedMethod
            .sink { [weak self] method in
                guard let self else {
                    return
                }

                self.resetView()
                self.sampleText = JlpMethod.entries
                    .first { $0.displayName == method }?.sample ?? ""
            }
            .store(in: &cancellable)
    }

    deinit {
        requestTask?.cancel()
    }

    private func resetView() {
        response = ""
        errorMsg = ""
        clearInputIfSampleText()
    }

    private func clearInputIfSampleText() {
        if isSampleText(text: inputText) {
            inputText = ""
        }
    }

    private func isSampleText(text: String) -> Bool {
        return JlpMethod
            .entries
            .contains { entry in entry.sample == text }
    }

    func execute() {
        if inputText.isEmpty {
            inputText = sampleText
        }

        guard let method = toJlpMethod(displayName: selectedMethod) else {
            errorMsg = "Methodが未定義です。"
            response = "error"
            return
        }

        let requestID = UUID()
        let handler = jlpHandlerStore.getHandler()
        let query = inputText
        latestRequestID = requestID

        requestTask?.cancel()
        requestTask = Task(priority: .utility) { [weak self] in
            let response = await handler.analyze(method: method, query: query, id: "1234-1")
            guard let self, latestRequestID == requestID else {
                return
            }
            let result = self.parser.parseResponse(response: response)

            self.errorMsg = (result.first ?? "") as String
            self.response = (result.second ?? "") as String
        }
    }

    func toJlpMethod(displayName: String) -> JlpMethod? {
        return JlpMethod.entries.first { method in method.displayName == displayName }
    }
}
