buildscript {
    repositories {
        repositories {
            mavenLocal()
            mavenCentral()
            jcenter()
            maven("https://oss.sonatype.org/content/repositories/snapshots/")
            maven("https://dl.bintray.com/kotlin/kotlin-eap")
            maven("https://maven.google.com")
        }
        dependencies {
            classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.61")
        }
    }

    allprojects {
        apply(plugin = "idea")

        repositories {
            mavenLocal()
            mavenCentral()
            jcenter()
            maven("https://oss.sonatype.org/content/repositories/snapshots/")
            maven("https://oss.sonatype.org/content/repositories/releases/")
        }
    }
}
