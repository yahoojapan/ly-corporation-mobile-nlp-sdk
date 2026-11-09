// swift-tools-version: 5.9

import PackageDescription

let package = Package(
    name: "MobileNLPKit",
    platforms: [
        .iOS(.v15),
    ],
    products: [
        .library(
            name: "MobileNLPKit",
            targets: ["MobileNLPKit"]
        ),
    ],
    targets: [
        .target(
            name: "MobileNLPKit",
            dependencies: ["MobileNLPKitCore"],
            path: "iosApp/MobileNLPKit/Sources/"
        ),
        .binaryTarget(
            name: "MobileNLPKitCore",
            url:
            "https://github.com/yahoojapan/ly-corporation-mobile-nlp-sdk/releases/download/1.0.0/MobileNLPKitCore.xcframework.zip",
            checksum: "79523fe37b789996ec16bab17a438e5da93113d42676d6b3641041508fcbc21e"
        ),
    ]
)
