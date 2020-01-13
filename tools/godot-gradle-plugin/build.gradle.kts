buildscript {
    repositories {
        mavenLocal()
        jcenter()
    }
    dependencies {
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.+")
    }
}

plugins {
    id("java-gradle-plugin")
    id("maven-publish")
    id("org.jetbrains.kotlin.jvm")
    id("com.jfrog.bintray")
}

group = "org.godotengine.kotlin"
version = "1.0.1"

gradlePlugin {
    plugins {
        create("godotGradlePlugin") {
            id = "godot-gradle-plugin"
            implementationClass = "godot.gradle.plugin.KotlinGodotPlugin"
        }
    }
}

//TODO: these are overrides because somehow from somewhere the versions 1.3.30 are used which dont work in gradle 6.0.1 with kotlin 1.3.61. Find out from where those versions come and fix it there and then remove this block. (hint: use gradle :dependencies task)
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation(project(":tools:entry-generator"))
    implementation("org.jetbrains.kotlin:kotlin-native-utils:1.3.61")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.61")
}

/*
bintray {
    user = System.getenv('BINTRAY_USER')
    key = System.getenv('BINTRAY_KEY')
    publications = ['pluginMaven']
    pkg {
        repo = 'kotlin-godot'
        name = 'godot-gradle-plugin'
        licenses = ['Apache-2.0']
        version {
            name = project.ext.version
            desc = "Godot gradle plugin ${project.ext.version}"
            released = new Date()
            vcsTag = project.ext.version
        }
    }
}*/


repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    maven("https://dl.bintray.com/kotlin/kotlin-dev")
}

tasks.build {
    finalizedBy(tasks.publishToMavenLocal)
}