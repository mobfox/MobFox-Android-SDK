package sdk.mobfox.com.appcore;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobfox.sdk.nativeads.Native;
import com.mobfox.sdk.nativeads.NativeListener;
import com.mobfox.sdk.networking.RequestParams;

import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by asafg84 on 03/05/16.
 */
public class UseNativeAd extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    String invh = "";

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    static final String[] toasts = {"scan unsuccessful", "invh: ", "invh field empty", "key field empty", "value field empty"};
    static final String tag = "MobFoxAppLog";

    MySpinner spinnerInvh;
    MySpinner spinnerkeys;
    EditText etInvh;
    Button loadBtn;
    Button btnAdd;
    EditText etvalue;
    TextView tvParams;
    boolean first = true;
    RequestParams params = new RequestParams();

    private Native aNative;

    private NativeListener listener;

    UseNativeAd self;

    TextView headline;
    ImageView nativeIcon, nativeMainImg;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_native);

        self = this;

//        Native.setDebug(true);

        //assigning xml components to our layout
        headline = (TextView) findViewById(R.id.headline);
        nativeIcon = (ImageView) findViewById(R.id.nativeIcon);
        nativeMainImg = (ImageView) findViewById(R.id.nativeMainImg);
        layout = (RelativeLayout) findViewById(R.id.nativeLayout);

        spinnerInvh = (MySpinner) findViewById(R.id.spinnerInvh);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinnerinvh, android.R.layout.simple_spinner_item);
        spinnerInvh.setAdapter(adapter);
        spinnerInvh.setOnItemSelectedListener(this);

        spinnerkeys = (MySpinner) findViewById(R.id.spinnerkeys);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.spinnerkeys, android.R.layout.simple_spinner_item);
        spinnerkeys.setAdapter(adapter);

        loadBtn = (Button) findViewById(R.id.loadBtn);
        loadBtn.setOnClickListener(self);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(self);

        etvalue = (EditText) findViewById(R.id.etvalue);

        tvParams = (TextView) findViewById(R.id.tvParams);

        etInvh = (EditText) findViewById(R.id.etInvh);

        Native.setLoc(true);
        aNative = new Native(this);

        params.setParams(new JSONObject());

        //we must set a listener for native

       /* listener = new NativeListener() {
            @Override
            public void onNativeReady(Native aNative, CustomEventNative event, NativeAd ad) {

                Toast.makeText(self, "on native ready", Toast.LENGTH_SHORT).show();

                //register custom layout click
                event.registerViewForInteraction(layout);
                ad.fireTrackers(self);

                headline.setText(ad.getTexts().get(0).getText());

                ad.loadImages(self, new NativeAd.ImagesLoadedListener() {
                    @Override
                    public void onImagesLoaded(NativeAd ad) {
                        Toast.makeText(self, "on images ready", Toast.LENGTH_SHORT).show();
                        nativeIcon.setImageBitmap(ad.getImages().get(0).getImg());
                    }
                });

            }*/

          /*  @Override
            public void onNativeError(Exception e) {
                Toast.makeText(self, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNativeClick(NativeAd ad) {
                Toast.makeText(self, "on native click", Toast.LENGTH_SHORT).show();
            }

        };*/

       // aNative.setListener(listener);
    }

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
                showDialog(self, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
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

    public static AlertDialog showDialog(final Context context, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo ) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(context);
        downloadDialog
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(buttonYes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Uri uri = Uri.parse("market://search?q=pname:com.google.zxing.client.android");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                try {
                                    context.startActivity(intent);
                                } catch (ActivityNotFoundException e) {

                                }
                            }
                        }).setNegativeButton(buttonNo,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        return downloadDialog.show();
    }

    public static String onResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                String format = data.getStringExtra("SCAN_RESULT_FORMAT");
                return contents;
            }
        }
        return "false";
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
    public void onClick(View v) {


        if (v.getId() == R.id.loadBtn) {
            if (invh.isEmpty()) {
                makeToast(self, toasts[2]);
                return;
            }
            aNative.load(invh);
            //"f129d39a980e6102b68dfcefe78c8832"
            return;
        }
        if (v.getId() == R.id.btnAdd) {
//            String key = etkey.getText().toString();
            String key = spinnerkeys.getSelectedItem().toString();
            if (key.isEmpty()) {
                makeToast(self, toasts[3]);
                return;
            }
            String value = etvalue.getText().toString();
            if (value.isEmpty()) {
                makeToast(self, toasts[4]);
                return;
            }
            aNative.params.setParam(key, value);
            params.setParam(key, value);

            boolean firstparam = true;
            String q = "";
            Iterator it = params.getNames();
            while (it.hasNext()) {
                String thiskey = (String) it.next();
                String thisval = (String) params.getParam(thiskey);
                if (firstparam) {
                    q += "  ?" + thiskey.replace(" ", "") + "=" + thisval.replace(" ", "");
                    firstparam = false;
                    continue;
                }
                q += "&" + thiskey.replace(" ", "") + "=" + thisval.replace(" ", "");
            }

            final String query = q;
            new Handler(self.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    tvParams.setText(query);
                }
            });
        }
    }
}