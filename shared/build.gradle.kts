
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("app.cash.sqldelight")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
            
        }
    }
    
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    
    jvm()
    val coroutinesVersion = "1.7.3"
    val sqlDelightVersion = "2.0.1"
    
    sourceSets {
        commonMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")


            api("moe.tlaster:precompose:1.5.0")
            api("moe.tlaster:precompose-viewmodel:1.5.0")
        }
        androidMain.dependencies {
            // put your Multiplatform dependencies here
            implementation("app.cash.sqldelight:android-driver:$sqlDelightVersion")
        }
        iosMain.dependencies {
            // put your Multiplatform dependencies here
            implementation("app.cash.sqldelight:native-driver:$sqlDelightVersion")

        }
        nativeMain.dependencies {
            implementation("app.cash.sqldelight:native-driver:$sqlDelightVersion")
        }
    }
    
}

android {
    namespace = "germano.guilherme.automorama2.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        this.kotlinCompilerExtensionVersion = "1.5.8-dev-k1.9.22-42b6ec2b037"
    }
}

sqldelight {
    databases {
        create("Automorama2Database") {
            packageName.set("germano.guilherme.automorama2")
        }
    }
}