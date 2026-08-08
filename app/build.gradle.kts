import com.github.triplet.gradle.play.PlayPublisherExtension
import com.github.triplet.gradle.androidpublisher.ResolutionStrategy
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    id("com.costular.android.application")
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.play.publisher)
    alias(libs.plugins.versions)
}

if (file("google-services.json").isFile) {
    apply(plugin = "com.google.gms.google-services")
}

android {
    namespace = "com.costular.leuksna_moon_phases"

    defaultConfig {
        applicationId = "com.costular.leuksna_moon_phases"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = rootProject.file("release.keystore")
            storePassword = System.getenv("ANDROID_KEYSTORE_PASSPHRASE")
            keyAlias = System.getenv("ANDROID_KEYSTORE_ALIAS")
            keyPassword = System.getenv("ANDROID_KEYSTORE_PASSPHRASE")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        viewBinding = true
    }
}

configure<PlayPublisherExtension> {
    serviceAccountCredentials.set(file("service-account-key.json"))
    defaultToAppBundles.set(true)
    track.set("production")
    resolutionStrategy.set(ResolutionStrategy.AUTO)
}

configure<KtlintExtension> {
    version.set("1.8.0")
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.koin.android)
    implementation(libs.coroutines.android)
    implementation(libs.uniflow.android)
    implementation(libs.calendar.view)
    implementation(libs.flow.preferences)
    implementation(libs.play.services.location)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.kastro)

    testImplementation(libs.junit)
    testImplementation(libs.koin.test)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.mockk)

    debugImplementation(libs.androidx.fragment.testing)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.kaspresso)
    androidTestImplementation(libs.androidx.navigation.testing)
}
