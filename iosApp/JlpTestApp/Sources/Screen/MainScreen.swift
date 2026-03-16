import MobileNLPKit
import SwiftUI

struct MainScreen: View {
    let onClickSettings: () -> Void

    @StateObject var viewModel = MainViewModel(jlpHandlerStore: JlpHandlerStore.shared)
    @FocusState var isTextFieldFocused: Bool

    var body: some View {
        ScrollView {
            VStack(alignment: .center) {
                ZStack(alignment: .trailing) {
                    TitleAndSubTitle(title: "テキスト解析サンプルアプリ", subTitle: "開発者向け動作確認ツール")
                        .frame(maxWidth: .infinity)

                    Button(action: onClickSettings) {
                        Image(systemName: "gearshape.fill")
                            .font(.system(size: 20, weight: .regular))
                            .foregroundColor(.primary)
                            .padding(10)
                            .padding(.trailing, 16)
                    }
                }
                JlpRequestCard(
                    selectedMethod: $viewModel.selectedMethod,
                    inputText: $viewModel.inputText,
                    sampleText: viewModel.sampleText,
                    isTextFieldFocused: $isTextFieldFocused,
                    methodDisplayNames: viewModel.methodDisplayNames,
                    doAnalyze: viewModel.execute
                )
                if viewModel.response != "" {
                    JlpResponseCard(response: viewModel.response, errorMsg: viewModel.errorMsg)
                        .frame(maxHeight: 510)
                }
                CopyrightText().padding(.top, 4)
                Spacer()
            }.padding([.leading, .trailing], 8)
        }
        .onTapGesture { isTextFieldFocused = false }
        .frame(maxHeight: .infinity)
        .background(Color.Theme.background)
    }
}

private struct CopyrightText: View {
    var body: some View {
        Text("© LY Corporation")
            .font(.system(size: 11, weight: .medium))
            .foregroundColor(Color.Theme.onSurfaceVariant)
            .frame(maxWidth: .infinity, alignment: .leading)
    }
}

#Preview {
    MainScreen(onClickSettings: {})
}
