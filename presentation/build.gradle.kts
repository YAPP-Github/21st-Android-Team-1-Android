import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

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
        manifestPlaceholders["KAKAO_APP_KEY"] = gradleLocalProperties(rootDir).getProperty("KAKAO_APP_KEY")
        buildConfigField("String","KAKAO_APP_KEY", gradleLocalProperties(rootDir).getProperty("KAKAO_APP_KEY"))
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
    implementation(ModuleDependency.KaKao.LOGIN)
    implementation(ModuleDependency.MLkit.ML_Kit)
    implementation(ModuleDependency.SplashScreen.SPLASH_SCREEN)
    implementation(ModuleDependency.Paging.PAGING_RUNTIME_KTX)
    implementation(ModuleDependency.Zxing.ZXING)
}