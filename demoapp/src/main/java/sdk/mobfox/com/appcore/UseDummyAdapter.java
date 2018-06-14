package sdk.mobfox.com.appcore;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by nabriski on 12/12/2016.
 */

public class UseDummyAdapter extends Activity {

    static long millisecs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_dummy_adapter);

        final LinearLayout layout = (LinearLayout) findViewById(R.id.dummy_container);
        final DummyAdapter da = new DummyAdapter();
        final UseDummyAdapter self = this;

        final DummyAdapter.Listener listener = new DummyAdapter.Listener(){

            @Override
            public void onAdLoaded(View v) {
                Log.d("MobFoxBanner","add loaded > "+(System.currentTimeMillis() - UseDummyAdapter.millisecs));
                layout.addView(v);
                final DummyAdapter.Listener thisListener = this;
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                self.loadAd(layout,da,thisListener);
                            }
                        },
                        5000);
            }

            @Override
            public void onAdError(Exception e) {
                Toast.makeText(self, e.getMessage(), Toast.LENGTH_SHORT).show();
                final DummyAdapter.Listener thisListener = this;
                da.loadAd(self,"fe96717d9875b9da4339ea5367eff1ec",thisListener);
            }
        };


        loadAd(layout,da,listener);

    }

   protected  void loadAd(final LinearLayout layout, final DummyAdapter adapter, final DummyAdapter.Listener listener){

       final UseDummyAdapter self = this;
       layout.removeAllViews();
       Log.d("MobFoxBanner","load ad!");
       UseDummyAdapter.millisecs = System.currentTimeMillis();
       adapter.loadAd(self,"fe96717d9875b9da4339ea5367eff1ec",listener);

   }

}
