import SwiftUI

extension Color {
    enum Theme {
        static let primary = Color("AppPrimary")
        static let secondary = Color("AppSecondary")
        static let background = Color("AppBackground")
        static let error = Color("AppError")
        static let surfaceContainer = Color.white

        static let onPrimary = Color.white
        static let onSurface = Color.black
        static let onSurfaceVariant = Color.gray
        static let onSurfaceVariantLow = Color("AppOnSurfaceVariantLow")
    }
}
