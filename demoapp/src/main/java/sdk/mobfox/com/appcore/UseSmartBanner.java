package sdk.mobfox.com.appcore;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mobfox.sdk.bannerads.Banner;
import com.mobfox.sdk.bannerads.BannerListener;

/**
 * Created by asaf on 07/12/16.
 */

public class UseSmartBanner extends Activity {
    Banner banner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smart_banner);

        banner = (Banner) findViewById(R.id.banner);

        final Activity self = this;
        banner.setListener(new BannerListener() {
            @Override
            public void onBannerError(View banner, Exception e) {
                Toast.makeText(self, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onBannerLoaded(View banner) {
                Toast.makeText(self, "loaded", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onBannerClosed(View banner) {
                Toast.makeText(self, "closed", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onBannerFinished() {
                Toast.makeText(self, "finished", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onBannerClicked(View banner) {
                Toast.makeText(self, "clicked", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNoFill(View banner) {
                Toast.makeText(self, "no fill", Toast.LENGTH_SHORT).show();
            }
        });
        banner.setInventoryHash("fe96717d9875b9da4339ea5367eff1ec");
        banner.load();
    }
    //permission dialog for marshmello and above
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        banner.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
