package godot.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.FeaturePreviews
import org.gradle.api.internal.file.FileResolver
import org.gradle.internal.cleanup.BuildOutputCleanupRegistry
import org.gradle.internal.reflect.Instantiator
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.konan.target.HostManager
import org.jetbrains.kotlin.konan.target.presetName
import javax.inject.Inject


class KotlinGodotPlugin @Inject constructor(): Plugin<Project> {

    companion object {
        const val GodotLibraryVersion = "1.0.0"
        const val KotlinVersion = "1.3.20"
        const val LibrariesDependency = "org.godotengine.kotlin:godot-library:$GodotLibraryVersion"
    }


    private val sourceSetsInformation = HashMap<KotlinSourceSet, GodotSourceSetInformation>()

    override fun apply(project: Project) {
        project.run {
            //pluginManager.apply(KotlinMultiplatformPluginWrapper::class.java)
            val kotlin = extensions.getByName("kotlin") as KotlinMultiplatformExtension

            convention.plugins["configureGodot"] = ConfigureGodotConvention(this, sourceSetsInformation)


            HostManager().targets.forEach { (_, target) ->
                val name = "godot" + target.presetName.capitalize()

                KotlinGodotTargetPreset(
                        name,
                        project,
                        target,
                        KotlinVersion,
                        sourceSetsInformation
                ).apply {
                    kotlin.presets.add(this)
                }
            }
        }
    }
}