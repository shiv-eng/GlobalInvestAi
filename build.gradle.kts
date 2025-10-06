plugins {
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.compose).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
}

allprojects {
    configurations.all {
        resolutionStrategy {
            // Force all Compose dependencies to the same version to fix duplicates
            force(
                "androidx.compose.ui:ui:1.7.1",
                "androidx.compose.ui:ui-tooling:1.7.1",
                "androidx.compose.ui:ui-tooling-preview:1.7.1",
                "androidx.compose.material3:material3:1.3.0",
                "androidx.compose.ui:ui-test-junit4-android:1.7.1",
                "androidx.compose.ui:ui-test-android:1.7.1"
            )
        }
    }
}
