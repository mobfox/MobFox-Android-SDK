package sdk.mobfox.com.appcore;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobfox.sdk.interstitialads.InterstitialAd;
import com.mobfox.sdk.interstitialads.InterstitialAdListener;

/**
 * Created by asafg84 on 03/05/16.
 */
public class UseInterstitialAd extends Activity implements View.OnClickListener {
    InterstitialAd interstitial;
    InterstitialAdListener listener;

    //interstitial
    static final String inter_invh = "267d72ac3f77a3f447b32cf7ebf20673";
    //video
    static final String some_app_inter = "145849979b4c7a12916c7f06d25b75e3";
    static final String video_invh = "80187188f458cfde788d961b6882fd53";
//    static final String video_invh = "9b3e3e967ad034aabd9a8d2295f51251";
    //audio
    static final String audio_invh = "75f994b45ca31b454addc8b808d59135";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_interstitial);

        makeButtons();

        interstitial = new InterstitialAd(this);

        listener = new InterstitialAdListener() {
            @Override
            public void onInterstitialLoaded(InterstitialAd interstitial) {

                interstitial.show();

                Toast.makeText(UseInterstitialAd.this, "load", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onInterstitialFailed(InterstitialAd interstitial, Exception e) {

                Toast.makeText(UseInterstitialAd.this, "fail, " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onInterstitialClosed(InterstitialAd interstitial) {

                Toast.makeText(UseInterstitialAd.this, "close", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onInterstitialFinished() {

                Toast.makeText(UseInterstitialAd.this, "finish", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onInterstitialClicked(InterstitialAd interstitial) {

                Toast.makeText(UseInterstitialAd.this, "click", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onInterstitialShown(InterstitialAd interstitial) {

                Toast.makeText(UseInterstitialAd.this, "show", Toast.LENGTH_SHORT).show();

            }
        };
        interstitial.setListener(listener);
        interstitial.setSkip(true);
        interstitial.setInventoryHash(some_app_inter);
    }

    //need to add this so video ads will work properly

    @Override
    protected void onPause() {
        super.onPause();
        interstitial.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        interstitial.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        interstitial.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected void makeButtons() {

        LinearLayout row = new LinearLayout(this);

        String[] labels = {
                "reg",
                "video",
                "audio",
                "load"
        };

        Button[] buttons = {

                new Button(this),
                new Button(this),
                new Button(this),
                new Button(this)
        };

        for (int i = 0; i < labels.length; i++) {

            buttons[i].setText(labels[i]);
            buttons[i].setId(i);

//            buttons[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT));

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
            //regular
            case 0:

                interstitial.setInventoryHash(inter_invh);
                interstitial.setType("");

                Toast.makeText(this, "invh: " + inter_invh, Toast.LENGTH_SHORT).show();

                break;
            //video
            case 1:

                interstitial.setInventoryHash(video_invh);
                interstitial.setType("video");
             //   interstitial.setStart_muted(true);
                interstitial.setSkip(true);

                Toast.makeText(this, "invh: " + video_invh, Toast.LENGTH_SHORT).show();

                break;
            //audio
            case 2:

                interstitial.setInventoryHash(audio_invh);
                interstitial.setType("video");
                interstitial.setSkip(true);
                interstitial.setStart_muted(true);

                Toast.makeText(this, "invh: " + audio_invh, Toast.LENGTH_SHORT).show();

                break;
            //load
            case 3:

                interstitial.load();

                break;
        }

    }
}