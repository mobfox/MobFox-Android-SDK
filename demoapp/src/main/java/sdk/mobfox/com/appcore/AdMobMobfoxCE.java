package sdk.mobfox.com.appcore;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.mobfox.adapter.MobFoxAdapter;

/**
 * Created by asafg84 on 15/05/16.
 */
public class AdMobMobfoxCE extends Activity implements AdapterView.OnItemSelectedListener {

    InterstitialAd mInterstitialAd;
    RewardedVideoAd mRewardedAd;
    RewardedVideoAdListener rewardedVideoAdListener;
    AdView mAdView;


    int adType;

    Activity self;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admob_mf_ce);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(144, 202, 249));
        self = this;

        mAdView = (AdView) findViewById(R.id.adView);


        Spinner sizeSpinner = (Spinner) findViewById(R.id.gadmf_size_spinner);
        ArrayAdapter<CharSequence> sizeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.admob_rewarded_ce, android.R.layout.simple_spinner_item);

        sizeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeSpinnerAdapter);
        sizeSpinner.setOnItemSelectedListener(this);


        ///////////////Admob Interstitial Listener

        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-6224828323195096/4350674761");
        mInterstitialAd.setAdUnitId("ca-app-pub-6224828323195096/1031427961");  //old
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Log.d("AdMob", "ad closed");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.d("AdMob", "ad error");
            }

            @Override
            public void onAdLeftApplication() {
                Log.d("AdMob", "ad left");
            }

            @Override
            public void onAdOpened() {
                Log.d("AdMob", "ad opened");
            }

            @Override
            public void onAdLoaded() {
                Log.d("AdMob", "ad loaded");
                mInterstitialAd.show();
            }
        });

        ///////////Admob Rewarded Listener
        rewardedVideoAdListener = new RewardedVideoAdListener() {


            @Override
            public void onRewardedVideoAdLoaded() {
                Log.d("AdMob", "onRewardedVideoAdLoaded");
                mRewardedAd.show();
            }

            @Override
            public void onRewardedVideoAdOpened() {
                Log.d("AdMob", "onRewardedVideoAdOpened");
            }

            @Override
            public void onRewardedVideoStarted() {
                Log.d("AdMob", "onRewardedVideoStarted");
            }

            @Override
            public void onRewardedVideoAdClosed() {
                Log.d("AdMob", "onRewardedVideoAdClosed");
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                Log.d("AdMob", "onRewarded");
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                Log.d("AdMob", "onRewardedVideoAdLeftApplication");
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                Log.d("AdMob", "onRewardedVideoAdFailedToLoad");
            }

        };


        ((Button) findViewById(R.id.load_gad_ce)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putBoolean("gdpr", true);
                bundle.putString("gdpr_consent", "YES");

                if (adType == 0) {
                    AdRequest adRequest = new AdRequest.Builder()
                            .addNetworkExtrasBundle(MobFoxAdapter.class, bundle)
                            .build();
                    mAdView.loadAd(adRequest);
                } else if (adType == 1) {
                    AdRequest adRequest = new AdRequest.Builder()
                            .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                            .addNetworkExtrasBundle(MobFoxAdapter.class, bundle)
                            .build();
                    mInterstitialAd.loadAd(adRequest);
                } else if (adType == 2) {
                    mRewardedAd = MobileAds.getRewardedVideoAdInstance(self);
                    mRewardedAd.setRewardedVideoAdListener(rewardedVideoAdListener);
                    mRewardedAd.loadAd("ca-app-pub-6224828323195096/5018083420", new AdRequest.Builder()
                            .addNetworkExtrasBundle(MobFoxAdapter.class, bundle)
                            .build());

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
                adType = 0;
                break;
            case "Interstitial":
                adType = 1;
                break;
            case "Rewarded":
                adType = 2;
                break;

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }
}
