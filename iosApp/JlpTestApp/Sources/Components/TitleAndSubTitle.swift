import SwiftUI

struct TitleAndSubTitle: View {
    let title: String
    let subTitle: String
    var body: some View {
        VStack(alignment: .center) {
            Text(title).font(.system(size: 16, weight: .semibold))
                .foregroundStyle(Color.Theme.onSurface)
            Text(subTitle).font(.system(size: 13))
                .foregroundStyle(Color.Theme.onSurfaceVariant)
        }
    }
}

#Preview {
    TitleAndSubTitle(title: "タイトル", subTitle: "サブタイトル")
}
