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
  * [Native Ad](#native-ad)
  * [Custom Events](#custom-events)
  * [Adapters](#adapters)

<!-- toc stop -->


# Prerequisites

You will need a [MobFox](http://www.mobfox.com/) account.

# Installation

## Option 1: Gradle Installation

Add ```jcenter``` to your repositories, if missing.
In your build.gradle:

```groovy
allprojects {
    repositories {
        jcenter()
    }
}
```

Next, add ```Google Play Services``` and ```MobFox-Android-Core``` to your compile dependencies:

```groovy
dependencies {
    //... other dependencies ...
    compile 'com.google.android.gms:play-services:+'
    compile 'com.mobfox.sdk:MobFox-Android-Core:1.+'
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
        public boolean onCustomEvent(JSONArray jsonArray) {
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

import com.mobfox.sdk.Native;
import com.mobfox.sdk.NativeListener;
import com.mobfox.sdk.MobFoxNativeObject;

// ...

    private Native aNative;
    private Activity self;

    private NativeListener listener;

    static String userAgent = System.getProperty("http.agent");
    
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
            public void onNativeReady(Native aNative, MobFoxNativeObject mobFoxNativeObject) {

                Toast.makeText(self, "on native ready", Toast.LENGTH_SHORT).show();

                //native object ready
                //first fire tracker urls for click tracking

                List<Tracker> trackers = mobFoxNativeObject.getTrackerList();

                for (int i = 0; i < trackers.size(); i++) {

                    Tracker tracker = trackers.get(i);

                    String trackerUrl = tracker.getUrl();

                    AsyncTask<String, Void, Void> fireTracker = new AsyncTask<String, Void, Void>() {

                        @Override
                        protected Void doInBackground(String... params) {

                            URL url;
                            HttpURLConnection con = null;

                            try {

                                url = new URL(params[0]);
                                con = (HttpURLConnection) url.openConnection();
                                
                                //you must set request user-agent for tracking to work
                                con.setRequestProperty("User-Agent", userAgent);
                                
                                int responseCode = con.getResponseCode();

                                if (responseCode == HttpURLConnection.HTTP_OK) {

                                    //tracker url fired!
                                } else if (responseCode == HttpURLConnection.HTTP_BAD_GATEWAY) {

                                    //tracker url bad gateway
                                }

                            } catch (Exception e) {

                                //check exception for error origin

                            } finally {

                                if (con != null) {

                                    con.disconnect();
                                }
                            }

                            return null;
                        }
                    };

                    if (trackerUrl != null) {

                        fireTracker.execute(trackerUrl);
                    } else {

                        continue;
                    }

                }
                
                //displaying object parameter e.g. headline

                String nativeHeadline = mobFoxNativeObject.getText_headline();

                headline.setText(nativeHeadline);

                //if we want to get the actual bitmap images
                //we will call the 'getIconFromUrl' to get the icon and 'getMainFromUrl' to get the main image
                //and pass our listener to notify us when ready

                mobFoxNativeObject.getIconFromURL(self, listener);
                mobFoxNativeObject.getMainFromURL(self, listener);

                //to make our layout clickable we will use the 'registerViewForInteraction'
                //and pass our view group e.g our relative layout

                aNative.registerViewForInteraction(layout);
            }

            @Override
            public void onNativeError(MobFoxNativeObject mobFoxNativeObject, Exception e) {

                Toast.makeText(self, "on native error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNativeClick(MobFoxNativeObject mobFoxNativeObject) {

                Toast.makeText(self, "on native click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNativeIcon(Bitmap bitmap) {

                Toast.makeText(self, "on native icon", Toast.LENGTH_SHORT).show();

                //icon ready
                //setting up ImageView

                nativeIcon.setImageBitmap(bitmap);
            }

            @Override
            public void onNativeMain(Bitmap bitmap) {

                Toast.makeText(self, "on native main", Toast.LENGTH_SHORT).show();

                //icon ready
                //setting up ImageView

                nativeMainImg.setImageBitmap(bitmap);
            }
        };

        aNative.setListener(listener);

        //load our native

        aNative.load("<your-publication-hash>");
    }

// ...

```

The ```MobFoxNativeObject``` object returned by the native listener contains the ad data used to construct the native ad:
```java
public class MobFoxNativeObject {

    public void getIconFromURL(NativeListener listener) {
        //...
    }
    
    public void getMainFromURL(NativeListener listener) {
        //...
    }

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
    
    public void getIconFromURL(Context context, NativeListener listener) {
        //returns Bitmap icon (in listener onNativeIcon method)
    }
    
    public void getMainFromURL(Context context, NativeListener listener) {
        //returns Bitmap main image (in listener onNativeMain method)
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
