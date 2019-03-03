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
 
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
 
    interstitial = new Interstitial(this,"<your-publication-hash>");
 
    listener = new InterstitialListener() {
                    @Override
                    public void onInterstitialLoaded(Interstitial interstitial) {
                        interstitial.show();
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
                };
    interstitial.setListener(listener);
    interstitial.load();
}
 

```


