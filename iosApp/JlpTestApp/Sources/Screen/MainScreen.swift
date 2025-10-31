import MobileNLPKit
import SwiftUI

struct MainScreen: View {
    @StateObject var viewModel = MainViewModel(
        jlpHandler: MobileJlpHandlerFactory.create(JlpHandlerType.online(appid: AppSecrets.APPID))
    )
    @FocusState var isTextFieldFocused: Bool

    var body: some View {
        ScrollView {
            VStack(alignment: .center) {
                TitleAndSubTitle()
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

private struct TitleAndSubTitle: View {
    var body: some View {
        VStack(alignment: .center) {
            Text("テキスト解析サンプルアプリ").font(.system(size: 16, weight: .semibold))
                .foregroundStyle(Color.Theme.onSurface)
            Text("開発者向け動作確認ツール").font(.system(size: 13))
                .foregroundStyle(Color.Theme.onSurfaceVariant)
        }.padding(.bottom, 16)
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
    MainScreen()
}
