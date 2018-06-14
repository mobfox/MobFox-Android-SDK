package sdk.mobfox.com.appcore;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.mobfox.sdk.bannerads.Banner;
import com.mobfox.sdk.bannerads.BannerListener;
import com.mobfox.sdk.customevents.CustomEventBanner;

/**
 * Created by asafg84 on 28/06/16.
 */
public class CustomSizeBanner extends Activity {
    Banner banner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_size);
        banner = new Banner(this, 320, 50);
        banner.setListener(new BannerListener() {
            @Override
            public void onBannerError(View banner, Exception e) {
            }

            @Override
            public void onBannerLoaded(View banner) {
                Log.d("appCore", "loaded");
                CustomSizeBanner.this.addContentView(banner, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
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
        banner.setInventoryHash("8769bb5eb962eb39170fc5d8930706a9");
        banner.load();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        banner.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

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
