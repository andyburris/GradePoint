object Versions {
    val min_sdk = 21
    val target_sdk = 29
    val compile_sdk = 29

    val kotlin = "1.3.70"
    val android_x = "1.1.0"
    val android_gradle_plugin = "4.1.0-alpha08"
    val junit = "4.12"
    val sqlDelight = "1.3.0"
    val ktor = "1.3.2"
    val stately = "1.0.2"
    val multiplatformSettings = "0.6"
    val coroutines = "1.3.5-native-mt"
    val koin = "3.0.0-alpha-9"
    val serialization = "0.20.0"
    val cocoapodsext = "0.6"
    val jetpackCompose = "0.1.0-dev09"
    val klock = "1.10.3"
    val skrapeit = "master-SNAPSHOT"
    val slf4j = "1.7.30"
}

object Deps {
    val app_compat_x = "androidx.appcompat:appcompat:${Versions.android_x}"
    val material_x = "com.google.android.material:material:${Versions.android_x}"
    val core_ktx = "androidx.core:core-ktx:${Versions.android_x}"
    val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.android_x}"
    val android_gradle_plugin = "com.android.tools.build:gradle:${Versions.android_gradle_plugin}"
    val junit = "junit:junit:${Versions.junit}"
    val stately = "co.touchlab:stately-common:${Versions.stately}"
    val multiplatformSettings =
        "com.russhwolf:multiplatform-settings:${Versions.multiplatformSettings}"
    val multiplatformSettingsTest =
        "com.russhwolf:multiplatform-settings-test:${Versions.multiplatformSettings}"
    val koinCore = "org.koin:koin-core:${Versions.koin}"
    val cocoapodsext = "co.touchlab:kotlinnativecocoapods:${Versions.cocoapodsext}"
    val skrapeit = "com.github.skrapeit:skrape.it:${Versions.skrapeit}"
    val slf4j = "org.slf4j:slf4j-simple:${Versions.slf4j}"

    object Klock {
        val common = "com.soywiz.korlibs.klock:klock:${Versions.klock}"
        val android = "com.soywiz.korlibs.klock:klock-android:${Versions.klock}"
    }

    object JetpackCompose {
        val runtime = "androidx.compose:compose-runtime:${Versions.jetpackCompose}"
        val framework = "androidx.ui:ui-framework:${Versions.jetpackCompose}"
        val tooling = "androidx.ui:ui-tooling:${Versions.jetpackCompose}"
        val layout = "androidx.ui:ui-layout:${Versions.jetpackCompose}"
        val material = "androidx.ui:ui-material:${Versions.jetpackCompose}"
        val foundation = "androidx.ui:ui-foundation:${Versions.jetpackCompose}"
        val animation = "androidx.ui:ui-animation:${Versions.jetpackCompose}"
        val platform = "androidx.ui:ui-platform:${Versions.jetpackCompose}"
        val test = "androidx.ui:ui-test:${Versions.jetpackCompose}"
        val icons = "androidx.ui:ui-material-icons-extended:${Versions.jetpackCompose}"
    }

    object AndroidXTest {
        val core = "androidx.test:core:${Versions.android_x}"
        val junit = "androidx.test.ext:junit:${Versions.android_x}"
        val runner = "androidx.test:runner:${Versions.android_x}"
        val rules = "androidx.test:rules:${Versions.android_x}"
    }

    object KotlinTest {
        val common = "org.jetbrains.kotlin:kotlin-test-common:${Versions.kotlin}"
        val annotations = "org.jetbrains.kotlin:kotlin-test-annotations-common:${Versions.kotlin}"
        val jvm = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
        val junit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
        val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    }

    object Coroutines {
        val common = "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${Versions.coroutines}"
        val jdk = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        val native = "org.jetbrains.kotlinx:kotlinx-coroutines-core-native:${Versions.coroutines}"
        val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    }

    object SqlDelight {
        val gradle = "com.squareup.sqldelight:gradle-plugin:${Versions.sqlDelight}"
        val runtime = "com.squareup.sqldelight:runtime:${Versions.sqlDelight}"
        val runtimeJdk = "com.squareup.sqldelight:runtime-jvm:${Versions.sqlDelight}"
        val driverIos = "com.squareup.sqldelight:native-driver:${Versions.sqlDelight}"
        val driverAndroid = "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}"
    }

    object Ktor {
        val commonCore = "io.ktor:ktor-client-core:${Versions.ktor}"
        val commonJson = "io.ktor:ktor-client-json:${Versions.ktor}"
        val commonSerialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"
        val commonLogging = "io.ktor:ktor-client-logging:${Versions.ktor}"
        val jvmCore = "io.ktor:ktor-client-core-jvm:${Versions.ktor}"
        val jvmJson = "io.ktor:ktor-client-json-jvm:${Versions.ktor}"
        val jvmLogging = "io.ktor:ktor-client-logging-jvm:${Versions.ktor}"
        val androidCore = "io.ktor:ktor-client-okhttp:${Versions.ktor}"
        val androidSerialization = "io.ktor:ktor-client-serialization-jvm:${Versions.ktor}"
        val ios = "io.ktor:ktor-client-ios:${Versions.ktor}"
        val iosCore = "io.ktor:ktor-client-core-native:${Versions.ktor}"
        val iosJson = "io.ktor:ktor-client-json-native:${Versions.ktor}"
        val iosSerialization = "io.ktor:ktor-client-serialization-native:${Versions.ktor}"
        val iosLogging = "io.ktor:ktor-client-logging-native:${Versions.ktor}"
    }
}
