import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.vanniktechMavenPublish)
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_17)
                }
            }
        }
    }

    val xcf = XCFramework("MobileNLPKitCore")
    val minIosVersion = "15.0"
    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "MobileNLPKitCore"
            isStatic = true
            freeCompilerArgs += listOf(
                "-Xoverride-konan-properties=" +
                "osVersionMin.ios_arm64=$minIosVersion;" +
                "osVersionMin.ios_simulator_arm64=$minIosVersion"
            )

            xcf.add(this)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.kotlinx.serialization)
            implementation(libs.kotlinx.coroutines.core)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "jp.co.lycorp.jlp.mobilenlp.kit"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

version = providers.gradleProperty("version").orElse("0.0.0").get()
mavenPublishing {
    publishToMavenCentral(automaticRelease = true)

    signAllPublications()

    coordinates("jp.co.lycorp", "ly-corporation-mobile-nlp-sdk")

    pom {
        name = "LY Corporation Mobile NLP SDK"
        description = "LY Corporation Mobile NLP SDK is a multi-platform library for Android and iOS that provides NLP features for mobile apps."
        inceptionYear = "2025"
        url = "https://github.com/yahoojapan/ly-corporation-mobile-nlp-sdk"
        licenses {
            license {
                name = "BSD-3-Clause"
                url = "https://opensource.org/license/BSD-3-Clause"
                distribution = "repo"
            }
        }
        developers {
            developer {
                name = "LY Corporation"
                url = "https://www.lycorp.co.jp/"
            }
        }
        scm {
            url = "https://github.com/yahoojapan/ly-corporation-mobile-nlp-sdk"
            connection = "scm:git:git://github.com/yahoojapan/ly-corporation-mobile-nlp-sdk.git"
            developerConnection = "scm:git:ssh://github.com/yahoojapan/ly-corporation-mobile-nlp-sdk.git"
        }
    }
}
