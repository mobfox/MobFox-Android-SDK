package sdk.mobfox.com.appcore;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobfox.sdk.banner.Banner;
import com.mobfox.sdk.interstitial.Interstitial;
import com.mobfox.sdk.interstitial.InterstitialListener;


/**
 * Created by asafg84 on 19/05/16.
 */
public class MobFoxMopubCE extends Activity implements AdapterView.OnItemSelectedListener {

    Banner banner;
    Interstitial interstitial;
    InterstitialListener listener;
    Boolean isBanner;

    Activity self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobfox_mp_ce);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(144, 202, 249));


        banner = (Banner) findViewById(R.id.bannerin_mp);

        Spinner sizeSpinner = (Spinner) findViewById(R.id.mfmp_size_spinner);
        ArrayAdapter<CharSequence> sizeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.ce_sizes_array, android.R.layout.simple_spinner_item);

        sizeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeSpinnerAdapter);
        sizeSpinner.setOnItemSelectedListener(this);


        String MoPubInter = "0fe750a1c049923f1b14f5958a353d1d";

        String MopubBanner = "30d36ccbff22b2c7dae0e3d2669be832";
        self = this;



        ////// Banner


        banner.setListener(new Banner.Listener() {
            @Override
            public void onBannerError(Banner banner, Exception e) {
                Toast.makeText(self, "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerLoaded(Banner banner) {
                Toast.makeText(self, "loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerClosed(Banner banner) {
                Toast.makeText(self, "closed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerFinished() {
                Toast.makeText(self, "finished", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerClicked(Banner banner) {
                Toast.makeText(self, "clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNoFill(Banner banner) {
                Toast.makeText(self, "no fill", Toast.LENGTH_SHORT).show();
            }
        });


        ///////////  Interstitial



        interstitial = new Interstitial(this,MoPubInter);
        interstitial.setListener(new InterstitialListener() {
            @Override
            public void onInterstitialLoaded(Interstitial interstitial) {
                Toast.makeText(self, "loaded", Toast.LENGTH_SHORT).show();
                interstitial.show();
            }

            @Override
            public void onInterstitialFailed(String e) {
                Toast.makeText(self, "failed, error: " + e, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInterstitialClosed() {
                Toast.makeText(self, "closed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInterstitialClicked() {
                Toast.makeText(self, "clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInterstitialShown() {
                Toast.makeText(self, "shown", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInterstitialFinished() {
                Toast.makeText(self, "finished", Toast.LENGTH_SHORT).show();
            }
        });


        ((Button) findViewById(R.id.load_mf_am_ce)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBanner) {
//                    banner.removeView(banner);
                    banner.load();
                } else {
                    interstitial.load();
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String spinnerId = parent.getItemAtPosition(position).toString();
//        int parentId = parent.getId();

        switch (spinnerId) {
            case "Banner":
                Toast.makeText(self, spinnerId, Toast.LENGTH_SHORT).show();
                isBanner = true;
                break;
            case "Interstitial":
                Toast.makeText(self, spinnerId, Toast.LENGTH_SHORT).show();
                isBanner = false;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
