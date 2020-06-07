import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
}

val loginProperties = Properties().apply {
    load(
        FileInputStream(rootProject.file("login.properties"))
    )
}

android {
    compileSdkVersion(Versions.compile_sdk)
    defaultConfig {
        applicationId = "com.andb.apps.aspen"
        minSdkVersion(Versions.min_sdk)
        targetSdkVersion(Versions.target_sdk)
        versionCode = 4
        versionName = "0.1.1-internal03"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        resValue("string", "app_name", "GradePoint")
    }
    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            buildConfigField("String", "TEST_USERNAME", "")
            buildConfigField("String", "TEST_PASSWORD", "")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            resValue("string", "app_name", "GradePoint Beta")
            buildConfigField("String", "TEST_USERNAME", loginProperties["USERNAME"] as String)
            buildConfigField("String", "TEST_PASSWORD", loginProperties["PASSWORD"] as String)
            applicationIdSuffix = ".debug"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }
    buildToolsVersion = "30.0.0 rc2"

    composeOptions {
        kotlinCompilerVersion = "1.3.70-dev-withExperimentalGoogleExtensions-20200424"
        kotlinCompilerExtensionVersion = Versions.jetpackCompose
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/NOTICE")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/NOTICE.txt")
    }
}

dependencies {
    implementation(Deps.kotlin)
    implementation(project(":shared"))
    implementation("androidx.recyclerview:recyclerview:1.1.0")
    implementation(Deps.material_x)
    implementation(Deps.app_compat_x)
    implementation(Deps.core_ktx)
    implementation(Deps.Ktor.androidCore)
    implementation(Deps.constraintlayout)
    implementation(Deps.SqlDelight.runtimeJdk)
    implementation(Deps.SqlDelight.driverAndroid)
    implementation(Deps.Coroutines.jdk)
    implementation(Deps.Coroutines.android)
    implementation(Deps.multiplatformSettings)
    implementation(Deps.koinCore)
    testImplementation(Deps.junit)
    implementation(Deps.JetpackCompose.runtime)
    implementation(Deps.JetpackCompose.core)
    implementation(Deps.JetpackCompose.tooling)
    implementation(Deps.JetpackCompose.layout)
    implementation(Deps.JetpackCompose.material)
    implementation(Deps.JetpackCompose.foundation)
    implementation(Deps.JetpackCompose.animation)
    implementation(Deps.JetpackCompose.test)
    implementation(Deps.JetpackCompose.icons)
    implementation(Deps.Klock.android)
    implementation(Deps.composeShimmer)
}
