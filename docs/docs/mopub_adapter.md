---
id: mopub_adapter
title: MoPub Adapter
---

First, [install the MobFox Android SDK](getting_started.md)

## Module Level ```build.grade```

Add to your module level ```build.gradle```:
```groovy
dependencies {
    //... other dependencies ...
    implementation 'com.github.mobfox.MobFox-Android-SDK-Core:MobFox-Android-SDK-MoPub-Adapter:3.6.7@aar'
}
```

## Set Up In The MoPub Dashboard


### Step 1 - Create Network
1. Click on the [Networks](https://app.mopub.com/networks) tab.
1. Select the ‘New Network’ button on the top right.
1. Scroll all the way down and choose ‘Custom SDK Network’.
1. Enter network name, geo-targeting and keyword targeting and select ‘next’.
1. Enter your CPM preferences.

### Step 2 - App & ad unit setup
1. Enter the appropriate **Custom Event Class Name** from the table below in the corresponding field.
1. Enter the appropriate **Custom Event Class Data** from the table below in the corresponding field.

| Ad Type        | Class  | Data  |
| ------------- |-------------| -----|
| Banner | ```com.mobfox.sdk.adapters.MoPubBannerAdapter``` | ```{"invh":"<your-mobfox-inventory-hash>"}```|
| Interstitial  | ```com.mobfox.sdk.adapters.MoPubInterstitialAdapter```  |```{"invh":"<your-mobfox-inventory-hash>"}```  |
| Rewarded Video  | ```com.mobfox.sdk.adapters.MoPubRewardedAdapter```  |```{"invh":"<your-mobfox-inventory-hash>"}```  |
| Native | ```com.mobfox.sdk.adapters.MoPubNativeAdAdapter``` | ```{"invh":"<your-mobfox-inventory-hash>"}```|


### Step 3 - Click 'Save & Close'.

Now, when calling your MoPub ads, you should get the respective Mobfox ads.





