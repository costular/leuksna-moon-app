
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("org.koin:koin-gradle-plugin:${Versions.koin}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktlintGradle}")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url="http://dl.bintray.com/sofakingforever/libraries")
        maven(url="https://jitpack.io")
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}

apply(plugin = "org.jlleitschuh.gradle.ktlint")