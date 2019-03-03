---
id: native
title: Native Ads
---

Native Ads are ads returned to you a set of resources - images, click URLs, texts - that you use to build an ad with the "native" look of your app.

## Requesting Native Ads

```
import com.mobfox.sdk.nativeads.Native;
import com.mobfox.sdk.nativeads.NativeAd;
import com.mobfox.sdk.nativeads.NativeListener;
import com.mobfox.sdk.customevents.CustomEventNative;
 
public class NativeActivity extends Activity { 

    private Native native;
 
    private NativeListener listener;
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_layout);
 
        native = new Native(this);
 
 
        listener = new NativeListener() {
            @Override
            public void onNativeReady(Native aNative, CustomEventNative event, NativeAd ad) {
 
                Toast.makeText(self, "on native ready", Toast.LENGTH_SHORT).show();
 
                //register custom layout click
                event.registerViewForInteraction(layout);
                //fire trackers
                ad.fireTrackers(self);
 
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
 
        native.setListener(listener);
 
        //load our native
        native.load("a764347547748896b84e0b8ccd90fd62");
    }
 
}
```
