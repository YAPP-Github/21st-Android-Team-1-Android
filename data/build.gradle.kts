plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = Configs.DATA_NAME
    compileSdk = Configs.COMPILE_SDK

    defaultConfig {
        minSdk = Configs.MIN_SDK
        targetSdk = Configs.TARGET_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":domain"))
    testImplementation(ModuleDependency.Default.JUNIT)
    androidTestImplementation(ModuleDependency.Default.TEST_RUNNER)
    androidTestImplementation(ModuleDependency.Default.ESPRESSO_CORE)
    implementation(ModuleDependency.Timber.TIMBER)
    implementation(ModuleDependency.Retrofit2.RETROFIT)
    implementation(ModuleDependency.Okhttp3.LOGGING_INTERCEPTOR)
    implementation(ModuleDependency.Retrofit2.CONVERTER_GSON)
    implementation(ModuleDependency.Default.CORE)
    implementation(ModuleDependency.Coroutine.COROUTINE_CORE)
    implementation(ModuleDependency.Coroutine.COROUTINE_ANDROID)
    implementation(ModuleDependency.DataStore.DATASTORE)
    implementation(ModuleDependency.Hilt.HILT)
    kapt(ModuleDependency.Hilt.HILT_COMPILER)
}