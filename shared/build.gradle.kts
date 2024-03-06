
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("app.cash.sqldelight")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
            
        }
    }
    
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.coroutines.extensions)
//            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
        }
        androidMain.dependencies {
            // put your Multiplatform dependencies here
            implementation(libs.android.driver)
        }
        iosMain.dependencies {
            // put your Multiplatform dependencies here
            implementation(libs.native.driver)
            implementation(libs.co.touchlab.stately.common)

        }
        nativeMain.dependencies {
            implementation(libs.native.driver)
        }
        jvmMain.dependencies {
            implementation(libs.sqlite.driver)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
    
}

android {
    namespace = "germano.guilherme.automorama2.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    //    buildFeatures {
//        compose = true
//    }
//    composeOptions {
//        this.kotlinCompilerExtensionVersion = "1.5.8-dev-k1.9.22-42b6ec2b037"
//    }
}

sqldelight {
    databases {
        create("Automorama2Database") {
            packageName.set("germano.guilherme.automorama2")
        }
    }
}