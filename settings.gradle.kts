/*pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "kotlin2js") {
                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
            }
        }
    }
}*/
include(":app", ":web", ":shared")
rootProject.name = "GradePoint"
enableFeaturePreview("GRADLE_METADATA")