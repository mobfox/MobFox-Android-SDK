import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CordovaInterface;
import android.provider.Settings;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mobfox.sdk.bannerads.Banner;
import com.mobfox.sdk.bannerads.BannerListener;
import com.mobfox.sdk.interstitialads.InterstitialAd;
import com.mobfox.sdk.interstitialads.InterstitialAdListener;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MobFoxPlugin extends CordovaPlugin {

	public static final String TAG = "MobFox"; 

	private Banner         mBanner       = null;
	private InterstitialAd mInterstitial = null;

    private static Context      mContext;
    private static MobFoxPlugin instance;
    
    private static CordovaWebView  mCordovaView;
    private CallbackContext mBannerCallback = null;
    private CallbackContext mInterstitialCallback = null;

	//#############################################################
	//### M I S C                                               ###
	//#############################################################

    public static int CalcDPIToReal(Context ctx, int dp) {
	    DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
	    return (int)(((float)dp * metrics.density) + 0.5);
	}

	private void MyToast(final String text)
	{
		final int duration = Toast.LENGTH_SHORT;
    	cordova.getActivity().runOnUiThread(new Runnable() {
			public void run() {
				Toast toast = Toast.makeText(cordova.getActivity().getApplicationContext(), text, Toast.LENGTH_SHORT);
				toast.show();
			}
		});
	}	
	
	//#############################################################
	//### I N I T                                               ###
	//#############################################################

	public MobFoxPlugin() {
    	MobFoxPlugin.instance = this;
	}
 
    public static MobFoxPlugin instance() {
        if(instance == null) {
            instance = new MobFoxPlugin();
        }
        return instance;
    }
 
    public void setContext(Context context) {
    	MobFoxPlugin.mContext = context;
    }

	//#############################################################
	//### C O R D O V A                                         ###
	//#############################################################

	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		Log.v(TAG,"Init MobFoxPlugin");
		
		MobFoxPlugin.mContext     = cordova.getActivity();
		MobFoxPlugin.mCordovaView = webView;
	}

	public boolean execute(final String action, JSONArray args, CallbackContext callbackContext) throws JSONException
	{
    	if (action.equals("showBanner")) 
    	{
    		mBannerCallback = callbackContext;
    	
    		JSONObject dict = new JSONObject(args.getString(0));
    		if (dict!=null)
    		{
	        	final String hash = dict.getString("hash");
    	    	final int    x    = dict.getInt("x");
        		final int    y    = dict.getInt("y");
        		final int    w    = dict.getInt("w");
        		final int    h    = dict.getInt("h");
       
	           	cordova.getActivity().runOnUiThread(new Runnable() {
				public void run() {
	        		showBanner(hash, x, y, w, h);
					}
				});
 	
        		return true;
    		}
    	}
    	
    	if (action.equals("createInterstitial")) 
    	{
    		mInterstitialCallback = callbackContext;
    	
    		JSONObject dict = new JSONObject(args.getString(0));
    		if (dict!=null)
    		{
	        	final String hash = dict.getString("hash");
	        
	           	cordova.getActivity().runOnUiThread(new Runnable() {
				public void run() {
	        		createInterstitial(hash);
					}
				});
	        	return true;
	        }
    	}
    	
    	if (action.equals("showInterstitial")) 
    	{
    		mInterstitialCallback = callbackContext;
    	
	           	cordova.getActivity().runOnUiThread(new Runnable() {
				public void run() {
		        	showInterstitial();
					}
				});
        	return true;
    	}
        
        if (action.equals("showToast"))
        {
            JSONObject dict = new JSONObject(args.getString(0));
            if (dict!=null)
            {
                String text = dict.getString("text");
                
                MyToast(text);
                return true;
            }
        }
	
    	return true;
	}
	
	private void SendCallback(CallbackContext trgCallback, boolean bOK, String text)
	{
		if (trgCallback!=null)
		{
			PluginResult pluginResult;
			
			if (bOK)
			{
				pluginResult = new PluginResult(PluginResult.Status.OK, text);
			} else {
				pluginResult = new PluginResult(PluginResult.Status.ERROR, text);
			}
			pluginResult.setKeepCallback(true);
			trgCallback.sendPluginResult(pluginResult);
		}
	}

    //#############################################################
	//### B A N N E R                                           ###
	//#############################################################

	private void showBanner(String myHash, int in_x, int in_y, int in_w, int in_h)
	{
		if (MobFoxPlugin.mContext==null) return;

    	int x = MobFoxPlugin.CalcDPIToReal(MobFoxPlugin.mContext, in_x);
    	int y = MobFoxPlugin.CalcDPIToReal(MobFoxPlugin.mContext, in_y);
    	int w = MobFoxPlugin.CalcDPIToReal(MobFoxPlugin.mContext, in_w);
    	int h = MobFoxPlugin.CalcDPIToReal(MobFoxPlugin.mContext, in_h);
    	
    	if (mBanner!=null)
    	{
    		mBanner = null;
    	}
    	
        mBanner = new Banner(MobFoxPlugin.mContext, in_w, in_h);
        if (mBanner!=null)
        {
    		final View v1 = ((Activity) MobFoxPlugin.mContext).getWindow().getDecorView().getRootView();
    		//final View v1 = MobFoxPlugin.mCordovaView;
    		if (v1!=null)
    		{
    	    	RelativeLayout.LayoutParams parentParameters = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,RelativeLayout.LayoutParams.FILL_PARENT);
				RelativeLayout rel = new RelativeLayout(MobFoxPlugin.mContext);
    			((ViewGroup)v1).addView(rel,parentParameters);

    	    	RelativeLayout.LayoutParams bannerParameters = new RelativeLayout.LayoutParams(w,h);
    	    	bannerParameters.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
    	    	bannerParameters.addRule(RelativeLayout.ALIGN_PARENT_TOP);   
    	    	bannerParameters.setMargins(x, y, 0, 0);

    			((ViewGroup)rel).addView(mBanner,bannerParameters);
   			}
        	
    		mBanner.setListener(mBannerListener);

    		mBanner.setInventoryHash(myHash);
  
    		mBanner.load();
        }
	}

	//=======================================================================
	
	private BannerListener mBannerListener = new BannerListener() {
        @Override
        public void onBannerError(View view, Exception e) {

        	Log.e(TAG,"dbg: ### onBannerError: "+e.getMessage());

        	if (MobFoxPlugin.mContext==null) return;
            
            SendCallback(mBannerCallback, false, e.getMessage());
        }

        @Override
        public void onBannerLoaded(View view) {

        	Log.v(TAG,"dbg: ### onBannerLoaded ###");
        	
        	if (MobFoxPlugin.mContext==null) return;
        	
            SendCallback(mBannerCallback, true, "onBannerLoaded");
        }

        @Override
        public void onBannerClosed(View view) {

        	Log.v(TAG,"dbg: ### onBannerClosed ###");

        	if (MobFoxPlugin.mContext==null) return;
        	
            SendCallback(mBannerCallback, true, "onBannerClosed");
        }

        @Override
        public void onBannerClicked(View view) {

        	Log.v(TAG,"dbg: ### onBannerClicked ###");

        	if (MobFoxPlugin.mContext==null) return;
        	
            SendCallback(mBannerCallback, true, "onBannerClicked");
        }

		@Override
		public void onBannerFinished() {
        	Log.v(TAG,"dbg: ### onBannerFinished ###");

        	if (MobFoxPlugin.mContext==null) return;
        	
            SendCallback(mBannerCallback, true, "onBannerFinished");
		}

		@Override
		public void onNoFill(View banner) {
        	Log.e(TAG,"dbg: ### onNoFill ###");

        	if (MobFoxPlugin.mContext==null) return;
        	
            SendCallback(mBannerCallback, false, "No fill");
		}
    };
	

	//#############################################################
	//### I N T E R S T I T I A L                               ###
	//#############################################################

	private void createInterstitial(String myHash)
	{
    	if (MobFoxPlugin.mContext==null) return;
    	
       	if (mInterstitial!=null)
       	{
       		mInterstitial = null;
       	}
       	
		mInterstitial = new InterstitialAd(MobFoxPlugin.mContext);
		
		if (mInterstitial!=null)
		{
	    	mInterstitial.setListener(mInterstitialListener);

	    	mInterstitial.setInventoryHash(myHash);

	    	mInterstitial.load();
		}
	}

	private void showInterstitial()
	{
    	if (MobFoxPlugin.mContext==null) return;
    	if (mInterstitial==null) return;
    	
    	mInterstitial.show();
	}

    //=======================================================================
	
	private InterstitialAdListener mInterstitialListener = new InterstitialAdListener() {

		@Override
		public void onInterstitialLoaded(InterstitialAd interstitial) {
        	Log.v(TAG,"dbg: ### onInterstitialLoaded ###");

        	if (MobFoxPlugin.mContext==null) return;	    	
        	
            SendCallback(mInterstitialCallback, true, "onInterstitialLoaded");
            
            //showInterstitial();
		}

		@Override
		public void onInterstitialFailed(InterstitialAd interstitial, Exception e) {
        	Log.v(TAG,"dbg: ### onInterstitialFailed: "+e.getMessage());

        	if (MobFoxPlugin.mContext==null) return;	    	
        	
            SendCallback(mInterstitialCallback, false, e.getMessage());
		}

		@Override
		public void onInterstitialClosed(InterstitialAd interstitial) {
        	Log.v(TAG,"dbg: ### onInterstitialClosed ###");

        	if (MobFoxPlugin.mContext==null) return;
        	
            SendCallback(mInterstitialCallback, true, "onInterstitialClosed");
		
        	mInterstitial = null;
		}

		@Override
		public void onInterstitialFinished() {
        	Log.v(TAG,"dbg: ### onInterstitialFinished ###");

        	if (MobFoxPlugin.mContext==null) return;
        	
            SendCallback(mInterstitialCallback, true, "onInterstitialFinished");
		}

		@Override
		public void onInterstitialClicked(InterstitialAd interstitial) {
        	Log.v(TAG,"dbg: ### onInterstitialClicked ###");

        	if (MobFoxPlugin.mContext==null) return;
        	
            SendCallback(mInterstitialCallback, true, "onInterstitialClicked");
		}

		@Override
		public void onInterstitialShown(InterstitialAd interstitial) {
        	Log.v(TAG,"dbg: ### onInterstitialShown ###");

        	if (MobFoxPlugin.mContext==null) return;
        	
            SendCallback(mInterstitialCallback, true, "onInterstitialShown");
		}
    };

	//=================================================================================================
}
