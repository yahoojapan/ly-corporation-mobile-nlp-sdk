@testable import JlpTestApp
import MobileNLPKit
import XCTest

class SpyMobileJlpHandler: MobileJlpHandler {
    var jlpResponse: JlpResponse = JlpResponseSuccess(jsonResponse: "{\"key\":\"value\"}")
    var error: Error?
    var method: JlpMethod?
    var query: String = ""

    func analyze(
        method: JlpMethod,
        query: String,
        id _: String,
        completionHandler: @escaping ((any JlpResponse)?, (any Error)?) -> Void
    ) {
        self.method = method
        self.query = query
        completionHandler(jlpResponse, error)
    }
}

class SpyJlpHandlerStore: JlpHandlerStoreProtocol {
    var jlpHandler: SpyMobileJlpHandler = .init()

    func getHandler() -> any MobileNLPKit.MobileJlpHandler {
        return jlpHandler
    }

    func updateAppId() {}
}

@MainActor
final class MainViewModelTests: XCTestCase {
    private var store: SpyJlpHandlerStore!
    private var subject: MainViewModel!

    override func setUp() {
        store = SpyJlpHandlerStore()
        subject = MainViewModel(jlpHandlerStore: store)
    }

    func test_指定したメソッドと入力文字列をリクエストする() {
        // setup
        subject.selectedMethod = JlpMethod.entries[1].displayName
        subject.inputText = "inputText"

        // exec
        subject.execute()

        // verify
        XCTAssertEqual(JlpMethod.entries[1], store.jlpHandler.method)
        XCTAssertEqual("inputText", store.jlpHandler.query)
    }

    func test_入力文字列が空文字の場合_サンプル文字列をリクエストする() {
        // setup
        let targetMethod = JlpMethod.entries[1]
        subject.selectedMethod = targetMethod.displayName
        subject.inputText = ""

        // exec
        subject.execute()

        // verify
        XCTAssertEqual(targetMethod.sample, store.jlpHandler.query)
    }

    func test_リクエストの成功時にApiのレスポンスを表示する() {
        // setup
        let expectation = self.expectation(description: "wait for main queue")
        let expected = """
        {
          "key": "value"
        }
        """
        store.jlpHandler.jlpResponse = JlpResponseSuccess(jsonResponse: "{\"key\":\"value\"}")

        // exec
        subject.execute()
        DispatchQueue.main.async {
            expectation.fulfill()
        }

        wait(for: [expectation], timeout: 1)

        // verify
        XCTAssertEqual("", subject.errorMsg)
        XCTAssertEqual(expected, subject.response)
    }

    func test_リクエストの失敗時にエラーメッセージとエラー内容を表示する() {
        // setup
        let expectation = self.expectation(description: "wait for main queue")
        let expected = """
        {
          "status": 500,
          "description": "Internal Error"
        }
        """
        store.jlpHandler.jlpResponse = JlpResponseHttpError(status: 500, description: "Internal Error")

        // exec
        subject.execute()
        DispatchQueue.main.async {
            expectation.fulfill()
        }

        wait(for: [expectation], timeout: 1)

        // verify
        XCTAssertEqual("リクエスト処理中にエラーが発生しました。", subject.errorMsg)
        XCTAssertEqual(expected, subject.response)
    }

    func test_表示をリセットする() {
        // setup
        subject.errorMsg = "error"
        subject.response = "response"

        // exec
        subject.selectedMethod = JlpMethod.entries[1].displayName

        // verify
        XCTAssertEqual("", subject.errorMsg)
        XCTAssertEqual("", subject.response)
    }

    func test_表示するサンプルの文字列を切り替える() {
        // setup
        let targetMethod = JlpMethod.entries[1]

        // exec
        subject.selectedMethod = targetMethod.displayName

        // verify
        XCTAssertEqual(targetMethod.sample, subject.sampleText)
    }

    func test_入力文字列がサンプル文字列の場合_入力文字列をリセットする() {
        // setup
        let targetMethod = JlpMethod.entries[1]
        subject.selectedMethod = targetMethod.displayName
        subject.inputText = targetMethod.sample

        // exec
        subject.selectedMethod = targetMethod.displayName

        // verify
        XCTAssertEqual("", subject.inputText)
    }

    func test_入力文字列がサンプル文字列以外の場合_入力文字列をそのままにする() {
        // setup
        let targetMethod = JlpMethod.entries[1]
        subject.selectedMethod = targetMethod.displayName
        subject.inputText = "inputText"

        // exec
        subject.selectedMethod = targetMethod.displayName

        // verify
        XCTAssertEqual("inputText", subject.inputText)
    }
}
