plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java{
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(ModuleDependency.Javax.INJECT)
    implementation(ModuleDependency.Coroutine.COROUTINE_CORE)
    implementation(ModuleDependency.Paging.PAGING_COMMON)
}