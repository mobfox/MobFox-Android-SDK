# MobFox-Android-SDK-Core-Lib

Supports Android OS 4.1.x (Jelly Bean) and up.


<!-- toc -->

* [Prerequisites](#prerequisites)
* [Installation](#installation)
  * [Gradle Installation](#option-1-gradle-installation)
  * [Jar Installation](#option-2-jar-installation)
* [Usage](#usage)
  * [Banner Ad](#banner)
  * [Interstitial Ad](#interstitial)
  * [Interstitial Ad Activity](#interstitial-activity)
  * [Native Ad](#native-ad)
  * [Custom Events](#custom-events)
  * [Adapters](#adapters)

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

Next, add ```Google Play Services``` and ```MobFox-Android-Core``` to your compile dependencies:

```groovy
dependencies {
    //... other dependencies ...
    compile 'com.google.android.gms:play-services-ads:+'
    compile 'com.github.mobfox:MobFox-Android-SDK-Core:1.5.6'
}
```

Done.

## Option 2: Jar Installation

Download and unzip [MobFox-Android-SDK-Core-Lib](https://github.com/mobfox/MobFox-Android-SDK-Core-Lib/releases/latest) or clone this repository and extract the ```MobFox-Android-SDK-Core.jar``` and put it in your project under the ``libs`` directory.

Next, In your ```gradle.build``` add the following dependencies:

```groovy
dependencies {
    compile 'com.google.android.gms:play-services:+'
    compile files('libs/MobFox-Android-SDK-Core-1.+.jar')
}
```

In your project's ```AndroidManifest.xml``` under the ```manifest``` tag, add the following permissions:
```xml
    
    <uses-permission android:name="android.permission.INTERNET"></uses-permission>
    <uses-permission android:name="android.permission.READ_PHONE_STATE"></uses-permission>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"></uses-permission>

```

# Usage

## Banner

Add to your activity's layout xml:
```xml

<com.mobfox.sdk.Banner
        android:layout_width="300dp"
        android:layout_height="250dp"
        android:id="@+id/banner"
        android:layout_centerHorizontal="true"
        android:layout_centerVertical="true">
</com.mobfox.sdk.Banner>
```
It's advised to select popular ```layout_width```/```layout_height``` combinations so you'll get a good fill rate.
Popular sizes are: 320x50, 300x250, 320x480

In your activity set up the banner:

```java
// ...

import com.mobfox.sdk.Banner;
import com.mobfox.sdk.BannerListener;

// ...

private Banner banner;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    banner = (Banner) findViewById(R.id.banner);

    final Activity self = this;
    
    banner.setListener(new BannerListener() {
        @Override
        public void onBannerError(View view, Exception e) {

            Toast.makeText(self, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBannerLoaded(View view) {

            Toast.makeText(self, "banner loaded", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBannerClosed(View view) {

            Toast.makeText(self, "banner closed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBannerFinished(View view) {

            Toast.makeText(self, "banner finished", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBannerClicked(View view) {

            Toast.makeText(self, "banner clicked", Toast.LENGTH_SHORT).show();
        }

        //do not write code here to not disturb custom events
        @Override
        public boolean onCustomEvent(JSONArray jsonArray, JSONObject jsonObject) {

            return false;
        }
    });
    
    //don't forget to set the inventory hash before loading
    banner.setInventoryHash("<your-publication-hash>");

    banner.load();
}

//need to add this so video ads will work properly
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

@Override
protected void onDestroy() {
    super.onDestroy();
    banner.onDestroy();
}

// ...

```

## Interstitial

In your activity set up the interstitial:

``` java

// ...

import com.mobfox.sdk.Interstitial;
import com.mobfox.sdk.InterstitialListener;

// ...

private Interstitial interstitial;

// ...

@Override
protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_main);

    interstitial = new Interstitial(this);

    final Activity self = this;
    
    InterstitialListener listener = new InterstitialListener() {
        @Override
        public void onInterstitialLoaded(Interstitial interstitial) {
            Toast.makeText(self, "interstitial ready", Toast.LENGTH_SHORT).show();

            //call show to display the interstitial when it finishes loading
            interstitial.show();
        }

        @Override
        public void onInterstitialFailed(Interstitial interstitial, Exception e) {
            Toast.makeText(self, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onInterstitialClosed(Interstitial interstitial) {
            Toast.makeText(self, "interstitial closed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onInterstitialFinished() {
            Toast.makeText(self, "interstitial finished", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onInterstitialClicked(Interstitial interstitial) {
            Toast.makeText(self, "interstitial clicked", Toast.LENGTH_SHORT).show();
        }
        
        @Override
        public void onInterstitialShown(Interstitial interstitial) {
            Toast.makeText(self, "interstitial shown", Toast.LENGTH_SHORT).show();
        }
    };

    interstitial.setListener(listener);
    
    interstitial.setInventoryHash("<your-publication-hash>");
    
    interstitial.load();
}

//need to add this so video ads will work properly
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

@Override
protected void onDestroy() {
    super.onDestroy();
    interstitial.onDestroy();
}

// ...
```

## Interstitial Activity

It's possible to display interstitial ads in their own separate activity.

In your activity set up the interstitialAd interstitial:

``` java

// ...

import com.mobfox.sdk.interstitialads.InterstitialAd;
import com.mobfox.sdk.interstitialads.InterstitialAdListener;

// ...

private InterstitialAd interstitial;

// ...

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    interstitial = new InterstitialAd(this);

    final Activity self = this;
    
    InterstitialAdListener listener = new InterstitialAdListener() {
        @Override
        public void onInterstitialLoaded(InterstitialAd interstitial) {
            Toast.makeText(self, "interstitial ready", Toast.LENGTH_SHORT).show();

            //call show to display the interstitial when it finishes loading
            interstitial.show();
        }

        @Override
        public void onInterstitialFailed(InterstitialAd interstitial, Exception e) {
            Toast.makeText(self, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onInterstitialClosed(InterstitialAd interstitial) {
            Toast.makeText(self, "interstitial closed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onInterstitialFinished() {
            Toast.makeText(self, "interstitial finished", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onInterstitialClicked(InterstitialAd interstitial) {
            Toast.makeText(self, "interstitial clicked", Toast.LENGTH_SHORT).show();
        }
        
        @Override
        public void onInterstitialShown(InterstitialAd interstitial) {
            Toast.makeText(self, "interstitial shown", Toast.LENGTH_SHORT).show();
        }
    };

    interstitial.setListener(listener);
    
    interstitial.setInventoryHash("<your-publication-hash>");
    
    interstitial.load();
}

//need to add this so video ads will work properly
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

@Override
protected void onDestroy() {
    super.onDestroy();
    interstitial.onDestroy();
}

// ...
```

In your project's ```AndroidManifest.xml``` under the ```application``` tag, declare the following activity:
```xml
    
    <activity android:name="com.mobfox.sdk.interstitialads.InterstitialActivity"></activity>

```

## Native Ad
We will use the layout below to show our native assets:

```xml

// ...

<RelativeLayout
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:id="@+id/nativeLayout">

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

                ad.fireTrackers(self);

                headline.setText(ad.getHeadline());

                ad.loadImages(self, new NativeAd.ImagesLoadedListener() {
                    @Override
                    public void onImagesLoaded(NativeAd ad) {

                        Toast.makeText(self, "on images ready", Toast.LENGTH_SHORT).show();

                        nativeIcon.setImageBitmap(ad.getMain());
                        nativeMainImg.setImageBitmap(ad.getIcon());

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
public class MobFoxNativeObject {

    public String getIconUrl();
    
    public int getIconWidth();

    public int getIconHeight();
    
    //this will be null until loadImages is called
    public Bitmap getIcon();
    
    public String getMainUrl();

    public int getMainWidth()

    public int getMainHeight();

    //this will be null until loadImages is called
    public Bitmap getMain();

    public String getHeadline();

    public String getDescription();

    public String getCta();

    public String getRating();

    public String getAdvertiser();

    public List<Tracker> getTrackerList();

    public String getClickUrl();
    
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

You can test your implementations with these [test inventory hashes](http://dev.mobfox.com/index.php?title=Test_Inventory_Hashes)

## Custom Events

This feature lets you use your accounts on other advertising platforms such as MoPub inside MobFox's SDK.

[Custom Events](https://github.com/mobfox/MobFox-Android-SDK-Core-Lib/wiki/Custom-Events)

## Adapters

Adapters are the opposite of Custom Events, they let you use MobFox as a Custom Event in other networks.

[Adapters](https://github.com/mobfox/MobFox-Android-SDK-Core-Lib/wiki/Adapters)

# Min Versions

Minimal lighter versions of the SDK core can be found [Here](https://github.com/mobfox/MobFox-Android-SDK-Core-Lib/wiki/Minimal-Versions)
