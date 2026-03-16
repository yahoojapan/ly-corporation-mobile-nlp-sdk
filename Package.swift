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
            path: "mobilenlp-kit/build/XCFrameworks/release/MobileNLPKitCore.xcframework"
        ),
    ]
)
