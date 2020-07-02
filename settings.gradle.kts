pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "kotlin2js") {
                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
            }
        }
    }
}
include(":app", ":client", ":shared")
rootProject.name = "Aspen"
enableFeaturePreview("GRADLE_METADATA")