# MobFox-Android-SDK

Supports Android OS 4.1.x (Jelly Bean) and up.


<!-- toc -->

* [Prerequisites](#prerequisites)
* [Installation](#installation)
  * [Gradle Installation](#option-1-gradle-installation)
  * [Jar Installation](#option-2-jar-installation)
  * [Location](#location)
* [DemoApp](#demoapp)
* [Usage](#usage)
  * [Banner Ad](#banner)
  * [Interstitial Ad](#interstitial)
  * [Native Ad](#native-ad)
  * [Custom Events](#custom-events)
  * [Adapters](#adapters)
  * [Plugins](#plugins)

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
    compile 'com.github.mobfox:MobFox-Android-SDK-Core:3.1.9'
}
```

Done.

## Option 2: Jar Installation

Download and unzip [MobFox-Android-SDK](https://github.com/mobfox/MobFox-Android-SDK/releases/latest) or clone this repository and extract the ```MobFox-Android-SDK-Core-latest.jar``` and put it in your project under the ``libs`` directory.

Next, In your ```gradle.build``` add the following dependencies:

```groovy
dependencies {
    compile 'com.google.android.gms:play-services-ads:+'
    compile files('libs/MobFox-Android-SDK-Core-3.+.jar')
}
```

In your project's ```AndroidManifest.xml``` under the ```manifest``` tag, add the following permissions:
```xml
    
    <uses-permission android:name="android.permission.INTERNET"></uses-permission>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
    
    ...
    <application
    ...
    <!--mobfox interstitial activity-->
    <activity android:name="com.mobfox.sdk.interstitialads.InterstitialActivity"></activity>
    ...
    </application>
    

```

Or add this [jar file](https://github.com/mobfox/MobFox-Android-SDK-Core-Lib/blob/master/MobFox-Android-SDK-Core-3.0.0b.jar)
to your libs folder.

## Proguard
```proguard
-keep class com.mobfox.** { *; } 
-keep class com.mobfox.adapter.** {*;} 
-keep class com.mobfox.sdk.** {*;}
```

# Location
Sending the user's location will provide you with higher CPMs.

## Enable Location
### banner
In your layout xml, in the Banner tag enable location by adding the mobfox attribute ```xmlns:mobfox="http://schemas.android.com/apk/lib/com.mobfox.sdk"``` and enable location to true ```mobfox:enableLocation="true"```

Full code
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent" android:layout_height="match_parent"
    xmlns:mobfox="http://schemas.android.com/apk/lib/com.mobfox.sdk">
    <com.mobfox.sdk.bannerads.Banner
        android:layout_height="50dp"
        android:layout_width="320dp"
        mobfox:enableLocation="true"
        android:id="@+id/banner"
        android:layout_centerVertical="true"
        android:layout_centerHorizontal="true">
    </com.mobfox.sdk.bannerads.Banner>
</RelativeLayout>
```
This could also be achieved by calling the static method
```
Banner.setLoc(true);
```
### interstitial
Enable location by calling the static method ```InterstitialAd.getLocation(true);```
### native
Enable location by calling the static method ```Native.setLoc(true);```

# Demoapp
Find Demoapp source [here](https://github.com/mobfox/MobFox-Android-SDK/tree/master/demoapp),
apk can be found under [demoapp/release](https://github.com/mobfox/MobFox-Android-SDK/blob/master/demoapp/release), and via [demoapp-latest.apk](https://github.com/mobfox/MobFox-Android-SDK/blob/master/demoapp-latest.apk) in the project's root folder.

# Usage

## Banner

Add to your activity's layout xml:
```xml

<com.mobfox.sdk.bannerads.Banner
        android:layout_width="300dp"
        android:layout_height="250dp"
        android:id="@+id/banner"
        android:layout_centerHorizontal="true"
        android:layout_centerVertical="true">
</com.mobfox.sdk.bannerads.Banner>
```
It's advised to select popular ```layout_width```/```layout_height``` combinations so you'll get a good fill rate.
Popular sizes are: 320x50, 300x250, 320x480

In your activity set up the banner:

```java
// ...

import com.mobfox.sdk.bannerads.Banner;
import com.mobfox.sdk.bannerads.BannerListener;

// ...

Banner banner;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    banner = (Banner) findViewById(R.id.banner);
    
    final Activity self = this;
    banner.setListener(new BannerListener() {
        @Override
        public void onBannerError(View banner, Exception e) {
            Toast.makeText(self, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onBannerLoaded(View banner) {
            Toast.makeText(self, "loaded", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onBannerClosed(View banner) {
            Toast.makeText(self, "closed", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onBannerFinished() {
            Toast.makeText(self, "finished", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onBannerClicked(View banner) {
            Toast.makeText(self, "clicked", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onNoFill(View banner) {
            Toast.makeText(self, "no fill", Toast.LENGTH_SHORT).show();
        }
    });
    banner.setInventoryHash("<your-publication-hash>");
    banner.load();
}

//permission dialog for marshmello and above
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    banner.onRequestPermissionsResult(requestCode, permissions, grantResults);
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

// ...

```

## Interstitial

In your activity set up the interstitialAd interstitial:

``` java

// ...

import com.mobfox.sdk.interstitialads.InterstitialAd;
import com.mobfox.sdk.interstitialads.InterstitialAdListener;

// ...

InterstitialAd interstitial;
InterstitialAdListener listener;

// ...

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    interstitial = new InterstitialAd(this);

    final Activity self = this;
    InterstitialAdListener listener = new InterstitialAdListener() {
        @Override
        public void onInterstitialLoaded(InterstitialAd interstitial) {
            Toast.makeText(self, "loaded", Toast.LENGTH_SHORT).show();
            //call show() to display the interstitial when its finished loading
            interstitial.show();
        }
        @Override
        public void onInterstitialFailed(InterstitialAd interstitial, Exception e) {
            Toast.makeText(self, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onInterstitialClosed(InterstitialAd interstitial) {
            Toast.makeText(self, "closed", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onInterstitialFinished() {
            Toast.makeText(self, "finished", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onInterstitialClicked(InterstitialAd interstitial) {
            Toast.makeText(self, "clicked", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onInterstitialShown(InterstitialAd interstitial) {
            Toast.makeText(self, "shown", Toast.LENGTH_SHORT).show();
        }
    };
    interstitial.setListener(listener);
    interstitial.setInventoryHash("<your-publication-hash>");
    interstitial.load();
}

//permission dialog for marshmello and above
@Override
public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    interstitial.onRequestPermissionsResult(requestCode, permissions, grantResults);
}

//add this so video ads will work properly
@Override
protected void onPause() {
    super.onPause();
    interstitial.onPause();
}

@Override
protected void onResume() {
    super.onResume();
    interstitial.onResume();
}

// ...
```

In your project's ```AndroidManifest.xml``` under the ```application``` tag, declare the following activity:
```xml
    
    <activity android:name="com.mobfox.sdk.interstitialads.InterstitialActivity"></activity>

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

# Beta version

MobFox SDK beta released! download it [here](https://github.com/mobfox/MobFox-Android-SDK/blob/master/beta/MobFox-Android-SDK-Core-beta.jar).
