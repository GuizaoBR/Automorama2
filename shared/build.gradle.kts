
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("app.cash.sqldelight")
    //alias(libs.plugins.compose.compiler)


}

kotlin {
//    androidTarget()
//    jvm("desktop")
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
            implementation(libs.kotlinx.datetime)
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

}
dependencies {
    implementation(libs.androidx.media3.common)
}

sqldelight {
    databases {
        create("Automorama2Database") {
            packageName.set("germano.guilherme.automorama2")
            deriveSchemaFromMigrations.set(true)
            verifyMigrations.set(true)

        }
    }
}