package sdk.mobfox.com.appcore;

import android.Manifest;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by asafg84 on 28/02/16.
 */
public class Menu extends ListActivity {

    static String cheesePackage = "sdk.mobfox.com.appcore";

    String[] classes = {"UseBannerAd","UseInterstitialAd","UseNativeAd","MobFoxMopubCE","MobFoxAdmobCE","AdMobMobfoxCE","MoPubMobfoxCE","UseDummyAdapter","InlineBannerActivity"};
    Intent myIntent;
    Class myClass;
    Context myContext = Menu.this;
    String cheese;

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        cheese = classes[position];
        try {
            myClass = Class.forName(cheesePackage + "." + cheese);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        myIntent = new Intent(myContext, myClass);
        startActivity(myIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setListAdapter(new ArrayAdapter<>(myContext, android.R.layout.simple_list_item_1, classes));

        LinearLayout llMethods = new LinearLayout(myContext);
        LinearLayout.LayoutParams methodsP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llMethods.setOrientation(LinearLayout.HORIZONTAL);
        llMethods.setWeightSum(32f);
        llMethods.setLayoutParams(methodsP);

        CheckBox secure = new CheckBox(myContext);
        LinearLayout.LayoutParams secure_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        secure_params.weight = 8;
        secure.setText("secure");
        secure.setLayoutParams(secure_params);

        CheckBox inter_secure = new CheckBox(myContext);
        LinearLayout.LayoutParams inter_secure_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        inter_secure_params.weight = 8;
        inter_secure.setText("secure interstitial");
        inter_secure.setLayoutParams(inter_secure_params);

        llMethods.addView(secure);
//        llMethods.addView(inter_secure);

        ViewGroup lv = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);

        ViewGroup parent = (ViewGroup) lv.getParent();

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);

        params.gravity = Gravity.BOTTOM;

        parent.addView(llMethods, params);

        secure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                /*if (isChecked)
                    Banner.setSecure(true);
                else
                    Banner.setSecure(false);*/
            }
        });

        inter_secure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                /*if (isChecked)
                    InterstitialAd.setSecure(true);
                else
                    InterstitialAd.setSecure(false);*/
            }
        });

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        5555);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 5555: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}
