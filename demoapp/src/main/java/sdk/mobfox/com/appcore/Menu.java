package sdk.mobfox.com.appcore;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by asafg84 on 28/02/16.
 */
public class Menu extends ListActivity {

    static String cheesePackage = "sdk.mobfox.com.appcore";

    String[] classes = {"UseNativeAd", "UseInterstitialAd", "UseBannerAd", "RTBView", "CustomSizeBanner"};
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
    }
}
