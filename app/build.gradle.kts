plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.compose)
}

android {
  namespace = "app.cash.paykit.sample"
  compileSdk = libs.versions.compileSdk.get().toInt()

  defaultConfig {
    applicationId = "app.cash.paykit.sample"
    minSdk = libs.versions.minSdk.get().toInt()
    targetSdk = libs.versions.targetSdk.get().toInt()
    versionCode = 3
    versionName = "1.0.2"

    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
      signingConfig = signingConfigs.getByName("debug")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  buildFeatures {
    compose = true
  }

  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  // Compose BOM for version alignment
  implementation(platform(libs.compose.bom))

  // Core Compose dependencies
  implementation(libs.bundles.compose)

  // Activity and Lifecycle Compose integration
  implementation(libs.activity.compose)
  implementation(libs.bundles.lifecycle)

  // Debug tooling
  debugImplementation(libs.bundles.compose.debug)

  // Common Android library dependencies
  implementation(libs.core.ktx)
  implementation(libs.material)

  // Cash App PayKit Android SDK dependency
  implementation(libs.paykit.core)
}
