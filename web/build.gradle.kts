plugins {
    id("org.jetbrains.kotlin.js")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(Deps.Koin.core) //for some reason the client module doesn't recognize this same dependency in commonMain, so reimporting it here
    implementation(Deps.Klock.js)

    implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.7.1")
    implementation(npm("text-encoding", "0.7.0"))
    implementation(npm("abort-controller", "3.0.0"))
    implementation(npm("bufferutil", "4.0.1"))
    implementation(npm("utf-8-validate", "5.0.2"))
    //implementation(npm("fs", ""))

    implementation("org.jetbrains:kotlin-react:16.13.1-pre.109-kotlin-1.3.72")
    implementation("org.jetbrains:kotlin-react-dom:16.13.1-pre.109-kotlin-1.3.72")
    implementation(npm("react", "16.13.1"))
    implementation(npm("react-dom", "16.13.1"))

    //Kotlin Styled (chapter 3)
    implementation("org.jetbrains:kotlin-styled:1.0.0-pre.109-kotlin-1.3.72")
    //implementation("org.jetbrains", "kotlin-css-js", "1.0.0-104-kotlin-1.3.72")
    implementation(npm("styled-components", "4.4.1"))
    implementation(npm("inline-style-prefixer", "6.0.0"))

    implementation(npm("rc-progress", "3.0.0"))
    implementation(npm("react-flip-toolkit", "7.0.12"))

    implementation(project(":shared"))
}


kotlin {
    js {
        useCommonJs()
        //binaries.executable()
        browser()
    }
}