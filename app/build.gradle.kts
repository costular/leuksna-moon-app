plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("org.jlleitschuh.gradle.ktlint")
}


android {
    compileSdkVersion(App.compileVersion)
    buildToolsVersion(App.buildToolsVersion)
    defaultConfig {
        applicationId = "com.costular.leuksna_moon_phases"
        minSdkVersion(App.minSdk)
        targetSdkVersion(App.targetSdk)
        versionCode = App.versionCode
        versionName = App.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}")

    implementation("androidx.appcompat:appcompat:${Versions.appCompat}")
    implementation("androidx.core:core-ktx:1.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}")
    implementation("androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}")
    implementation("com.google.android.material:material:${Versions.material}")
    implementation("androidx.navigation:navigation-fragment-ktx:${Versions.navigation}")
    implementation("androidx.navigation:navigation-ui-ktx:${Versions.navigation}")
    implementation("devs.mulham.horizontalcalendar:horizontalcalendar:${Versions.horizontalCalendar}")
    implementation("org.koin:koin-androidx-viewmodel:${Versions.koin}")
    implementation("org.koin:koin-androidx-fragment:${Versions.koin}")
    implementation("io.uniflow:uniflow-android:${Versions.uniflow}")
    implementation("com.jakewharton.threetenabp:threetenabp:${Versions.threeTen}")
    implementation("com.sofakingforever.libraries:animated-stars-android:1.1.4@aar")
    implementation("com.github.kizitonwose:CalendarView:${Versions.calendarView}")
    implementation("com.github.tfcporciuncula:flow-preferences:${Versions.flowPreferences}")
    implementation("com.google.android.gms:play-services-location:${Versions.playLocation}")
    implementation("org.permissionsdispatcher:permissionsdispatcher:${Versions.permissionsDispatcher}")
    kapt("org.permissionsdispatcher:permissionsdispatcher-processor:${Versions.permissionsDispatcher}")

    testImplementation("junit:junit:4.12")
    testImplementation("org.koin:koin-test:${Versions.koin}")
    testImplementation("io.uniflow:uniflow-android-test:${Versions.uniflow}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}")
    testImplementation("io.mockk:mockk:${Versions.mockk}")
    testImplementation("io.kotlintest:kotlintest:${Versions.kotlintest}")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.2")
    testImplementation("org.threeten:threetenbp:1.3.2")

    debugImplementation("androidx.fragment:fragment-testing:${Versions.fragment}")

    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
    androidTestImplementation("com.kaspersky.android-components:kaspresso:${Versions.kaspresso}")
    androidTestImplementation("androidx.navigation:navigation-testing:${Versions.navigation}")
}
