# Mobile Development Implementation
The Mobile Development path part of this app is to accommodate the results of the Cloud Computing and Machine Learning paths, and create layouts and logic using Kotlin so that the application can run. The features created on the Mobile Development path are the splash screen feature, authentication feature, profile feature, taking pictures from the camera and gallery, detecting category, calculation of carbon emission formula and estimated user profit, and news related to the environment.

## Plugins:

    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    id 'kotlin-parcelize'
    id 'com.google.android.libraries.mapsplatform.secrets-gradle-plugin'

## Dependecies:

    implementation 'androidx.core:core-ktx:1.10.1'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.9.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'com.google.android.gms:play-services-maps:18.1.0'
    implementation 'org.tensorflow:tensorflow-lite-support:0.1.0'
    implementation 'org.tensorflow:tensorflow-lite-metadata:0.1.0'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'

    implementation "com.airbnb.android:lottie:5.0.3"
    implementation 'androidx.cardview:cardview:1.0.0'
    implementation 'com.github.bumptech.glide:glide:4.15.1'

    def camerax_version = "1.2.2"
    implementation "androidx.camera:camera-camera2:1.2.3"
    implementation "androidx.camera:camera-lifecycle:1.2.3"
    implementation "androidx.camera:camera-view:1.2.3"

    implementation 'androidx.navigation:navigation-fragment-ktx:2.6.0'
    implementation 'androidx.navigation:navigation-ui-ktx:2.6.0'

    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation "com.squareup.retrofit2:converter-gson:2.9.0"
    implementation "com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.5"

    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1"
    implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.6.1"
    implementation "androidx.activity:activity-ktx:1.7.2"
    implementation "androidx.fragment:fragment-ktx:1.6.0"
    implementation 'androidx.preference:preference-ktx:1.2.0'
    implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
    implementation "androidx.datastore:datastore-preferences:1.0.0"

    implementation 'org.tensorflow:tensorflow-lite-metadata:0.1.0'
    implementation 'org.tensorflow:tensorflow-lite-support:0.1.0'
