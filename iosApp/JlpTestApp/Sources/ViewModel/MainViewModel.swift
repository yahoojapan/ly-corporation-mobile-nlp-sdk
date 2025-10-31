import Combine
import Foundation
import MobileNLPKit

class MainViewModel: ObservableObject {
    @Published var inputText: String = ""
    @Published var response: String = ""
    @Published var selectedMethod: String = JlpMethod.entries[0].displayName
    @Published var sampleText: String = ""
    @Published var errorMsg: String = ""
    let methodDisplayNames: [String] = JlpMethod.entries.map { method in method.displayName }

    private let jlpHandler: MobileJlpHandler
    private let parser = JlpResponseParser()
    private var cancellable = Set<AnyCancellable>()

    init(jlpHandler: MobileJlpHandler) {
        self.jlpHandler = jlpHandler
        $selectedMethod
            .handleEvents(receiveOutput: { _ in self.resetView() })
            .map { method in
                JlpMethod
                    .entries
                    .first { entry in entry.displayName == method }?.sample ?? ""
            }
            .assign(to: \.sampleText, on: self)
            .store(in: &cancellable)
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

        jlpHandler.analyze(method: method, query: inputText, id: "1234-1") { jlpResponse, _ in
            guard let response = jlpResponse else {
                return
            }
            let result = self.parser.parseResponse(response: response)

            DispatchQueue.main.async {
                self.errorMsg = (result.first ?? "") as String
                self.response = (result.second ?? "") as String
            }
        }
    }

    func toJlpMethod(displayName: String) -> JlpMethod? {
        return JlpMethod.entries.first { method in method.displayName == displayName }
    }
}
