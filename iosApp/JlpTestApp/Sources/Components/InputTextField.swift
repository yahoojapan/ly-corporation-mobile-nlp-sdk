import SwiftUI

struct InputTextField: View {
    @Binding var inputText: String
    var sampleText: String
    var isTextFieldFocused: FocusState<Bool>.Binding

    var body: some View {
        TextEditor(text: $inputText)
            .padding([.top, .bottom], 4)
            .padding(.trailing, 24)
            .frame(maxWidth: .infinity, minHeight: 132, maxHeight: 132)
            .foregroundStyle(Color.Theme.onSurface)
            .background(Color.Theme.surfaceContainer)
            .scrollContentBackground(Visibility.hidden)
            .multilineTextAlignment(.leading)
            .focused(isTextFieldFocused.projectedValue, equals: true)
            .overlay(
                RoundedRectangle(cornerRadius: 4)
                    .stroke(Color.Theme.onSurfaceVariant, lineWidth: 1)
            )
            .overlay(alignment: .topLeading) {
                if inputText.isEmpty {
                    Text(sampleText)
                        .foregroundStyle(Color.Theme.onSurfaceVariantLow)
                        .padding(EdgeInsets(top: 11, leading: 6, bottom: 8, trailing: 24))
                }
            }
            .overlay(alignment: .topTrailing) {
                if !inputText.isEmpty {
                    ClearButton(onClick: { inputText = "" })
                        .padding(.top, 13)
                        .padding(.trailing, 8)
                }
            }
    }
}

private struct ClearButton: View {
    let onClick: () -> Void
    var body: some View {
        Button(action: onClick) {
            Label("clear input", systemImage: "xmark.circle")
                .labelStyle(.iconOnly)
                .foregroundStyle(.gray)
        }
    }
}

private struct InputTextFieldShowSamplePreview: View {
    @FocusState private var isFocused: Bool

    var body: some View {
        InputTextField(
            inputText: .constant(""),
            sampleText: "東京から新大阪まで向かいたいのですがどのように行けば良いでしょうか？",
            isTextFieldFocused: $isFocused
        )
    }
}

private struct InputTextFieldShowInputTextPreview: View {
    @FocusState private var isFocused: Bool

    var body: some View {
        InputTextField(
            inputText: .constant("東京から新大阪まで向かいたいのですがどのように行けば良いでしょうか？"),
            sampleText: "六本木から東京ミッドタウンまで徒歩10分程度で着きます。",
            isTextFieldFocused: $isFocused
        )
    }
}

#Preview {
    Group {
        InputTextFieldShowSamplePreview()
        InputTextFieldShowInputTextPreview()
    }
}
