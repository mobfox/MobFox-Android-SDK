package sdk.mobfox.com.appcore;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobfox.sdk.bannerads.Banner;
import com.mobfox.sdk.bannerads.BannerListener;


/**
 * Created by asafg84 on 13/04/16.
 */
public class UseBannerAd extends Activity implements View.OnClickListener {

    Banner banner;
    BannerListener listener;

    //banner
    static final String banner_invh = "fe96717d9875b9da4339ea5367eff1ec";
    static final String some_app = "eb115dc9c19112f5a5c95ab728a3ce9c";
    //text
    static final String text_invh = "8769bb5eb962eb39170fc5d8930706a9";
    //video
    static final String video_invh = "80187188f458cfde788d961b6882fd53";
    //static final String video_invh = "9b3e3e967ad034aabd9a8d2295f51251";
    //audio
    static final String audio_invh = "75f994b45ca31b454addc8b808d59135";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.banner_ad);
        makeButtons();
        final Activity self = this;
        banner = (Banner) findViewById(R.id.banner);
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
            public void onNoFill(View view) {

                Toast.makeText(self, "no fill", Toast.LENGTH_SHORT).show();
            }
        };
        Banner.setGetLocation(false);
        //Banner.setDev_js(0);
        banner.setListener(listener);
        banner.setInventoryHash(banner_invh);
//        banner.setRefresh(5);
      //  banner.setDev_js(0);

        Toast.makeText(this, "invh: " + banner_invh, Toast.LENGTH_SHORT).show();
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

    protected void makeButtons() {

        LinearLayout row = new LinearLayout(this);

        String[] labels = {
                "text",
                "banner",
                "video",
                "audio",
                "load"
        };

        Button[] buttons = {

                new Button(this),
                new Button(this),
                new Button(this),
                new Button(this),
                new Button(this)
        };

        for (int i = 0; i < labels.length; i++) {

            buttons[i].setText(labels[i]);
            buttons[i].setId(i);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);

            buttons[i].setLayoutParams(params);

            buttons[i].setOnClickListener(this);

            row.addView(buttons[i]);
        }

        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);

        viewGroup.addView(row);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {

            //text
            case 0:
                banner.setType("");
                banner.setInventoryHash(text_invh);
                banner.setSkip(true);

                Toast.makeText(this, "invh: " + text_invh, Toast.LENGTH_SHORT).show();

                break;

            //banner
            case 1:
                banner.setType("");
                banner.setInventoryHash(banner_invh);

                Toast.makeText(this, "invh: " + banner_invh, Toast.LENGTH_SHORT).show();

                break;

            //video
            case 2:

                banner.setInventoryHash(video_invh);
                banner.setType("video");
                banner.setStart_muted(true);
                banner.setRefresh(0);
                banner.setSkip(true);

                Toast.makeText(this, "invh: " + video_invh, Toast.LENGTH_SHORT).show();

                break;
            //audio
            case 3:

                banner.setInventoryHash(audio_invh);
                banner.setType("video");
                banner.setRefresh(0);
//                banner.setAutoplay("false");
                banner.setStart_muted(true);
                banner.setSkip(true);

                Toast.makeText(this, "invh: " + audio_invh, Toast.LENGTH_SHORT).show();

                break;
            //load
            case 4:

                banner.load();

                break;
        }

    }
}
