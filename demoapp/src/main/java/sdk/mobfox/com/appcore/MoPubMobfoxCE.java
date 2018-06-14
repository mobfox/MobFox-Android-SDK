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

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubView;

/**
 * Created by asafg84 on 16/05/16.
 */
public class MoPubMobfoxCE extends Activity implements AdapterView.OnItemSelectedListener {
    private MoPubView moPubView;
    private MoPubInterstitial mInterstitial;

    Boolean isBanner;

    Activity self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mopub_mf_ce);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(144,202,249));


//        String invhBanner  = "8dae639b05354cae88760db5ce751346";
        String invhBanner  = "4ad212b1d0104c5998b288e7a8e35967";
//        String invhBanner  = "ab70c611fc4c4c789cb8214ee85869f7"; // old
        String invhInterstitial  = "3fd85a3e7a9d43ea993360a2536b7bbd";
//        String invhInterstitial  = "ab70c611fc4c4c789cb8214ee85869f7";


        Spinner sizeSpinner = (Spinner) findViewById(R.id.mpmf_size_spinner);
        ArrayAdapter<CharSequence> sizeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.ce_sizes_array, android.R.layout.simple_spinner_item);

        sizeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeSpinnerAdapter);
        sizeSpinner.setOnItemSelectedListener(this);

        self = this;
        moPubView = (MoPubView) findViewById(R.id.adview);
        moPubView.setAdUnitId(invhBanner);

        moPubView.setBannerAdListener(new MoPubView.BannerAdListener() {
            @Override
            public void onBannerLoaded(MoPubView banner) {
                Toast.makeText(self, "loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {
                Toast.makeText(self, "failed", Toast.LENGTH_SHORT).show();
                banner.destroy();
            }

            @Override
            public void onBannerClicked(MoPubView banner) {
                Toast.makeText(self, "clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerExpanded(MoPubView banner) {
                Toast.makeText(self, "expand", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerCollapsed(MoPubView banner) {
                Toast.makeText(self, "collapsed", Toast.LENGTH_SHORT).show();
                banner.destroy();
            }
        });


        //////////// MP inter listener

        mInterstitial = new MoPubInterstitial(this, invhInterstitial);

        MoPubInterstitial.InterstitialAdListener listener = new MoPubInterstitial.InterstitialAdListener() {
            @Override
            public void onInterstitialLoaded(MoPubInterstitial interstitial) {
                Toast.makeText(self, "loaded", Toast.LENGTH_SHORT).show();
                if (interstitial.isReady()) {
                    mInterstitial.show();
                } else {
                    // Other code
                }
            }

            @Override
            public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {
                Toast.makeText(self, "failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInterstitialShown(MoPubInterstitial interstitial) {
                Toast.makeText(self, "shown", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInterstitialClicked(MoPubInterstitial interstitial) {
                Toast.makeText(self, "clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInterstitialDismissed(MoPubInterstitial interstitial) {
                Toast.makeText(self, "dismissed", Toast.LENGTH_SHORT).show();
                interstitial.destroy();
            }
        };
        mInterstitial.setInterstitialAdListener(listener);


        ((Button) findViewById(R.id.load_mp_ce)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBanner){
                    moPubView.loadAd();
                }else {
                    mInterstitial.load();
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
