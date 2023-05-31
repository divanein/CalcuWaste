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

### Machine Learning Implementation
The ML path part of this app is categorizing uploaded images of recyclables into specific emission factors. The emission factor is how much carbon footprint is emitted per specific mass, which is written as CO2eq/kg. This parameter differs between each recyclable category, therefore the ML part is to categorize the multiclass classification for the image. The dataset for this implementation already trained and able tp put in there [Google Drive for Dataset](https://drive.google.com/drive/folders/1wsRu9LGo_YwNcVRxo3rc49i-cNNuURxt?usp=share_link).

### Cloud Computing Implementation

### Mobile Development Implementation
The Mobile Development path in this application is to accommodate the results of the Cloud Computing and Machine Learning paths, and create layouts and logic so that the application can run. The features created on the Mobile Development path are the splash screen feature, authentication feature, profile feature, taking pictures from the camera, sending images to the server to detect carbon emissions, and history feature.

Dependecies :
```
implementation 'androidx.core:core-ktx:1.10.1'
implementation 'androidx.appcompat:appcompat:1.6.1'
implementation 'com.google.android.material:material:1.9.0'
implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
testImplementation 'junit:junit:4.13.2'
androidTestImplementation 'androidx.test.ext:junit:1.1.5'
androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
implementation "com.airbnb.android:lottie:5.0.3"
implementation 'com.github.bumptech.glide:glide:4.15.1'
implementation 'androidx.cardview:cardview:1.0.0'

implementation 'androidx.navigation:navigation-fragment-ktx:2.5.3'
implementation 'androidx.navigation:navigation-ui-ktx:2.5.3'
def camerax_version = "1.2.2"
implementation "androidx.camera:camera-camera2:1.2.3"
implementation "androidx.camera:camera-lifecycle:1.2.3"
implementation "androidx.camera:camera-view:1.2.3"
```


