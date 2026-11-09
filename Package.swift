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
            "https://github.com/yahoojapan/ly-corporation-mobile-nlp-sdk/releases/download/1.2.0/MobileNLPKitCore.xcframework.zip",
            checksum: "d5a990860cfbb4f9b757026e1132e0ce82015d14676615c9981750323786b079"
        ),
    ]
)
