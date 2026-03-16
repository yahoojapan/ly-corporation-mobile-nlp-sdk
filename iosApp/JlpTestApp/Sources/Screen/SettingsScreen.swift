import SwiftUI

struct SettingsScreen: View {
    @StateObject var viewModel = SettingsViewModel(
        settingsStore: .shared, jlpHandlerStore: JlpHandlerStore.shared
    )

    var body: some View {
        VStack(alignment: .center, spacing: 16) {
            VStack(alignment: .leading, spacing: 8) {
                Text("AppId")
                    .font(.system(size: 16))
                    .padding()

                HStack {
                    SecureField("例: your-app-id", text: $viewModel.editingAppId)
                        .textFieldStyle(.roundedBorder)
                        .textInputAutocapitalization(.never)
                        .autocorrectionDisabled()

                    Button("保存") {
                        viewModel.onSaveAppId()
                    }
                    .buttonStyle(.borderedProminent)
                    .disabled(!viewModel.canSaveAppId)
                }
                .padding(.bottom, 16)
                .padding(.horizontal, 16)
            }
            .background(
                RoundedRectangle(cornerRadius: 8)
                    .fill(Color(.systemBackground))
            )

            Spacer()
        }
        .padding([.leading, .trailing], 8)
        .frame(maxHeight: .infinity)
        .background(Color.Theme.background)
    }
}

// preview
#Preview {
    SettingsScreen()
}
