plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.myapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1000
        versionName = "1.0.01"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val listSubFiles = {
        val  resFolder = "src/main/res/layout"
        val files = file(resFolder).listFiles()
        val folders = mutableListOf<String>()
        files.forEach { item -> folders.add(item.absolutePath) }
        folders.add(file(resFolder).parentFile.absolutePath)
        folders
    }

    sourceSets{
        getByName("main"){
            res.srcDirs(listSubFiles)
            res.srcDirs(

                "src/main/res/layout/layout",
                "src/main/res/dimens_",
                "src/main/res/layout",
                "src/main/res",

                "src/main/res/layout/res30/layout_05",
                "src/main/res/layout/res30/layout_04",
                "src/main/res/layout/res30/layout_03",
                "src/main/res/layout/res30/layout_02",
                "src/main/res/layout/res30/layout_01",
                "src/main/res/layout/res30/layout",
                "src/main/res/layout/res30",
            )
//            jniLibs.srcDirs(
//                "libs"
//            )
        }
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
    implementation(files("libs\\FitCSVTool.jar"))
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

    implementation("com.amap.api","3dmap","9.8.2")

    //implementation("com.amap.api","location","3.3.0")
    implementation("com.amap.api","search","5.0.0")
    //implementation("com.garmin","fit","21.107")

//    compile 'com.amap.api:3dmap:5.0.0'
//    compile 'com.amap.api:location:3.3.0'
//    compile 'com.amap.api:search:5.0.0'
}