package sdk.mobfox.com.appcore;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobfox.sdk.interstitialads.InterstitialAd;
import com.mobfox.sdk.interstitialads.InterstitialAdListener;

import static sdk.mobfox.com.appcore.UseNativeAd.ACTION_SCAN;
import static sdk.mobfox.com.appcore.UseNativeAd.makeToast;
import static sdk.mobfox.com.appcore.UseNativeAd.onResult;
import static sdk.mobfox.com.appcore.UseNativeAd.toasts;

/**
 * Created by asafg84 on 03/05/16.
 */
public class UseInterstitialAd extends Activity {

    String invh = "";

    InterstitialAd interstitial;
    InterstitialAdListener listener;

    MySpinner spinnerInvh;
    EditText etInvh;
    Button loadBtn;

    boolean first = true;

    UseInterstitialAd self;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_interstitial);

        InterstitialAd.getLocation(true);
        interstitial = new InterstitialAd(this);

        self = this;

        listener = new InterstitialAdListener() {
            @Override
            public void onInterstitialLoaded(InterstitialAd interstitial) {
                interstitial.show();
                Toast.makeText(UseInterstitialAd.this, "load", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInterstitialFailed(InterstitialAd interstitial, Exception e) {
                Toast.makeText(UseInterstitialAd.this, "fail, " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInterstitialClosed(InterstitialAd interstitial) {
                Toast.makeText(UseInterstitialAd.this, "close", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInterstitialFinished() {
                Toast.makeText(UseInterstitialAd.this, "finish", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInterstitialClicked(InterstitialAd interstitial) {
                Toast.makeText(UseInterstitialAd.this, "click", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInterstitialShown(InterstitialAd interstitial) {
                Toast.makeText(UseInterstitialAd.this, "show", Toast.LENGTH_SHORT).show();
            }
        };
        interstitial.setListener(listener);

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

        loadBtn = (Button) findViewById(R.id.loadBtn);
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (invh.isEmpty()) {
                    makeToast(self, toasts[2]);
                    return;
                }
                interstitial.setInventoryHash(invh);
                interstitial.load();
                return;
            }
        });
    }

    //need to add this so video ads will work properly

    @Override
    protected void onPause() {
        super.onPause();
        interstitial.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        interstitial.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        interstitial.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}