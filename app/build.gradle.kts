plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.myapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapp"
        minSdk = 30
        targetSdk = 34
        versionCode = 1000
        versionName = "1.0.00"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //implementation 'com.orhanobut:logger:2.1.1'
    //格式    implementation （“组”，“名字”，“版本号”）
    implementation("com.orhanobut","logger","2.1.1")

    implementation("com.squareup.okhttp3","okhttp","4.9.1")

    implementation("io.reactivex.rxjava2","rxandroid","2.0.1")
    implementation("io.reactivex.rxjava2","rxjava","2.0.7")

    implementation("com.squareup.retrofit2","retrofit","2.9.0")
    implementation("com.squareup.retrofit2","converter-gson","2.9.0")

    implementation("com.yayaG.iosSwitchButton","iosswitchbutton","1.0.3")

}