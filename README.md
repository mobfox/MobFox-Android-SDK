# MobFox-Android-SDK-Core-Lib

Supports Android OS 4.1.x (Jelly Bean) and up.

#Prerequisites

You will need a [MobFox](http://www.mobfox.com/) account.

# Installation

# Usage

## Banner

Add in your ```AndroidManifext.xml``` or in one of your layout xmls:
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
// ....

import com.mobfox.sdk.Banner;
import com.mobfox.sdk.BannerListener;

// ....

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
```



