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
        target.compilations.getByName("main") {
            if (this is org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeCompilation) {
                println("Configuring target ${target.name}")
                cinterops {
                    create("GDNative") {
                        defFile("src/main/c_interop/godot.def")
                        includeDirs("../lib/godot_headers", "src/main/c_interop")
                    }
                }
            } else {
                System.err.println("Not a native target! TargetName: ${target.name}")
            }
        }
    }
}

/*
bintray {
    user = System.getenv('BINTRAY_USER')
    key = System.getenv('BINTRAY_KEY')
    publications = ['kotlinMultiplatform', 'metadata', 'windows', 'linux', 'macos']
    pkg {
        repo = 'kotlin-godot'
        name = 'godot-library'
        licenses = ['Apache-2.0']
        version {
            name = project.ext.version
            desc = "Godot library ${project.ext.version}"
            released = new Date()
            vcsTag = project.ext.version
        }
    }
}*/

tasks.build {
    finalizedBy(tasks.publishToMavenLocal)
}