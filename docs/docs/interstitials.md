---
id: interstitials
title: Interstitial Ads
---

Interstitials are full screen ads. 

#### There are two steps to display an intersititial ad:
1. Load the ad.
2. When you wish to show the loaded interstitial, call the ```interstital.show()``` method to display the ad.

## Setup In Your Activity

```java
import com.mobfox.sdk.interstitial.Interstitial;
import com.mobfox.sdk.interstitial.InterstitialListener;
 
Interstitial interstitial;
InterstitialListener listener;

//only needed to run this example
import android.widget.Toast;
import android.content.Context;
 
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
 
    final Context context = this.getApplicationContext();  

    //test inventory hash for interstitial
    String inventoryHash = "267d72ac3f77a3f447b32cf7ebf20673";
    interstitial = new Interstitial(this,inventoryHash);
 
    listener = new InterstitialListener() {
            @Override
            public void onInterstitialLoaded(Interstitial interstitial) {

                //.show() can only be called after the ad is loaded.
                //But does not have to be called immediately -
                //you can wait for an opportune moment to do so.
                interstitial.show();
            }

            @Override
            public void onInterstitialFailed(String errStr) {
                Toast.makeText(context, "ad load failed: "+errStr, Toast.LENGTH_SHORT).show(); 
            }

            @Override
            public void onInterstitialClosed() { 
                Toast.makeText(context, "ad closed", Toast.LENGTH_SHORT).show(); 
            }

            @Override
            public void onInterstitialClicked() {
                Toast.makeText(context, "ad clicked", Toast.LENGTH_SHORT).show(); 
            }

            @Override
            public void onInterstitialShown() {
                Toast.makeText(context, "ad shown", Toast.LENGTH_SHORT).show(); 
            }

            @Override
            public void onInterstitialFinished() {
                Toast.makeText(context, "ad finished playing (for a video ad)", Toast.LENGTH_SHORT).show(); 
            }
    };
    interstitial.setListener(listener);
    interstitial.load();
}
 

```


