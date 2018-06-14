package sdk.mobfox.com.appcore;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mobfox.sdk.bannerads.Banner;
import com.mobfox.sdk.bannerads.BannerListener;
import com.mobfox.sdk.bannerads.LayoutUtils;

import static sdk.mobfox.com.appcore.UseNativeAd.ACTION_SCAN;
import static sdk.mobfox.com.appcore.UseNativeAd.onResult;
import static sdk.mobfox.com.appcore.UseNativeAd.tag;
import static sdk.mobfox.com.appcore.UseNativeAd.toasts;


/**
 * Created by asafg84 on 13/04/16.
 */
public class UseBannerAd extends Activity {

    String invh = "";

    Banner banner;
    BannerListener listener;

    UseBannerAd self;

    MySpinner spinnerInvh;
    MySpinner spinnersizes;
    EditText etInvh;
    Button loadBtn;

    boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.banner_ad);
        self = this;

        banner = (Banner) findViewById(R.id.banner);
        listener = new BannerListener() {
            @Override
            public void onBannerError(View banner, Exception e) {
                Toast.makeText(self, "on banner error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerLoaded(View banner) {
                Toast.makeText(self, "on banner loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerClosed(View banner) {
                Toast.makeText(self, "on banner closed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerFinished() {
                Toast.makeText(self, "on banner finished", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBannerClicked(View banner) {
                Toast.makeText(self, "on banner clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNoFill(View view) {
                Toast.makeText(self, "no fill", Toast.LENGTH_SHORT).show();
            }
        };
        banner.setListener(listener);
        banner.setR_floor(0.5f);

        etInvh = (EditText) findViewById(R.id.etInvh);

        spinnerInvh = (MySpinner) findViewById(R.id.spinnerInvh);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinnerinvh, android.R.layout.simple_spinner_item);
        spinnerInvh.setAdapter(adapter);
        spinnerInvh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (first) {
                    first = false;
                    return;
                }
                String label = parent.getItemAtPosition(position).toString();
                String[] spinnerinvh = getResources().getStringArray(R.array.spinnerinvh);
                if (label.equals(spinnerinvh[0])) {
                    invh = "";
                    etInvh.setText("");
                    try {
                        Intent intent = new Intent(ACTION_SCAN);
                        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                        startActivityForResult(intent, 0);
                    } catch (ActivityNotFoundException e) {
                        UseNativeAd.showDialog(self, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
                    }
                    return;
                }
                String[] splited = label.split("\\s+");
                invh = splited[splited.length - 1];
                etInvh.setText(invh);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnersizes = (MySpinner) findViewById(R.id.spinnersizes);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.bannersizes, android.R.layout.simple_spinner_item);
        spinnersizes.setAdapter(adapter);
        spinnersizes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String label = parent.getItemAtPosition(position).toString();
                if (label.equals("smart_banner")) {
                    banner.setSmart(true);
                    makeToast(self, "smart_banner");
                    return;
                }
                String[] splited = label.split("x");
                setDimensions(banner, Integer.parseInt(splited[0]), Integer.parseInt(splited[1]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        loadBtn = (Button) findViewById(R.id.loadBtn);
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (invh.isEmpty()) {
                    makeToast(self, toasts[2]);
                    return;
                }
                banner.setInventoryHash(invh);
                banner.load();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        invh = onResult(requestCode, resultCode, data);
        if (invh.equals("false")) {
            makeToast(self, toasts[0]);
            return;
        }
        etInvh.setText(invh);
        makeToast(self, toasts[1] + invh);
    }

    public static void makeToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        banner.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onPause() {
        super.onPause();
        banner.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        banner.onResume();
    }

    static public void setDimensions(View view, int width, int height) {
        try {
            Context context = view.getContext();
            RelativeLayout.LayoutParams layout_params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            layout_params.width = LayoutUtils.convertDpToPixel(width, context);
            layout_params.height = LayoutUtils.convertDpToPixel(height, context);
            view.setLayoutParams(layout_params);
            makeToast(context, "width: " + width + ", height: " + height);
        } catch (Exception e) {
            Log.d(tag, e.toString());
        }
    }
}
