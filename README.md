# MobFox-Android-SDK-Core-Lib

Supports Android OS 4.1.x (Jelly Bean) and up.

#Prerequisites

You will need a [MobFox](http://www.mobfox.com/) account.

# Installation

Download and unzip [MobFox-Android-SDK-Core-Lib](https://github.com/mobfox/MobFox-Android-SDK-Core-Lib/releases/latest) or clone this repository and extract the ```MobFox-Android-SDK-Core.jar``` and put it in your project under the ``libs`` directory.


In your project's ```AndroidManifest.xml``` under the ```manifest``` tag, add the following permissions:
```xml
    
    <uses-permission android:name="android.permission.INTERNET"></uses-permission>
    <uses-permission android:name="android.permission.READ_PHONE_STATE"></uses-permission>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"></uses-permission>

```

In your ```gradle.build``` add the following dependencies:

```groovy
dependencies {
    compile 'com.google.android.gms:play-services:+'
    compile files('libs/MobFox-Android-SDK-Core.jar')
}
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
        public void onBannerFinished() {
            Toast.makeText(self, "banner finished", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBannerClicked(View view) {
            Toast.makeText(self, "banner clicked", Toast.LENGTH_SHORT).show();
        }

        //do not write code here to not disturb custom events
        @Override
        public boolean onCustomEvent(JSONArray jsonArray) {
            return false;
        }
    });

    banner.load("<your-publication-hash>");
}

//need to add this so video ads will work properly
@Override
protected void onPause() {
    super.onPause();
    banner.onPause();
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
    };

    interstitial.setListener(listener);
    interstitial.load("<your-publication-hash>");
}

//need to add this so video ads will work properly
@Override
protected void onPause() {
    super.onPause();
    interstitial.onPause();
}

// ...
```

## Native Ad
In your activity set up the native ad:
``` java

// ...

import com.mobfox.sdk.Native;
import com.mobfox.sdk.NativeListener;

// ...

    private Native aNative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NativeListener listener = new NativeListener() {
            @Override
            public void onNativeReady(MobFoxNativeObject mobFoxNativeObject) {
                //native object ready
                //displaying object parameter e.g. click_url
                Toast.makeText(MyNative.this, "click_url" + mobFoxNativeObject.getClick_url(), Toast.LENGTH_SHORT).show();
            }
        };

        aNative = new Native(this);
        
        aNative.setListener(listener);

        aNative.load("<your-publication-hash>");
    }

// ...

```

The ```MobFoxNativeObject``` object returned by the native listener contains the ad data used to construct the native ad:
```java
public class MobFoxNativeObject {

    public String getIcon_url() {
        //...
    }

    
    public int getIcon_width() {
        //...
    }


    public int getIcon_height() {
        //...
    }

    public Bitmap getIcon() {
        //...
    }

    public String getMain_url() {
        //...
    }

    public int getMain_width() {
        //...
    }

    public int getMain_height() {
        //...
    }

    public Bitmap getMain() {
        //...
    }

    public String getText_headline() {
        //...
    }

    public String getText_description() {
        //...
    }

    public String getText_cta() {
        //...
    }

    public String getText_rating() {
        //...
    }

    public String getText_advertiser() {
        //...
    }

    public List<Tracker> getTrackerList() {
        //...
    }

    public String getClick_url() {
        //...
    }
}
```

The ```List<Tracker>``` returned by ```getTrackerList``` contains tracker url's you must call before displaying the ad:
```java
public class Tracker {

    public String getType() {
        //..
    }

    //you must call this!
    public String getUrl() {
        //..
    }

}
```

## Custom Events

This feature lets you use your accounts on other advertising platforms such as MoPub inside MobFox's SDK.

[Custom Events](https://github.com/mobfox/MobFox-Android-SDK-Core-Lib/wiki/Custom-Events)

## Adapters

Adapters are the opposite of Custom Events, they let you use MobFox as a Custom Event in other networks.

[Adapters](https://github.com/mobfox/MobFox-Android-SDK-Core-Lib/wiki/Adapters)
