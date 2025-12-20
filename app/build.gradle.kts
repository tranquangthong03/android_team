    plugins {
        id("com.android.application")
        id("com.google.gms.google-services")
    }
    
    android {
        namespace = "com.example.android_project"
        compileSdk = 36   // Nếu sync lỗi vì chưa cài SDK 36 thì tạm đổi thành 35 hoặc 34
    
        defaultConfig {
            applicationId = "com.example.android_project"
            minSdk = 24
            targetSdk = 36
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
            }
        }
    
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }
    
    dependencies {
    
        // Thư viện UI cơ bản
        implementation("androidx.appcompat:appcompat:1.7.0")
        implementation("com.google.android.material:material:1.12.0")
        implementation("androidx.activity:activity:1.9.3")
        implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    
        // Navigation Component
        implementation("androidx.navigation:navigation-fragment:2.7.7")
        implementation("androidx.navigation:navigation-ui:2.7.7")
        implementation(libs.recyclerview)

        // Test
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.2.1")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

        implementation(platform("com.google.firebase:firebase-bom:34.6.0"))
        implementation("com.google.firebase:firebase-auth")
        implementation("com.google.firebase:firebase-analytics")

        implementation("com.google.firebase:firebase-firestore:24.10.0")
        // Thư viện Glide (để load ảnh từ URL)
        implementation("com.github.bumptech.glide:glide:4.16.0")
    }
