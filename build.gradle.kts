plugins {
    alias(libs.plugins.android) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt.android) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("androidx.room") version "2.7.0-alpha13" apply false

}