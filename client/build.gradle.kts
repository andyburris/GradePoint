plugins {
    kotlin("js")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(kotlin("stdlib-js"))
    implementation(Deps.Koin.core) //for some reason the client module doesn't recognize this same dependency in commonMain, so reimporting it here
    implementation(Deps.Klock.js)

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.3.7")
    implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.7.1")
    implementation(npm("text-encoding"))
    implementation(npm("abort-controller"))
    implementation(npm("bufferutil"))
    implementation(npm("utf-8-validate"))
    implementation(npm("fs"))

    implementation("org.jetbrains:kotlin-react:16.13.1-pre.110-kotlin-1.3.72")
    implementation("org.jetbrains:kotlin-react-dom:16.13.1-pre.110-kotlin-1.3.72")
    implementation(npm("react", "16.13.1"))
    implementation(npm("react-dom", "16.13.1"))

    //Kotlin Styled (chapter 3)
    implementation("org.jetbrains:kotlin-styled:1.0.0-pre.110-kotlin-1.3.72")
    implementation("org.jetbrains", "kotlin-css-js", "1.0.0-110-kotlin-1.3.72")
    implementation(npm("styled-components"))
    implementation(npm("inline-style-prefixer"))

    implementation(npm("rc-progress"))
    implementation(npm("react-flip-toolkit"))

    implementation(project(":shared"))
}


kotlin {
    target {
        useCommonJs()
        browser {
            // https://kotlinlang.org/docs/reference/javascript-dce.html#known-issue-dce-and-ktor
            dceTask {
                keep("ktor-ktor-io.\$\$importsForInline\$\$.ktor-ktor-io.io.ktor.utils.io")
            }
        }
    }
}