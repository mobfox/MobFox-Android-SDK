package sdk.mobfox.com.appcore;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mobfox.sdk.bannerads.Banner;
import com.mobfox.sdk.bannerads.BannerListener;

/**
 * Created by asafg84 on 15/06/16.
 */
public class RTBView extends Activity implements View.OnClickListener {
    Banner aboveTheFold;
    Banner belowTheFold;
    Banner header;
    Banner footer;
    BannerListener listener;
    //banner
    static final String banner_invh = "fe96717d9875b9da4339ea5367eff1ec";
    //text
    static final String text_invh = "8769bb5eb962eb39170fc5d8930706a9";
    //video
    static final String video_invh = "80187188f458cfde788d961b6882fd53";
    //audio
    static final String audio_invh = "75f994b45ca31b454addc8b808d59135";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_view);
        final Activity self = this;
        aboveTheFold = (Banner) findViewById(R.id.aboveTheFold);
        belowTheFold = (Banner) findViewById(R.id.belowTheFold);
        header = (Banner) findViewById(R.id.header);
        footer = (Banner) findViewById(R.id.footer);
        listener = new BannerListener() {
            @Override
            public void onBannerError(View banner, Exception e) {

                Toast.makeText(self, "on banner error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerLoaded(View banner) {

                Toast.makeText(self, "on banner loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerClosed(View banner) {

                Toast.makeText(self, "on banner closed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerFinished() {

                Toast.makeText(self, "on banner finished", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerClicked(View banner) {

                Toast.makeText(self, "on banner clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNoFill(View banner) {

                Toast.makeText(self, "no fill", Toast.LENGTH_SHORT).show();
            }
        };
        header.setListener(listener);
        header.setInventoryHash(text_invh);
//        header.load();
        footer.setListener(listener);
        footer.setInventoryHash(text_invh);
//        footer.load();
        aboveTheFold.setListener(listener);
        aboveTheFold.setInventoryHash(text_invh);
//        aboveTheFold.load();
        belowTheFold.setListener(listener);
        belowTheFold.setInventoryHash(text_invh);
//        belowTheFold.load();

        Button topLoadBtn = (Button) findViewById(R.id.topLoadBtn);
        Button bottomLoadBtn = (Button) findViewById(R.id.bottomLoadBtn);

        topLoadBtn.setOnClickListener(this);
        bottomLoadBtn.setOnClickListener(this);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        header.onRequestPermissionsResult(requestCode, permissions, grantResults);
        footer.onRequestPermissionsResult(requestCode, permissions, grantResults);
        belowTheFold.onRequestPermissionsResult(requestCode, permissions, grantResults);
        aboveTheFold.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View v) {
//        header.load();
//        footer.load();
        belowTheFold.load();
        aboveTheFold.load();
    }
}
