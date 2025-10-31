import SwiftUI

struct SelectMethodPicker: View {
    @Binding var selectedMethod: String
    var methodDisplayNames: [String]

    var body: some View {
        Picker("", selection: $selectedMethod) {
            ForEach(methodDisplayNames, id: \.self) { displayName in
                Text(displayName)
            }
        }
        .labelsHidden()
        .frame(maxWidth: .infinity)
        .overlay(
            RoundedRectangle(cornerRadius: 4)
                .stroke(Color.Theme.onSurfaceVariant, lineWidth: 1)
        )
    }
}

#Preview {
    SelectMethodPicker(
        selectedMethod: .constant("日本語形態素解析"),
        methodDisplayNames: ["日本語形態素解析", "サンプル"]
    )
}
