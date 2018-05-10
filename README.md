
[![](https://jitpack.io/v/mobfox/MobFox-Android-SDK-Core.svg)](https://jitpack.io/#mobfox/MobFox-Android-SDK-Core)

# MobFox-Android-SDK

For any problems or questions not covered by the instructions below, please contact <sdk_support@mobfox.com> or open an issue.

Supports Android OS 4.1.x (Jelly Bean) and up.

<!-- toc -->

* [Prerequisites](#prerequisites)
* [Installation](#installation)
  * [Gradle Installation](#option-1-gradle-installation)
  * [Jar Installation](#option-2-jar-installation)
  * [Smart Banner](#smart-banner)
  * [Location](#location)
* [DemoApp](#demoapp)
* [Usage](#usage)
  * [Banner Ad](#banner)
  * [Interstitial Ad](#interstitial)
  * [Native Ad](#native-ad)
  * [Custom Events](#custom-events)
  * [Adapters](#adapters)
  * [Plugins](#plugins)
* [MOAT](#built-in-moat-viewability-measurement)

<!-- toc stop -->

# Prerequisites

You will need a [MobFox](http://www.mobfox.com/) account.

# Installation

## Option 1: Gradle Installation

MobFox SDK can easily be added as a dependency from JitPack package repository.

Add ```JitPack``` to your repositories, if missing.
In your build.gradle:

```groovy
allprojects {
    repositories {
        //... other repositories ...
        maven { url "https://jitpack.io" }
    }
}
```

Next, add ```Google Play Services``` and ```MobFox-Android-SDK-Core``` to your compile dependencies:

```groovy
dependencies {
    //... other dependencies ...
    compile 'com.google.android.gms:play-services-ads:+'
    compile 'com.github.mobfox.MobFox-Android-SDK-Core:MobFox-Android-SDK-Core:3.4.2'
}
```
**Note that we are using android ```compileSdkVersion 25```  and ```buildToolsVersion 25.0.3```.**

Done.

## Option 2: Jar Installation

Download and unzip [MobFox-Android-SDK](https://github.com/mobfox/MobFox-Android-SDK/releases/latest) or clone this repository and extract the ```MobFox-Android-SDK-Core-latest.jar``` and put it in your project under the ``libs`` directory.

Next, In your ```gradle.build``` add the following dependencies:

```groovy

repositories {
    //... other repositories ...
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.google.android.gms:play-services-ads:+'
    compile 'com.github.mobfox:AndroidVideoCache:v2.7.1'
    compile files('libs/MobFox-Android-SDK-Core-3.4.1.jar')
}
```

### Add moat integration:

Download and add [MAT-moat-mobile-app-kit.aar](https://github.com/mobfox/MobFox-Android-SDK/blob/master/MAT-moat-mobile-app-kit.aar) to libs directory.

Next, In your ```gradle.build``` add the following repositories and dependencies:

```groovy
allprojects {
    repositories {
        //... other repositories ...
        flatDir {
            dirs 'libs'
        }
    }
}

dependencies {
    //... other dependencies ...
    compile(name:'MAT-moat-mobile-app-kit', ext:'aar')
}
```

## Setting permissions and adding activity (for both installation options)

In your project's ```AndroidManifest.xml``` under the ```manifest``` tag, add the following permissions:
```xml
    
    <uses-permission android:name="android.permission.INTERNET">
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    
    ...
    <application
    ...
    <!--mobfox interstitial activity-->
    <activity android:name="com.mobfox.sdk.interstitial.InterstitialActivity" android:hardwareAccelerated="true"  android:theme="@android:style/Theme.NoTitleBar.Fullscreen"/>
    <service android:name="com.mobfox.sdk.services.MobFoxService" android:launchMode="singleTop" />
    ...
    </application>
    

```

## Proguard
```proguard
-keep class com.mobfox.** { *; }
-keep class com.mobfox.adapter.** {*;}
-keep class com.mobfox.sdk.** {*;}
```

# Demoapp
Find Demoapp source [here](https://github.com/mobfox/MobFox-Android-SDK/tree/master/demoapp).

# Usage

## Banner

Add to your activity's layout xml:
```xml

<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:mobfox="http://schemas.android.com/apk/lib/com.mobfox.sdk"
    android:orientation="vertical" 
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.mobfox.sdk.banner.Banner
        android:layout_height="50dp"
        android:layout_width="320dp"
        android:id="@+id/banner"
        mobfox:inventory="fe96717d9875b9da4339ea5367eff1ec"
        android:layout_centerVertical="true"
        android:layout_centerHorizontal="true">
    </com.mobfox.sdk.banner.Banner>

</RelativeLayout>
```

```
It's advised to select popular ```layout_width```/```layout_height``` combinations so you'll get a good fill rate.
Popular sizes are: 320x50, 300x250, 320x480

In your activity set up the banner:

```java
// ...

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mobfox.sdk.banner.Banner;

/**
 * Created by nabriski on 10/05/2018.
 */

public class InlineBannerActivity extends Activity {

    Banner banner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.banner_inline);
        banner = (Banner) findViewById(R.id.banner);
        banner.setListener(new Banner.Listener() {
            @Override
            public void onBannerError(Banner banner, Exception e) {

            }

            @Override
            public void onBannerLoaded(Banner banner) {

            }

            @Override
            public void onBannerClosed(Banner banner) {

            }

            @Override
            public void onBannerFinished() {

            }

            @Override
            public void onBannerClicked(Banner banner) {

            }

            @Override
            public void onNoFill(Banner banner) {

            }
        });
        banner.load();
    }

    //add this so video ads will work properly
    @Override
    protected void onPause() {
        super.onPause();
        banner.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        banner.onResume();
    }
}


```

### Refresh
Enable refresh by calling the method ```banner.setRefresh(10)```
- Integer Units in seconds
- Minimum is 10 seconds

## Interstitial

In your activity set up the interstitial ad:

``` java
//...

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.mobfox.sdk.interstitial.Interstitial;
import com.mobfox.sdk.interstitial.InterstitialListener;

public class UseInterstitialAd extends Activity {

    private static String invh = "267d72ac3f77a3f447b32cf7ebf20673";
    Interstitial inter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_inline);

        final LinearLayout layout = (LinearLayout) findViewById(R.id.dummy_container);

        final UseInterstitialAd self = this;

                inter = new Interstitial(self,invh);
                inter.setListener(new InterstitialListener() {
                    @Override
                    public void onInterstitialLoaded(Interstitial interstitial) {
                        inter.show();
                    }

                    @Override
                    public void onInterstitialFailed(String e) {

                    }

                    @Override
                    public void onInterstitialClosed() {
                    }

                    @Override
                    public void onInterstitialClicked() {
                    }

                    @Override
                    public void onInterstitialShown() {
                    }

                    @Override
                    public void onInterstitialFinished() {
                    }
                });
               inter.load();

    }

}


```

## Native Ad
the layout below is used to show native assets:

```xml

// ...

<RelativeLayout
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:id="@+id/nativeLayout"
    android:layout_below="@+id/spinnerInvh">
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/headline"/>
        <ImageView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:adjustViewBounds="true"
            android:maxWidth="300dp"
            android:maxHeight="300dp"
            android:scaleType="fitCenter"
            android:id="@+id/nativeIcon"
            android:layout_below="@+id/headline"/>
        <ImageView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:adjustViewBounds="true"
            android:maxHeight="300dp"
            android:scaleType="fitCenter"
            android:id="@+id/nativeMainImg"
            android:layout_below="@+id/nativeIcon"/>
</RelativeLayout>
 
// ...
 
```

In your activity set up the native ad:
``` java

// ...

import com.mobfox.sdk.nativeads.Native;
import com.mobfox.sdk.nativeads.NativeAd;
import com.mobfox.sdk.nativeads.NativeListener;
import com.mobfox.sdk.customevents.CustomEventNative;

// ...

    private Native aNative;
    private Activity self;

    private NativeListener listener;
    
    //creating variables for our layout
    TextView headline;
    ImageView nativeIcon, nativeMainImg;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.our_layout);

        self = this;

        //assigning xml components to our layout
        headline = (TextView) findViewById(R.id.headline);
        nativeIcon = (ImageView) findViewById(R.id.nativeIcon);
        nativeMainImg = (ImageView) findViewById(R.id.nativeMainImg);
        layout = (RelativeLayout) findViewById(R.id.nativeLayout);

        aNative = new Native(this);

        //we must set a listener for native

        listener = new NativeListener() {
            @Override
            public void onNativeReady(Native aNative, CustomEventNative event, NativeAd ad) {

                Toast.makeText(self, "on native ready", Toast.LENGTH_SHORT).show();

                //register custom layout click
                event.registerViewForInteraction(layout);
                //fire trackers
                ad.fireTrackers(self);

                headline.setText(ad.getTexts().get(0).getText());

                ad.loadImages(self, new NativeAd.ImagesLoadedListener() {
                    @Override
                    public void onImagesLoaded(NativeAd ad) {
                        Toast.makeText(self, "on images ready", Toast.LENGTH_SHORT).show();
                        nativeIcon.setImageBitmap(ad.getImages().get(0).getImg());
                    }
                });

            }

            @Override
            public void onNativeError(Exception e) {
                Toast.makeText(self, "on native error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNativeClick(NativeAd ad) {
                Toast.makeText(self, "on native click", Toast.LENGTH_SHORT).show();
            }
            
        };

        aNative.setListener(listener);

        //load our native
        aNative.load("<your-publication-hash>");
    }

// ...

```

The ```NativeAd``` object returned by the native listener contains the ad data used to construct the native ad:

```java
public class NativeAd {
    ...
    public List<Tracker> getTrackerList() {
        return trackerList;
    }
    public List<ImageItem> getImages() {
        return images;
    }
    public List<TextItem> getTexts() {
        return texts;
    }
    public String getLink() {
        return link;
    }
    ...
}
public class TextItem {
    ...
    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }
    ...
}
public class ImageItem {
    ...
    public String getType() {
        return type;
    }
    public String getUrl() {
        return url;
    }
    public int getH() {
        return h;
    }
    public int getW() {
        return w;
    }
    public Bitmap getImg() {
        return img;
    }
    ...
}
```

The ```List<Tracker>``` returned by ```getTrackerList``` contains tracker url's you must call before displaying the ad by calling the ```NativeAd.fireTrackers``` method :
```java
public class Tracker {

    public String getType();

    //you must call this url!
    public String getUrl();

}
```

## Test Banner

You can test your implementations with these [test inventory hashes](https://docs.mobfox.com/docs/inventory-hashes-for-testing)

## Custom Events

[Custom Events](https://github.com/mobfox/MobFox-Android-SDK-Core-Lib/wiki/Custom-Events) let you use your accounts on other advertising platforms such as MoPub inside MobFox's SDK.

## Adapters

[Adapters](https://github.com/mobfox/MobFox-Android-SDK-Core-Lib/wiki/Adapters) are the opposite of Custom Events, they let you use MobFox as a Custom Event in other networks.

## Plugins

Plugins can be found in [SDK-plugins](https://github.com/mobfox/SDK-Plugins) repository.

# Built-in MOAT Viewability Measurement
 
This enables publishers to measure their in-app inventory according to [Moat](https://moat.com/)’s viewability metrics, and make their inventory more available to advertisers who are only interested in ‘viewability-monitored’ traffic.

