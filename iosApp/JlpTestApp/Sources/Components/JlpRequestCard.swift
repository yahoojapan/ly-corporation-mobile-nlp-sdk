import SwiftUI

struct JlpRequestCard: View {
    @Binding var selectedMethod: String
    @Binding var inputText: String
    var sampleText: String
    var isTextFieldFocused: FocusState<Bool>.Binding
    var methodDisplayNames: [String]
    let doAnalyze: () -> Void

    var body: some View {
        VStack(alignment: .leading, spacing: 24) {
            VStackBox(title: "利用するメソッド") {
                SelectMethodPicker(selectedMethod: $selectedMethod, methodDisplayNames: methodDisplayNames)
            }
            VStackBox(title: "入力テキスト") {
                InputTextField(inputText: $inputText, sampleText: sampleText, isTextFieldFocused: isTextFieldFocused)
            }
            Button("解析する", action: doAnalyze)
                .buttonStyle(RoundedButtonStyle())
        }
        .padding(EdgeInsets(top: 24, leading: 20, bottom: 24, trailing: 20))
        .background(.white)
    }
}

private struct VStackBox<Content: View>: View {
    var title: String
    @ViewBuilder let content: () -> Content

    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text(title).font(.system(size: 15, weight: .semibold))
            content()
        }
    }
}

private struct RoundedButtonStyle: ButtonStyle {
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .padding()
            .frame(maxWidth: .infinity)
            .foregroundColor(Color.Theme.onPrimary)
            .background(Color.Theme.primary)
            .cornerRadius(12)
    }
}

private struct JlpRequestCardPreview: View {
    @FocusState private var isFocused: Bool

    var body: some View {
        JlpRequestCard(
            selectedMethod: .constant("形態素解析"),
            inputText: .constant("入力テキスト"),
            sampleText: "",
            isTextFieldFocused: $isFocused,
            methodDisplayNames: ["形態素解析", "サンプル"],
            doAnalyze: {}
        )
    }
}

#Preview {
    JlpRequestCardPreview()
}
