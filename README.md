# MobFox-Android-SDK-Core-Lib

Supports Android OS 4.1.x (Jelly Bean) and up.

#Prerequisites

You will need a [MobFox](http://www.mobfox.com/) account.

# Installation

# Usage

## Banner

Add to your ```AndroidManifest.xml``` or one of your layout xmls:
```xml

<com.mobfox.sdk.Banner
        android:layout_width="300dp"
        android:layout_height="250dp"
        android:id="@+id/banner"
        android:layout_centerHorizontal="true"
        android:layout_centerVertical="true">
</com.mobfox.sdk.Banner>
```

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
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.banner);

    banner = (Banner) findViewById(R.id.banner);

    final Activity self = this;
    banner.setListener(new BannerListener() {
        @Override
        public void onBannerError(String error) {
            Toast.makeText(self, error, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBannerLoaded(Banner banner) {
            Toast.makeText(self, "banner loaded", Toast.LENGTH_SHORT).show();
        }

        public void onBannerClosed() {
            Toast.makeText(self, "banner closed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBannerFinished() {
            Toast.makeText(self, "banner finished", Toast.LENGTH_SHORT).show();
        } 

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

import com.mobfox.sdk.Interstitial;
import com.mobfox.sdk.InterstitialListener;

private Interstitial interstitial;

@Override
protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.interstitial);

    interstitial = new Interstitial(this);

    Activity self = this;
    InterstitialListener listener = new InterstitialListener() {
        @Override
        public void onInterstitialLoaded() {
            Toast.makeText(self, "ready", Toast.LENGTH_SHORT).show();
            //call show to disaply the interstitial when it finishes loading
            interstitial.show();
        }

        @Override
        public void onInterstitialFailed(String error) {
            Toast.makeText(self, "error: " + error, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onInterstitialClosed() {
            Toast.makeText(self, "closed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onInterstitialFinished() {
            Toast.makeText(self, "finished", Toast.LENGTH_SHORT).show();
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
```

## Native Ad
