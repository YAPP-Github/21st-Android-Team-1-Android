plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = Configs.PRESENTATION_NAME
    compileSdk = Configs.COMPILE_SDK

    defaultConfig {
        minSdk = Configs.MIN_SDK
        targetSdk = Configs.TARGET_SDK

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
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

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation(ModuleDependency.Default.JUNIT)
    androidTestImplementation(ModuleDependency.Default.TEST_RUNNER)
    androidTestImplementation(ModuleDependency.Default.ESPRESSO_CORE)
    implementation(ModuleDependency.Default.CORE)
    implementation(ModuleDependency.Default.APP_COMPAT)
    implementation(ModuleDependency.Default.MATERIAL)
    implementation(ModuleDependency.Default.CONSTRAINT_LAYOUT)
    testImplementation(ModuleDependency.Default.JUNIT)
    androidTestImplementation(ModuleDependency.Default.TEST_RUNNER)
    androidTestImplementation(ModuleDependency.Default.ESPRESSO_CORE)
    implementation(ModuleDependency.KTX.FRAGMENT)
    implementation(ModuleDependency.KTX.VIEWMODEL)
    implementation(ModuleDependency.KTX.LIFECYCLE)
    implementation(ModuleDependency.Glide.GLIDE)
    kapt(ModuleDependency.Glide.GLIDE_COMPILER)
    implementation(ModuleDependency.Navigation.FRAGMENT_KTX)
    implementation(ModuleDependency.Navigation.UI_KTX)
    implementation(ModuleDependency.Timber.TIMBER)
    implementation(ModuleDependency.Coroutine.COROUTINE_CORE)
    implementation(ModuleDependency.Coroutine.COROUTINE_ANDROID)
    implementation(ModuleDependency.Hilt.HILT)
    kapt(ModuleDependency.Hilt.HILT_COMPILER)
}