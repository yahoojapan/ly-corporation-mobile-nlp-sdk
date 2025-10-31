import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
}

abstract class GenerateSecretTask : DefaultTask() {
    private val packageName = "jp.co.lycorp.jlp.testapp"
    private val clsName = "AppSecrets"

    @get:InputFile
    abstract val secretsFile: RegularFileProperty

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    private fun Properties.toConstVals(): String {
        val indent = "|${" ".repeat(4)}"
        return this
            .map { (k, v) -> "const val ${k.toString().uppercase()}: String = \"${v}\"" }
            .joinToString(separator = "\n$indent", prefix = indent)
    }

    @TaskAction
    fun generate() {
        val secret = secretsFile.get().asFile
        if (!secret.exists()) {
            throw GradleException("'${secret.name}' file not found. Please place '${secret.name}' in the project root directory.")
        }

        val props = Properties().apply { secret.inputStream().use(::load) }
        val src = """
        |package $packageName
        |
        |object $clsName {
        ${props.toConstVals()}
        |}
        |
        """.trimMargin("|")

        val outputFile = outputDir.get().file("${packageName.replace(".", "/")}/$clsName.kt").asFile
        outputFile.parentFile.mkdirs()
        outputFile.writeText(src)
        logger.lifecycle("generated ${outputFile.name} at: ${outputFile.absolutePath}")
    }
}

val generateSecrets = tasks.register<GenerateSecretTask>("generateSecrets") {
    secretsFile.set(rootProject.file("secrets.properties"))
    outputDir.set(layout.buildDirectory.dir("generated/secrets"))
}

tasks.withType<KotlinCompile>().configureEach {
    dependsOn(generateSecrets)
}

android {
    namespace = "jp.co.lycorp.jlp.testapp.android"
    compileSdk = 35
    defaultConfig {
        applicationId = "jp.co.lycorp.jlp.testapp.android"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    sourceSets {
        getByName("main") {
            kotlin.srcDirs(generateSecrets.map { it.outputDir })
        }
    }
}

dependencies {
    implementation(project(":mobilenlp-kit"))

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.viewmodel)
}
