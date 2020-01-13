plugins {
    id("kotlin-multiplatform")
    id("maven-publish")
}

kotlin {
    sourceSets {
        sourceSets.create("macosMain")
        sourceSets.create("linuxMain")
        sourceSets.create("windowsMain")
        configure(listOf(sourceSets["macosMain"], sourceSets["linuxMain"], sourceSets["windowsMain"])) {
            this.kotlin.srcDir("src/main/kotlin")
        }
    }

    val targets = listOf(
            targetFromPreset(presets["mingwX64"], "windows"),
            targetFromPreset(presets["linuxX64"], "linux"),
            targetFromPreset(presets["macosX64"], "macos")
    )

    targets.forEach { target ->
        target.compilations.all {
            dependencies {
                implementation(project(":wrapper:godot-library"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.3.3")
            }
        }
    }
}

tasks.build {
    finalizedBy(tasks.publishToMavenLocal)
}