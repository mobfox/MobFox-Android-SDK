package sdk.mobfox.com.appcore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.mobfox.sdk.banner.Banner;
import com.mobfox.sdk.networking.MobfoxRequestParams;

import sdk.mobfox.com.appcore.barcode.BarcodeCaptureActivity;


/**
 * Created by asafg84 on 13/04/16.
 */
public class UseBannerAd extends Activity implements AdapterView.OnItemSelectedListener {

    private static final int BARCODE_READER_REQUEST_CODE = 1;

    private static String invh = "fe96717d9875b9da4339ea5367eff1ec";
    // private static String invh = "30d36ccbff22b2c7dae0e3d2669be832"; 
  //  private static String invh = "396a7ad474e81a2f15ef7da323afdb4f"; //MOAT
    //private static String invh = "fe96717d9875b9da4339ea5367eff1ec";


    public EditText invhText,floorText;
    public Button qrcode,loadBtn;
    public Context c;
    public MobfoxRequestParams requestParams;
    public ViewGroup.LayoutParams layoutParams;

    float floor = -1;
    int bannerSize = 0;
    String server="";

    Banner banner320x50,banner300x50,banner300x250;
    Banner.Listener bannerListener;
    LinearLayout view;
    View mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_banner_ad);
        getWindow().getDecorView().setBackgroundColor(Color.rgb(144,202,249));

        c = getApplicationContext();

        requestParams = new MobfoxRequestParams();

//        view        = (LinearLayout)findViewById(R.id.banner_container);

        floorText   = (EditText)    findViewById(R.id.floor_etext);
        invhText    = (EditText)    findViewById(R.id.invhText);
        loadBtn     = (Button)      findViewById(R.id.load_btn);
        qrcode      = (Button)      findViewById(R.id.qrcode);
        banner320x50       = (Banner)      findViewById(R.id.banner320x50);
        banner300x50       = (Banner)      findViewById(R.id.banner300x50);
        banner300x250      = (Banner)      findViewById(R.id.banner300x250);

        Spinner sizeSpinner     = (Spinner) findViewById(R.id.size_spinner);
        Spinner serverSpinner   = (Spinner) findViewById(R.id.server_spinner);

        invhText.setText(invh);


        qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
                startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
            }
        });

        ArrayAdapter<CharSequence> sizeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.sizes_array, android.R.layout.simple_spinner_item);

        sizeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeSpinnerAdapter);
        sizeSpinner.setOnItemSelectedListener(this);


        ArrayAdapter<CharSequence> serverSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.servers_array, android.R.layout.simple_spinner_item);

        serverSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serverSpinner.setAdapter(serverSpinnerAdapter);
        serverSpinner.setOnItemSelectedListener(this);


        // Banner Listener
        bannerListener = new Banner.Listener() {
            @Override
            public void onBannerError(Banner banner, Exception e) {
                Toast.makeText(c, e.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerLoaded(Banner banner) {
                Toast.makeText(c, "ad loaded", Toast.LENGTH_SHORT).show();
                view.addView(banner);
            }

            @Override
            public void onBannerClosed(Banner banner) {
                Toast.makeText(c, "closed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerFinished() {
                Toast.makeText(c, "finished", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerClicked(Banner banner) {
                Toast.makeText(c, "clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNoFill(Banner banner) {
                Toast.makeText(c, "no fill", Toast.LENGTH_SHORT).show();
            }
        };

        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBanner();

            }
        });


    }

    public void loadBanner() {

        invh = invhText.getText().toString();
//            if (floorText.getText().length() > 0) {
//                floor = Float.parseFloat(floorText.getText().toString());
//                if (floor > 0) {
//                    requestParams.setParam(MobfoxRequestParams.R_FLOOR, floor);
//                    banner320x50.addParams(requestParams);
//                }
//            }
//
//            if (!server.equals("")){
//                if (server.equals("http://nvirginia-my.mobfox.com")){
//                    requestParams.setParam("debugResponseURL", server);
//                    banner320x50.addParams(requestParams);
//                }
//                if (server.equals("http://tokyo-my.mobfox.com")){
//                    requestParams.setParam("debugResponseURL", server);
//                    banner320x50.addParams(requestParams);
//
//                }
//
//            }

        switch (bannerSize){
            case 0:
                banner300x250.hideBanner();
                banner300x50.hideBanner();
                banner320x50.load();
                break;
            case 1:
                banner320x50.hideBanner();
                banner300x250.hideBanner();
                banner300x50.load();
                break;
            case 2:
                banner320x50.hideBanner();
                banner300x50.hideBanner();
                banner300x250.load();
                break;
        }

    }


    public static void makeToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(banner320x50 != null) {
            banner320x50.onPause();
        }
        if(banner300x50 != null) {
            banner300x50.onPause();
        }
        if(banner300x250 != null) {
            banner300x250.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(banner320x50 != null) {
            banner320x50.onResume();
        }
        if(banner300x50 != null) {
            banner300x50.onResume();
        }
        if(banner300x250 != null) {
            banner300x250.onResume();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    // mResultTextView.setText(barcode.displayValue);
                    //invh = barcode.displayValue;
                    invhText.setText(barcode.displayValue);
                }
            }
        }
        else super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String spinnerId = parent.getItemAtPosition(position).toString();
//        int parentId = parent.getId();

        switch (spinnerId){
            case "320x50":
                bannerSize = 0;
                break;
            case "300x50":
                bannerSize = 1;
                break;
            case "300x250":
                bannerSize = 2;
                break;
            case "Size":
                bannerSize = 0;
                break;
            case "Server":
                server="";
                break;
            case "North Virginia":
                //set server to north virginia
                server = "http://nvirginia-my.mobfox.com";
//                Toast.makeText(c,"North Virginia",Toast.LENGTH_SHORT).show();
                break;
            case "Tokyo":
                //set server to tokyo
                server = "http://tokyo-my.mobfox.com";
//                Toast.makeText(c,"Tokyo",Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
