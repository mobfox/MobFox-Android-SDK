package sdk.mobfox.com.appcore;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.mobfox.sdk.interstitial.Interstitial;
import com.mobfox.sdk.interstitial.InterstitialListener;
import com.mobfox.sdk.networking.MobfoxRequestParams;
import com.mobfox.sdk.tags.BannerTag;

/**
 * Created by nabriski on 12/12/2016.
 *
 */

public class DummyAdapter {

    private BannerTag banner;
    private float floor = -1;

    public void setFloor(float floor) {
        this.floor = floor;
    }

    public interface Listener{
        void onAdLoaded(View v);
        void onAdError(Exception e);
        void onAdNoFill(View v);
    }


    void loadAd(Context c, String invh, final Listener dummylistener) {

        Interstitial newOne = new Interstitial(c, invh);
        newOne.setListener(new InterstitialListener() {
            @Override
            public void onInterstitialLoaded(Interstitial interstitial) {
                interstitial.show();
            }

            @Override
            public void onInterstitialFailed(String e) {
                Log.d("New Interstitial", e);
            }

            @Override
            public void onInterstitialClosed() {
                Log.d("New Interstitial", "onInterstitialClosed");
            }

            @Override
            public void onInterstitialClicked() {
                Log.d("New Interstitial", "onInterstitialClicked");
            }

            @Override
            public void onInterstitialShown() {
                Log.d("New Interstitial", "onInterstitialShown");
            }

            @Override
            public void onInterstitialFinished() {
                Log.d("New Interstitial", "onInterstitialFinished");
            }
        });

        if(floor > 0){
            MobfoxRequestParams rqp = new MobfoxRequestParams();
            rqp.setParam(MobfoxRequestParams.R_FLOOR,floor);
            newOne.setRequestParams(rqp);
        }
        newOne.load();
    }

    public void invalidate() {
        banner.setListener(null);
    }
}
