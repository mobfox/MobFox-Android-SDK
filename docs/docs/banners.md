---
id: banners
title: Banner Ads
---

There are two methods to create a MobFox Banner instance:

- [Create the banner using an XML layout](#using-xml-layout)
- [Create the banner programmatically](#programmatically)


## Using XML Layout

Add to your activity's ```layout.xml```:

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    
    // this is the important part
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

- ```mobfox:inventory``` - is your MobFox inventory hash.

### Tip

Select popular layout_width / layout_height combinations so that you get a good fill rate.

Popular sizes are: 320x50, 300x250, 320x480

### Use It In Your Activity

```java
import com.mobfox.sdk.banner.Banner;

//only needed to run this example
import android.widget.Toast;
import android.content.Context;
 
public class InlineBannerActivity extends Activity {
 
    Banner banner;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        setContentView(R.layout.<YOUR_ACTIVITY_LAYOUT>);
        banner = findViewById(R.id.banner);
        
        final Context context = this.getApplicationContext(); 

        banner.setListener(new Banner.Listener() {

            @Override
            public void onBannerLoaded(Banner banner) {
                Toast.makeText(context, "ad loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerError(Banner banner, Exception e) {
                Toast.makeText(context, "error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
 
 
            @Override
            public void onBannerClosed(Banner banner) {
 
            }
 
            @Override
            public void onBannerFinished() {
 
            }
 
            @Override
            public void onBannerClicked(Banner banner) {
                Toast.makeText(context, "ad clicked", Toast.LENGTH_SHORT).show();
            }
 
            @Override
            public void onNoFill(Banner banner) {
 
            }
        });

        banner.setRefresh(30);//refresh every 30 seconds
        banner.load();
    }
 
    //add this so video ads will work properly
    @Override
    protected void onPause() {
        super.onPause();
        banner.onPause();
    }
 
    //add this so video ads will work properly
    @Override
    protected void onResume() {
        super.onResume();
        banner.onResume();
    }
}
 
```

## Programmatically

In your activity create and load the banner:
```java

import com.mobfox.sdk.banner.Banner;


//only needed to run this example
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.content.Context;
 
public class ProgrammaticBannerActivity extends AppCompatActivity {
 
    Banner banner;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
 
 
        super.onCreate(savedInstanceState);
        final Context context = getApplicationContext();
        String inventoryHash = "fe96717d9875b9da4339ea5367eff1ec"; //test inventory hash for banner
        setContentView(R.layout.<YOUR_ACTIVITY_LAYOUT>);
        LinearLayout view = findViewById(R.id.your_layout);
        //Default banner sizes:
        int bannerWidth = 320;
        int bannerHeight = 50;
 
        banner = new Banner(context, bannerWidth, bannerHeight, inventoryHash, new Banner.Listener() {
            @Override
            public void onBannerLoaded(Banner banner) {
                Toast.makeText(context, "ad loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerError(Banner banner, Exception e) {
                Toast.makeText(context, "error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
 
 
            @Override
            public void onBannerClosed(Banner banner) {
 
            }
 
            @Override
            public void onBannerFinished() {
 
            }
 
            @Override
            public void onBannerClicked(Banner banner) {
                Toast.makeText(context, "ad clicked", Toast.LENGTH_SHORT).show();
            }
 
            @Override
            public void onNoFill(Banner banner) {
 
            }
        });
 
        banner.load();
        banner.setRefresh(30);//refresh every 30 seconds
        view.addView(banner);
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

### Tip

Select popular ```bannerWidth``` / ```bannerHeight``` combinations so that you get a good fill rate.

Popular sizes are: 320x50, 300x250, 320x480
