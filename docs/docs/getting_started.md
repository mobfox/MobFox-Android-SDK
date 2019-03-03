---
id: getting_started
title: Install The MobFox Android SDK
---

The SDK supports Android OS 4.4.x / API 19 (KitKat) and later.

For users with lower versions the SDK will run normally but will not return any ads.


## Where to get support
For any problems or questions not covered by the instructions below, contact EMAIL: sdk_support@mobfox.com or open an issue.

To report bugs or technical issues with this SDK please do it on Github.


## Prerequisites
You will need a [MobFox](https://mobfox.com) account.


## AndroidManifest.xml

Add the following your app's AndroidManifest.xml:
```html,xml
<manifest 
    //... other manifest properties
    xmlns:tools="http://schemas.android.com/tools"
    //... other manifest properties
    >

    <!-- other manifest contents -->

    <uses-sdk tools:overrideLibrary="com.mobfox.sdk.all,sdk.mobfox.com.mobfoxandroidadaptermopub,sdk.mobfox.com.mobfoxandroidadapteradmob,com.mobfox.sdk,com.mobfox.sdk.interstitial,com.mobfox.sdk.nativeads" />
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
         
    <application>

        <!-- other application contents -->

        <activity android:name="com.mobfox.sdk.interstitial.InterstitialActivity" android:hardwareAccelerated="true"  android:theme="@android:style/Theme.NoTitleBar.Fullscreen"/>
        <service android:name="com.mobfox.sdk.services.MobFoxService" android:launchMode="singleTop" />
    </application>

</manifest>
```


## Top Level ```build.grade```
Add the ```jitpack``` repository to your top level ```build.gradle```.
```groovy
allprojects {
    repositories {
        //... other repositories ...
        maven { url "https://jitpack.io" }
    }
}
```

## Module Level ```build.grade```

### Google Play Services
Install [Google Play Services](https://developers.google.com/android/guides/setup) for ads (```com.google.android.gms:play-services-ads```), if you don't have it already.

### Include the MobFox SDK
Add to your module level ```build.gradle```:
```groovy
dependencies {
    //... other dependencies ...
    implementation 'com.github.mobfox.MobFox-Android-SDK-Core:MobFox-Android-SDK-Core:3.6.7'
}
```

Note: We are using android compileSdkVersion 25 and buildToolsVersion 25.0.3.


## Proguard
If using [Proguard](https://developer.android.com/studio/build/shrink-code) please add the following to your ```proguard-rules.pro``` or similar file:
```
-keep class com.mobfox. {*;}

-keep class com.mobfox.adapter. {*;}

-keep class com.mobfox.sdk. {*;}
```


