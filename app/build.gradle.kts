plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlinx-serialization")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.kelvin.weatherappkelvin"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kelvin.weatherappkelvin"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "FORECAST_BASE_URL", "\"https://api.weatherapi.com\"")
            buildConfigField("String", "FORECAST_KEY", "\"0a2963fa41f146daa1c171250201408\"")
        }
        debug {
            buildConfigField("String", "FORECAST_BASE_URL", "\"https://api.weatherapi.com\"")
            buildConfigField("String", "FORECAST_KEY", "\"0a2963fa41f146daa1c171250201408\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.4")
    implementation("com.airbnb.android:lottie:3.4.0")
    implementation("androidx.hilt:hilt-common:1.2.0")

    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    //Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

    implementation("com.squareup.moshi:moshi-kotlin:1.12.0")

    //palette
    implementation("androidx.palette:palette-ktx:1.0.0")

    //    Room
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    //Fuzed location
    implementation("com.google.android.gms:play-services-location:18.0.0")

    //Easy Permission
    implementation("pub.devrel:easypermissions:3.0.0")

    implementation("com.jakewharton.threetenabp:threetenabp:1.2.1")

    implementation("androidx.work:work-runtime-ktx:2.8.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // JUnit 4
    testImplementation ("junit:junit:4.13.2")

    // Mockito Core
    testImplementation ("org.mockito:mockito-core:5.5.0")

    // For mocking final classes
    testImplementation ("org.mockito:mockito-inline:4.11.0")

    // Mockito Kotlin extension
    testImplementation ("org.mockito.kotlin:mockito-kotlin:5.0.0")

    // For coroutines testing
    testImplementation( "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    testImplementation( "org.robolectric:robolectric:4.9")

    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

}