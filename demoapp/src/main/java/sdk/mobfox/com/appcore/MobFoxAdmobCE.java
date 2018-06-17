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

public class MobFoxAdmobCE extends Activity implements AdapterView.OnItemSelectedListener {


    Banner banner;
    Interstitial interstitial;
    InterstitialListener listener;
    Boolean isBanner;
    Activity self;
    String tag = "MobFoxAdMobApp";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobfox_am_ce);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(144, 202, 249));

        Spinner sizeSpinner = (Spinner) findViewById(R.id.mfam_size_spinner);
        ArrayAdapter<CharSequence> sizeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.ce_sizes_array, android.R.layout.simple_spinner_item);

        sizeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeSpinnerAdapter);
        sizeSpinner.setOnItemSelectedListener(this);


        ///////// Banner //////////

        self = this;
        banner = (Banner) findViewById(R.id.bannerin_am);

        banner.setListener(new Banner.Listener() {
            @Override
            public void onBannerError(Banner banner, Exception e) {
                Toast.makeText(self, "banner error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerLoaded(Banner banner) {
                Toast.makeText(self, "banner loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerClosed(Banner banner) {
                Toast.makeText(self, "banner closed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerFinished() {
                Toast.makeText(self, "banner finished", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerClicked(Banner banner) {
                Toast.makeText(self, "banner clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNoFill(Banner banner) {
                Toast.makeText(self, "no fill", Toast.LENGTH_SHORT).show();
            }
        });


        /////// Interstitial //////


        listener = new InterstitialListener() {
            @Override
            public void onInterstitialLoaded(Interstitial interstitial) {
                Toast.makeText(self, "loaded", Toast.LENGTH_SHORT).show();
                interstitial.show();
            }

            @Override
            public void onInterstitialFailed(String e) {
                Toast.makeText(self, "failed e: " + e, Toast.LENGTH_SHORT).show();

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
        };


        ((Button)findViewById(R.id.load_mf_am_ce)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBanner){
//                    banner.setDemo_age(37);
//                    banner.setDemo_gender("F");
                    banner.load();
                }else {
                    interstitial = new Interstitial(self,"d2db78d5614bbc7a1cfe5a1ecb7760a2",listener);
//                    interstitial.getBanner().setDemo_age(25);
//                    interstitial.getBanner().setDemo_gender("M");
//                    interstitial.setInventoryHash("29829a1a989398f608db5b27c912de58");
//                    interstitial.setListener(listener);
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
//                Toast.makeText(self, spinnerId, Toast.LENGTH_SHORT).show();
                isBanner = true;
                break;
            case "Interstitial":
//                Toast.makeText(self, spinnerId, Toast.LENGTH_SHORT).show();
                isBanner = false;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
