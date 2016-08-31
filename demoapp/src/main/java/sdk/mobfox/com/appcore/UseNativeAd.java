package sdk.mobfox.com.appcore;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.formats.NativeAppInstallAdView;
import com.mobfox.sdk.customevents.CustomEventNative;
import com.mobfox.sdk.nativeads.Native;
import com.mobfox.sdk.nativeads.NativeAd;
import com.mobfox.sdk.nativeads.NativeListener;

/**
 * Created by asafg84 on 03/05/16.
 */
public class UseNativeAd extends Activity {

    final static String some_app_native = "4c3ea57788c5858881dc42cfafe8c0ab";
    final static String invh = "80187188f458cfde788d961b6882fd53";
    final static String tag = "UseNative";

    private Native aNative;
    private Activity self;

    private NativeListener listener;

    static String userAgent = System.getProperty("http.agent");

    TextView headline;
    ImageView nativeIcon, nativeMainImg;
    RelativeLayout layout;
    FrameLayout fl_adplaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_native);

        self = this;

        //assigning xml components to our layout
        headline = (TextView) findViewById(R.id.headline);
        nativeIcon = (ImageView) findViewById(R.id.nativeIcon);
        nativeMainImg = (ImageView) findViewById(R.id.nativeMainImg);
        layout = (RelativeLayout) findViewById(R.id.nativeLayout);
        //admob
//        final NativeAppInstallAdView nativeAdView = (NativeAppInstallAdView) findViewById(R.id.nativeAdView);

        aNative = new Native(this);

        //we must set a listener for native

        listener = new NativeListener() {
            @Override
            public void onNativeReady(Native aNative, CustomEventNative event, NativeAd ad) {

                Toast.makeText(self, "on native ready", Toast.LENGTH_SHORT).show();

                //register custom layout click
                fl_adplaceholder = (FrameLayout) findViewById(R.id.fl_adplaceholder);
                event.registerViewForInteraction(fl_adplaceholder);

                ad.fireTrackers(self);

//                headline.setText(ad.getHeadline());

                ad.loadImages(self, new NativeAd.ImagesLoadedListener() {
                    @Override
                    public void onImagesLoaded(NativeAd ad) {

                        Toast.makeText(self, "on images ready", Toast.LENGTH_SHORT).show();

                        nativeIcon.setImageBitmap(ad.getMain());
//                        nativeMainImg.setImageBitmap(ad.getIcon());

                    }
                });

            }

            @Override
            public void onNativeError(Exception e) {

                Toast.makeText(self, "on native error", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNativeClick(NativeAd ad) {

                Toast.makeText(self, "on native click", Toast.LENGTH_SHORT).show();

            }

        };

        aNative.setListener(listener);

        //load our native
        aNative.load(some_app_native);
    }

}