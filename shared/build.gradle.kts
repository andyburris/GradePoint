import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("co.touchlab.native.cocoapods")
    id("kotlinx-serialization")
    id("com.android.library")
    id("com.squareup.sqldelight")
}

android {
    compileSdkVersion(29)
    buildToolsVersion("29.0.3")

    defaultConfig {
        minSdkVersion(Versions.min_sdk)
        targetSdkVersion(Versions.target_sdk)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            minifyEnabled(false)
        }
    }
}

kotlin {
    targets {
        android()
        //Revert to just ios() when gradle plugin can properly resolve it
        val onPhone = System.getenv("SDK_NAME")?.startsWith("iphoneos")?:false
        if(onPhone){
            iosArm64("ios")
        }else{
            iosX64("ios")
        }
    }

    js {
        browser { }
    }

    targets.getByName<KotlinNativeTarget>("ios").compilations["main"].kotlinOptions.freeCompilerArgs +=
        listOf("-Xobjc-generics", "-Xg0")

    version = "1.1"

    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }
    }

    sourceSets["commonMain"].dependencies {
        //implementation(kotlin("stdlib-common", Versions.kotlin))
        implementation(Deps.serialization)
        implementation(Deps.SqlDelight.runtime)
        implementation(Deps.Ktor.commonCore)
        implementation(Deps.Ktor.commonJson)
        implementation(Deps.Ktor.commonLogging)
        implementation(Deps.Ktor.commonSerialization)
        implementation(Deps.Coroutines.common)
        implementation(Deps.stately)
        implementation(Deps.Koin.core)
        implementation(Deps.Klock.common)
        implementation(Deps.Kissme.common)
        //implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:${Versions.serialization}")
        //implementation("com.michael-bull.kotlin-result:kotlin-result:1.1.7")
    }

    sourceSets["commonTest"].dependencies {
        implementation(Deps.KotlinTest.common)
        implementation(Deps.KotlinTest.annotations)
        implementation(Deps.Coroutines.common)
    }

    sourceSets["androidMain"].dependencies {
        //implementation(kotlin("stdlib", Versions.kotlin))
        implementation(Deps.SqlDelight.driverAndroid)
//        implementation(Deps.Ktor.androidCore)
//        implementation(Deps.Ktor.androidSerialization)
//        implementation(Deps.Ktor.jvmCore)
//        implementation(Deps.Ktor.jvmJson)
//        implementation(Deps.Ktor.jvmLogging)
        implementation(Deps.slf4j)
        implementation(Deps.Kissme.android)
        implementation(Deps.Koin.android)
    }

    sourceSets["androidTest"].dependencies {
        implementation(Deps.KotlinTest.jvm)
        implementation(Deps.KotlinTest.junit)
        implementation(Deps.AndroidXTest.core)
        implementation(Deps.AndroidXTest.junit)
        implementation(Deps.AndroidXTest.runner)
        implementation(Deps.AndroidXTest.rules)
        implementation("org.robolectric:robolectric:4.3")
    }

    sourceSets["iosMain"].dependencies {
        implementation(Deps.SqlDelight.driverIos)
        implementation(Deps.Ktor.ios)
//        implementation(Deps.Ktor.iosCore)
//        implementation(Deps.Ktor.iosJson)
//        implementation(Deps.Ktor.iosSerialization)
//        implementation(Deps.Ktor.iosLogging)
        implementation(Deps.Kissme.ios)
    }

    sourceSets["jsMain"].dependencies {
        //implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:${Versions.serialization}")
        implementation(Deps.Ktor.js)
        implementation(Deps.Ktor.jsCore)
        implementation(Deps.Ktor.jsJson)
        implementation(Deps.Ktor.jsSerialization)
        implementation(Deps.Ktor.jsLogging)
        implementation(Deps.Koin.js)
        implementation(Deps.SqlDelight.runtimeJS)
    }

    sourceSets["jsTest"].dependencies {
        implementation(kotlin("test-js"))
    }

    cocoapodsext {
        summary = "Common library for the KaMP starter kit"
        homepage = "https://github.com/touchlab/KaMPStarter"
        isStatic = false
    }
}

sqldelight {
    database("SubjectConfigDb") {
        packageName = "com.andb.apps.aspen"
    }
}
