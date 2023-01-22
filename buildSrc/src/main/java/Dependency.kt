object Versions {
    const val ANDROID_CORE = "1.7.0"
    const val ANDROID_COMPAT = "1.5.1"
    const val ANDROID_NAVIGATION = "2.5.3"
    const val ANDROID_VIEWMODEL_KTX = "2.5.1"
    const val ANDROID_LIFECYCLE_KTX = "2.5.1"
    const val ANDROID_FRAGMENT_KTX = "1.5.4"
    const val CONSTRAINT_LAYOUT = "2.1.4"
    const val ANDROID_DATASTORE = "1.0.0"
    const val JUNIT = "4.13.2"
    const val TEST_RUNNER = "1.1.4"
    const val ESPRESSO_CORE = "3.5.0"
    const val COROUTINE = "1.6.4"

    const val ROOM = "2.4.3"
    const val HILT = "2.44"
    const val OKHTTP = "4.9.0"
    const val RETROFIT = "2.9.0"
    const val TIMBER = "5.0.1"
    const val ML_KIT = "17.0.2"
    const val GLIDE = "4.14.2"
    const val METERIAL = "1.7.0"
    const val GSON = "2.9.0"
    const val INJECT = "1"
    const val PAGING = "3.1.1"
    const val KAKAO_LOGIN = "2.12.0"
    const val SPLASH_SCREEN = "1.0.0-beta02"
}

object ModuleDependency {
    object Default {
        const val CORE = "androidx.core:core-ktx:${Versions.ANDROID_CORE}"
        const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.ANDROID_COMPAT}"
        const val MATERIAL = "com.google.android.material:material:${Versions.METERIAL}"
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
        const val JUNIT = "junit:junit:${Versions.JUNIT}"
        const val TEST_RUNNER = "androidx.test.ext:junit:${Versions.TEST_RUNNER}"
        const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
    }

    object Coroutine {
        const val COROUTINE_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINE}"
        const val COROUTINE_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINE}"
    }

    object KTX {
        const val FRAGMENT = "androidx.fragment:fragment-ktx:${Versions.ANDROID_FRAGMENT_KTX}"
        const val VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.ANDROID_VIEWMODEL_KTX}"
        const val LIFECYCLE = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ANDROID_LIFECYCLE_KTX}"
    }

    object Retrofit2 {
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
        const val CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    }

    object Okhttp3 {
        const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"
    }

    object GSON {
        const val GSON = "com.google.code.gson:gson:${Versions.GSON}}"
    }

    object Navigation {
        const val FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:${Versions.ANDROID_NAVIGATION}"
        const val UI_KTX = "androidx.navigation:navigation-ui-ktx:${Versions.ANDROID_NAVIGATION}"
    }

    object Room {
        const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
        const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"
        const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
    }

    object Glide {
        const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE}"
        const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:${Versions.GLIDE}"
    }

    object Paging {
        const val PAGING_RUNTIME_KTX = "androidx.paging:paging-runtime-ktx:${Versions.PAGING}"
    }

    object Timber{
        const val TIMBER = "com.jakewharton.timber:timber:${Versions.TIMBER}"
    }

    object DataStore{
        const val DATASTORE = "androidx.datastore:datastore-preferences:${Versions.ANDROID_DATASTORE}"
    }

    object Hilt {
        const val HILT = "com.google.dagger:hilt-android:${Versions.HILT}"
        const val HILT_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
    }

    object Javax {
        const val INJECT = "javax.inject:javax.inject:${Versions.INJECT}"
    }

    object KaKao{
        const val LOGIN = "com.kakao.sdk:v2-user:${Versions.KAKAO_LOGIN}"
    }

    object MLkit {
        const val ML_Kit = "com.google.mlkit:barcode-scanning:${Versions.ML_KIT}"
    }

    object SplashScreen {
        const val SPLASH_SCREEN = "androidx.core:core-splashscreen:${Versions.SPLASH_SCREEN}"
    }
}

