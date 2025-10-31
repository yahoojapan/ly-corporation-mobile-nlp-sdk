import SwiftUI

struct JlpResponseCard: View {
    let response: String
    let errorMsg: String

    var body: some View {
        VStack(spacing: 12) {
            Text("解析結果 (JSON)")
                .font(.system(size: 15))
                .frame(maxWidth: .infinity, alignment: .leading)

            if !errorMsg.isEmpty {
                Text(errorMsg)
                    .font(.system(size: 13))
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .foregroundStyle(Color.Theme.error)
            }

            ScrollView {
                Text(response)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .foregroundStyle(errorMsg.isEmpty ? Color.Theme.onSurface : Color.Theme.error)
            }
            .background(Color.Theme.background)
            .padding([.leading, .trailing], 12)
        }
        .padding(EdgeInsets(top: 24, leading: 20, bottom: 24, trailing: 20))
        .background(Color.Theme.surfaceContainer)
    }
}

#Preview {
    let response = """
       {
         "id": "1234-1",
         "jsonrpc": "2.0",
         "result": {
           "tokens": [
             [
               "美しい",
               "うつくしい",
               "美しい",
               "形容詞",
               "*",
               "イ形容詞イ段",
               "基本形"
             ],
    """
    JlpResponseCard(response: response, errorMsg: "")
}

#Preview("Error") {
    let response = """
      {
         "id": "1234-1",
         "jsonrpc": "2.0",
         "error": {
           "code": -32601,
           "message": "Method not found"
         }
       }
    """
    JlpResponseCard(response: response, errorMsg: "解析エラーが起こりました。")
}
