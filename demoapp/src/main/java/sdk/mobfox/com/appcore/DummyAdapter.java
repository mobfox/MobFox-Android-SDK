package sdk.mobfox.com.appcore;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.mobfox.sdk.bannerads.Banner;
import com.mobfox.sdk.bannerads.BannerListener;

import java.util.List;

/**
 * Created by nabriski on 12/12/2016.
 */

public class DummyAdapter {

    public interface Listener{
        void onAdLoaded(View v);
        void onAdError(Exception e);
    }


    void loadAd(Context c, String invh,final Listener listener){
        Banner banner = new Banner(c,320,50);
        banner.setListener(new BannerListener() {
            @Override
            public void onBannerError(View banner, Exception e) {
                listener.onAdError(e);
            }

            @Override
            public void onBannerLoaded(View banner) {
                listener.onAdLoaded(banner);
            }

            @Override
            public void onBannerClosed(View banner) {

            }

            @Override
            public void onBannerFinished() {

            }

            @Override
            public void onBannerClicked(View banner) {

            }

            @Override
            public void onNoFill(View banner) {

            }
        });


        banner.setInventoryHash(invh);
        banner.load();
    }
}
