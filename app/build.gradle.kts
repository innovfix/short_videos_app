import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

// Load local.properties
val localProperties = Properties().apply {
    val localPropsFile = rootProject.file("local.properties")
    if (localPropsFile.exists()) {
        load(localPropsFile.inputStream())
    }
}

android {
    namespace = "com.app.reelshort"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.app.reelshort"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "APPLOVIN_KEY", "\"${localProperties["APPLOVIN_KEY"]}\"")
//        buildConfigField("String", "FACEBOOK_APP_ID", "\"${localProperties["FACEBOOK_APP_ID"]}\"")
        buildConfigField("String", "BASE_URL", "\"${localProperties["BASE_URL"]}\"")
        buildConfigField("String", "SHA_256", "\"${localProperties["SHA_256"]}\"")
        buildConfigField("String", "HOST_EMAIL", "\"${localProperties["HOST_EMAIL"]}\"")
        buildConfigField("String", "HOST_DISPLAY_NAME", "\"${localProperties["HOST_DISPLAY_NAME"]}\"")
        buildConfigField("String", "HOST_LOGIN_TYPE_ID", "\"${localProperties["HOST_LOGIN_TYPE_ID"]}\"")
        buildConfigField("String", "HOST_PROFILE_URL", "\"${localProperties["HOST_PROFILE_URL"]}\"")
        buildConfigField("String", "HOST_LOGIN_TYPE_GUEST", "\"${localProperties["HOST_LOGIN_TYPE_GUEST"]}\"")
        buildConfigField("String", "HOST_LOGIN_TYPE_FB", "\"${localProperties["HOST_LOGIN_TYPE_FB"]}\"")
        buildConfigField("String", "HOST_LOGIN_TYPE_GOOGLE", "\"${localProperties["HOST_LOGIN_TYPE_GOOGLE"]}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
//        getByName("debug") {
//            signingConfig = signingConfigs.getByName("debug")
//        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }
    bundle {
        language {
            enableSplit = false
        }
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
    kapt {
        correctErrorTypes = true
    }



}

dependencies {

    //androidx
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)


    //Other dependencies
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    //tools
    implementation(libs.sdp.android)
    implementation(libs.ssp.android)
    implementation(libs.lottie)
    implementation(libs.glide)
    implementation(libs.dotsindicator)
    implementation(libs.android.advancedwebview)


    //retrofit2
    implementation(libs.converter.gson)
    implementation(libs.retrofit)
    implementation(libs.okhttp)

    // OkHttp (optional logging)
    implementation(libs.logging.interceptor)

    //exoplayer
    implementation(libs.exoplayer)

    // RatingBar
    implementation(libs.ratingbar)

    //razorpay
    implementation(libs.checkout)

    // Stripe Android SDK
    implementation(libs.stripe.java)
    implementation(libs.stripe.android)


    implementation(libs.play.services.ads)
    implementation(libs.applovin.sdk)

//    // Facebook SDK
//    implementation(libs.facebook.android.sdk)
//    implementation(libs.firebase.auth.ktx)

    //auth
    implementation(libs.play.services.auth)
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-crashlytics")

//    implementation("com.google.android.gms:play-services-auth:18.1.0")
//    implementation("com.google.android.gms:play-services-identity:17.0.0")
//    implementation("com.google.android.gms:play-services-auth-api-phone:17.4.0")
//    implementation("com.google.android.gms:play-services-base:17.3.0")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
}