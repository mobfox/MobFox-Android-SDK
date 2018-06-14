package sdk.mobfox.com.appcore;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.mobfox.sdk.banner.Banner;
import com.mobfox.sdk.networking.MobfoxRequestParams;

/**
 * Created by nabriski on 10/05/2018.
 */

public class InlineBannerActivity extends Activity {

    Banner banner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.banner_inline);


        banner = (Banner) findViewById(R.id.banner);
        banner.setListener(new Banner.Listener() {
            @Override
            public void onBannerError(Banner banner, Exception e) {

            }

            @Override
            public void onBannerLoaded(Banner banner) {

            }

            @Override
            public void onBannerClosed(Banner banner) {

            }

            @Override
            public void onBannerFinished() {

            }

            @Override
            public void onBannerClicked(Banner banner) {

            }

            @Override
            public void onNoFill(Banner banner) {

            }
        });
        MobfoxRequestParams gdprParams = new MobfoxRequestParams();
        gdprParams.setParam(MobfoxRequestParams.GDPR,"1");
        gdprParams.setParam(MobfoxRequestParams.GDPR_CONSENT,"1");
        banner.addParams(gdprParams);
        banner.load();
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
