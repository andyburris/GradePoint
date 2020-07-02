object Versions {
    const val min_sdk = 23
    const val target_sdk = 29
    const val compile_sdk = 29

    const val kotlin = "1.3.72"
    const val android_x = "1.1.0"
    const val android_gradle_plugin = "4.1.0-alpha09"
    const val junit = "4.12"
    const val sqlDelight = "1.3.0"
    const val ktor = "1.3.2"
    const val stately = "1.0.2"
    const val multiplatformSettings = "0.6"
    const val coroutines = "1.3.7"
    const val koin = "3.0.0-alpha-9"
    const val serialization = "0.20.0"
    const val cocoapodsext = "0.6"
    const val jetpackCompose = "0.1.0-dev14"
    const val klock = "1.10.3"
    const val slf4j = "1.7.30"
    const val kissme = "0.2.5"
    const val composeShimmer = "1.0.1"
    const val kotlinResult = "1.1.7"
}

object Deps {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val app_compat_x = "androidx.appcompat:appcompat:${Versions.android_x}"
    const val material_x = "com.google.android.material:material:${Versions.android_x}"
    const val core_ktx = "androidx.core:core-ktx:${Versions.android_x}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.android_x}"
    const val android_gradle_plugin = "com.android.tools.build:gradle:${Versions.android_gradle_plugin}"
    const val junit = "junit:junit:${Versions.junit}"
    const val stately = "co.touchlab:stately-common:${Versions.stately}"
    const val multiplatformSettings = "com.russhwolf:multiplatform-settings:${Versions.multiplatformSettings}"
    const val multiplatformSettingsTest = "com.russhwolf:multiplatform-settings-test:${Versions.multiplatformSettings}"
    const val cocoapodsext = "co.touchlab:kotlinnativecocoapods:${Versions.cocoapodsext}"
    const val slf4j = "org.slf4j:slf4j-simple:${Versions.slf4j}"
    const val composeShimmer = "com.github.kazemihabib:compose-shimmer:${Versions.composeShimmer}"
    const val kotlinResult = "com.michael-bull.kotlin-result:kotlin-result:${Versions.kotlinResult}"

    object Koin {
        const val core = "org.koin:koin-core:${Versions.koin}"
        const val viewModel = "org.koin:koin-android-viewmodel:${Versions.koin}"
    }

    object Klock {
        const val common = "com.soywiz.korlibs.klock:klock:${Versions.klock}"
        const val android = "com.soywiz.korlibs.klock:klock-android:${Versions.klock}"
    }

    object JetpackCompose {
        const val runtime = "androidx.compose:compose-runtime:${Versions.jetpackCompose}"
        const val core = "androidx.ui:ui-core:${Versions.jetpackCompose}"
        const val tooling = "androidx.ui:ui-tooling:${Versions.jetpackCompose}"
        const val layout = "androidx.ui:ui-layout:${Versions.jetpackCompose}"
        const val material = "androidx.ui:ui-material:${Versions.jetpackCompose}"
        const val foundation = "androidx.ui:ui-foundation:${Versions.jetpackCompose}"
        const val animation = "androidx.ui:ui-animation:${Versions.jetpackCompose}"
        const val test = "androidx.ui:ui-test:${Versions.jetpackCompose}"
        const val icons = "androidx.ui:ui-material-icons-extended:${Versions.jetpackCompose}"
    }

    object AndroidXTest {
        const val core = "androidx.test:core:${Versions.android_x}"
        const val junit = "androidx.test.ext:junit:${Versions.android_x}"
        const val runner = "androidx.test:runner:${Versions.android_x}"
        const val rules = "androidx.test:rules:${Versions.android_x}"
    }

    object KotlinTest {
        const val common = "org.jetbrains.kotlin:kotlin-test-common:${Versions.kotlin}"
        const val annotations = "org.jetbrains.kotlin:kotlin-test-annotations-common:${Versions.kotlin}"
        const val jvm = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
        const val junit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    }

    object Coroutines {
        const val common = "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${Versions.coroutines}"
        const val jdk = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val native = "org.jetbrains.kotlinx:kotlinx-coroutines-core-native:${Versions.coroutines}"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        const val js = "org.jetbrains.kotlinx:kotlinx-coroutines-core-js:${Versions.coroutines}"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    }

    object SqlDelight {
        const val gradle = "com.squareup.sqldelight:gradle-plugin:${Versions.sqlDelight}"
        const val runtime = "com.squareup.sqldelight:runtime:${Versions.sqlDelight}"
        const val runtimeJdk = "com.squareup.sqldelight:runtime-jvm:${Versions.sqlDelight}"
        const val driverIos = "com.squareup.sqldelight:native-driver:${Versions.sqlDelight}"
        const val driverAndroid = "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}"
        const val driverJS = "com.squareup.sqldelight:sqljs-driver:${Versions.sqlDelight}"
    }

    object Ktor {
        const val commonCore = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val commonJson = "io.ktor:ktor-client-json:${Versions.ktor}"
        const val commonSerialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"
        const val commonLogging = "io.ktor:ktor-client-logging:${Versions.ktor}"
        const val jvmCore = "io.ktor:ktor-client-core-jvm:${Versions.ktor}"
        const val jvmJson = "io.ktor:ktor-client-json-jvm:${Versions.ktor}"
        const val jvmLogging = "io.ktor:ktor-client-logging-jvm:${Versions.ktor}"
        const val androidCore = "io.ktor:ktor-client-okhttp:${Versions.ktor}"
        const val androidSerialization = "io.ktor:ktor-client-serialization-jvm:${Versions.ktor}"
        const val ios = "io.ktor:ktor-client-ios:${Versions.ktor}"
        const val iosCore = "io.ktor:ktor-client-core-native:${Versions.ktor}"
        const val iosJson = "io.ktor:ktor-client-json-native:${Versions.ktor}"
        const val iosSerialization = "io.ktor:ktor-client-serialization-native:${Versions.ktor}"
        const val iosLogging = "io.ktor:ktor-client-logging-native:${Versions.ktor}"
        const val js = "io.ktor:ktor-client-js:${Versions.ktor}"
        const val jsCore = "io.ktor:ktor-client-core-js:${Versions.ktor}"
        const val jsJson = "io.ktor:ktor-client-json-js:${Versions.ktor}"
        const val jsSerialization = "io.ktor:ktor-client-serialization-js:${Versions.ktor}"
        const val jsLogging = "io.ktor:ktor-client-logging-js:${Versions.ktor}"
    }

    object Kissme {
        const val common = "com.netguru.kissme:common:${Versions.kissme}"
        const val android = "com.netguru.kissme:android:${Versions.kissme}"
        const val ios = "com.netguru.kissme:ios:${Versions.kissme}"
        const val js = "com.netguru.kissme:js:${Versions.kissme}"
    }
}
