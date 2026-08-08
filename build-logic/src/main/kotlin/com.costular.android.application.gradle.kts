import com.android.build.api.dsl.ApplicationExtension

plugins {
    id("com.android.application")
}

extensions.configure<ApplicationExtension> {
    compileSdk = 36

    defaultConfig {
        minSdk = 26
        targetSdk = 36
        versionCode = providers.gradleProperty("appVersionCode").get().toInt()
        versionName = providers.gradleProperty("appVersionName").get()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
