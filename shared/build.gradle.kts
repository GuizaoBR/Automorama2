
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

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)


            api(libs.precompose)
            api(libs.precompose.viewmodel)
            api(libs.precompose.molecule)
            implementation(libs.coroutines.extensions)
        }
        androidMain.dependencies {
            // put your Multiplatform dependencies here
            implementation(libs.android.driver)
        }
        iosMain.dependencies {
            // put your Multiplatform dependencies here
            implementation(libs.native.driver)

        }
        nativeMain.dependencies {
            implementation(libs.native.driver)
        }
        jvmMain.dependencies {
            implementation(libs.sqlite.driver)
        }
    }
    
}

android {
    namespace = "germano.guilherme.automorama2.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
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