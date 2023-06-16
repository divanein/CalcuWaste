<p align="center">
  <img src=https://github.com/divanein/CalcuWaste/assets/100034010/94e63b53-c510-40b9-9753-7cf64bb85b9a/>
</p>

# Calcuwaste
CalcuWaste is a mobile application that enables the user to calculate the CO2 equivalent that can be saved by recycling, and its economic value, and connect them with the nearby waste bank to ensure a better experience in recycling.
Here is the feature of the CalcuWaste app:
| Main Features | Aditional Features |
|--|--|
|Calculate carbon emission reduction and economic value of the recyclable if recycled according to its category (plastic, metal, aluminum, etc.)|Connect the users with the recycling centers/waste banks to conduct transactions of the recyclable|
|Keep track of (accounts) reduced carbon emission and total economic value of each user account throughout the history of application usage|Accounts overall carbon emission of the equipment used by an individual or SMEs|
|Find nearest recycling centers/waste banks that accept the recyclable category|

## How This Application Works
How our app is used is relatively simple, the user only needs to log in and upload a recyclable image and its weight/size to show its carbon emission saving, economic value, nearby waste banks that can accommodate it, and connect them with the waste bank.

There is implementation from each of the learning path :

- Machine Learning Implementation
The ML path part of this app is categorizing uploaded images of recyclables into specific emission factors. The emission factor is how much carbon footprint is emitted per specific mass, which is written as CO2eq/kg. This parameter differs between each recyclable category, therefore the ML part is to categorize the multiclass classification for the image. The dataset for this implementation already trained and able to put in there [Google Drive for Dataset](https://drive.google.com/drive/folders/1wsRu9LGo_YwNcVRxo3rc49i-cNNuURxt?usp=share_link).

- Cloud Computing Implementation
The Cloud Computing learning path part of the app is to build APIs for authentication for the registration and login for the user. Also the application need expected to catch image capture and able to proceed by the model that already made by the Machine Learning path. From the photo captured, we provide the service to store the resources from photo scanning objects into storage as Cloud Storage. 

- Mobile Development Implementation
The Mobile Development path part of this app is to accommodate the results of the Cloud Computing and Machine Learning paths, and create layouts and logic using Kotlin so that the application can run. The features created on the Mobile Development path are the splash screen feature, authentication feature, profile feature, taking pictures from the camera and gallery, detecting category, calculation of carbon emission formula and estimated user profit, and news related to the environment.

<h1>Plugins:</h1>

    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    id 'kotlin-parcelize'
    id 'com.google.android.libraries.mapsplatform.secrets-gradle-plugin'

<h1>Dependecies:</h1>

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


