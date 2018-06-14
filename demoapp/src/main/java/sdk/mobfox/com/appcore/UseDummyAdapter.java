package sdk.mobfox.com.appcore;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by nabriski on 12/12/2016.
 *
 */

public class UseDummyAdapter extends Activity {

    static long millisecs;
    final DummyAdapter da = new DummyAdapter();
    Button load;
    Button qrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_dummy_adapter);

        final LinearLayout layout = (LinearLayout) findViewById(R.id.dummy_container);

        final UseDummyAdapter self = this;

        final DummyAdapter.Listener listener = new DummyAdapter.Listener(){

            @Override
            public void onAdLoaded(View v) {
                Log.d("DummyAdapter","add loaded > "+(System.currentTimeMillis() - UseDummyAdapter.millisecs));
                Toast.makeText(self, "ad loaded", Toast.LENGTH_SHORT).show();
                layout.removeAllViews();
                layout.addView(v);
                self.reload(layout,da,this);
            }

            @Override
            public void onAdError(Exception e) {
                Toast.makeText(self, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("DummyAdapter","error > "+(System.currentTimeMillis() - UseDummyAdapter.millisecs));
                self.reload(layout,da,this);

            }

            @Override
            public void onAdNoFill(View v) {
                Toast.makeText(self, "no fill", Toast.LENGTH_SHORT).show();
                Log.d("DummyAdapter","error > No add available "+(System.currentTimeMillis() - UseDummyAdapter.millisecs));
            }
        };

        load = (Button) findViewById(R.id.loadBtn);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAd(layout,da,listener);
            }
        });


    }

   protected  void loadAd(final LinearLayout layout, final DummyAdapter adapter, final DummyAdapter.Listener listener){

       final UseDummyAdapter self = this;
       layout.removeAllViews();
       Log.d("MobFoxBanner","load ad!");
       UseDummyAdapter.millisecs = System.currentTimeMillis();
       String invh = "80187188f458cfde788d961b6882fd53";  // video
       // String invh = "fe96717d9875b9da4339ea5367eff1ec"; // banner
       // String invh = "267d72ac3f77a3f447b32cf7ebf20673"; // interstitial
       adapter.loadAd(self, invh, listener);

   }

    protected void reload(final LinearLayout layout, final DummyAdapter adapter, final DummyAdapter.Listener listener){

        final UseDummyAdapter self = this;
        UseDummyAdapter.millisecs = System.currentTimeMillis();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        da.loadAd(self,"fe96717d9875b9da4339ea5367eff1ec",listener);
                    }
                },
                500000);
    }

}
