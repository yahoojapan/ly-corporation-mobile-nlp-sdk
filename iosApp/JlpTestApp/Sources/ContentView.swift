import SwiftUI

enum Route: Hashable {
    case settings
}

struct ContentView: View {
    @State private var path = NavigationPath()

    var body: some View {
        NavigationStack(path: $path) {
            MainScreen(onClickSettings: { path.append(Route.settings) })
                .navigationDestination(for: Route.self) { route in
                    switch route {
                    case .settings:
                        SettingsScreen()
                            .navigationTitle("設定")
                    }
                }
                .navigationTitle("")
        }
    }
}

#Preview {
    ContentView()
}
