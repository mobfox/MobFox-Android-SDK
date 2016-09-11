package com.mobfox.cordova.plugin;


package org.apache.cordova.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.DisplayMetrics;
import android.view.View;

import com.adsdk.sdk.Ad;
import com.adsdk.sdk.AdManager;
import com.adsdk.sdk.banner.AdView;
import com.adsdk.sdk.AdListener;
import com.google.android.gms.ads.AdSize;

public class MobFoxPlugin extends CordovaPlugin {

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException
	{
    	/*
	cordova.exec( successCallback, failureCallback, 'MobFoxPlugin', 'createBanner', [{hash:hash, x:x, y:y, w:w, h:h}] );
	cordova.exec( successCallback, failureCallback, 'MobFoxPlugin', 'showBanner', [] );
	cordova.exec( successCallback, failureCallback, 'MobFoxPlugin', 'hideBanner', [] );
	cordova.exec( successCallback, failureCallback, 'MobFoxPlugin', 'createInterstitial', [ {hash:hash} ] );
	cordova.exec( successCallback, failureCallback, 'MobFoxPlugin', 'showInterstitial', [] );
    	*/
    	
    	if (action.equals("createBanner")) 
    	{
        	String hash = args.getString(0);
        	int    x    = args.getInt(1);
        	int    y    = args.getInt(2);
        	int    w    = args.getInt(3);
        	int    h    = args.getInt(4);
        	
        	this.createBanner(hash, x, y, w, h, callbackContext);
        	return true;
    	}
    	
    	if (action.equals("showBanner")) 
    	{
        	this.showBanner(callbackContext);
        	return true;
    	}
    	
    	if (action.equals("hideBanner")) 
    	{
        	this.hideBanner(callbackContext);
        	return true;
    	}
    	
    	if (action.equals("createInterstitial")) 
    	{
        	String hash = args.getString(0);

        	this.createInterstitial(hash, callbackContext);
        	return true;
    	}
    	
    	if (action.equals("showInterstitial")) 
    	{
        	this.showInterstitial(callbackContext);
        	return true;
    	}

    	return false;
	}

	//=================================================================================================

	private void createBanner(String hash, int x, int y, int w, int h, CallbackContext callbackContext)
	{
    	if (hash != null && hash.length() > 0) 
    	{
        	callbackContext.success(hash);
    	} else {
        	callbackContext.error("Expected one non-empty string argument.");
    	}
	}

	private void showBanner(CallbackContext callbackContext)
	{
        callbackContext.success("showed");
	}

	private void hideBanner(String hash, int x, int y, int w, int h, CallbackContext callbackContext)
	{
        callbackContext.success("hidden");
	}

	private void createInterstitial(String hash, CallbackContext callbackContext)
	{
    	if (hash != null && hash.length() > 0) 
    	{
        	callbackContext.success(hash);
    	} else {
        	callbackContext.error("Expected one non-empty string argument.");
    	}
	}

	private void showInterstitial(CallbackContext callbackContext)
	{
        callbackContext.success("showed");
	}

    @Override
    protected void pluginInitialize() {
    	super.pluginInitialize();
    	
    	DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenDensity = metrics.density;
	}

	@Override
	protected String __getProductShortName() {
		return "MobFoxPlugin";
	}
	
	@Override
	protected View __createAdView(String adId) {
		if(isTesting) adId = TEST_ID_BANNER;
		
		AdView ad = new AdView(getActivity(), MOBFOX_SERVER_URL, adId, true, true);
		
        if(adSize != null) {
    		adWidth = adSize.getWidth();
    		adHeight = adSize.getHeight();
    		
    		if(adWidth < 0) {
    			adWidth = (int)(getView().getWidth() / screenDensity);
    		}
    		if(adHeight < 0) {
    			adHeight = (adWidth > 360) ? 90 : 50;
    		}
        } 
    		
        ad.setAdspaceWidth(adWidth);
        ad.setAdspaceHeight(adHeight);
        ad.setAdspaceStrict(false);

        ad.setAdListener(new AdListener(){
    		@Override
    		public void adClicked() {
            	fireAdEvent(EVENT_AD_LEAVEAPP, ADTYPE_BANNER);
    		}

    		@Override
    		public void adClosed(Ad arg0, boolean arg1) {
            	fireAdEvent(EVENT_AD_DISMISS, ADTYPE_BANNER);
    		}

    		@Override
    		public void adLoadSucceeded(Ad arg0) {
    			if((! bannerVisible) && autoShowBanner) {
    				showBanner(adPosition, posX, posY);
    			}
            	fireAdEvent(EVENT_AD_LOADED, ADTYPE_BANNER);
    		}

    		@Override
    		public void adShown(Ad arg0, boolean arg1) {
            	fireAdEvent(EVENT_AD_PRESENT, ADTYPE_BANNER);
    		}

    		@Override
    		public void noAdFound() {
            	fireAdErrorEvent(EVENT_AD_FAILLOAD, -1, "No Ad found", ADTYPE_BANNER);
    		}
        });
        
		return ad;
	}

	@Override
	protected int __getAdViewWidth(View view) {
		return view.getWidth();
	}

	@Override
	protected int __getAdViewHeight(View view) {
		return view.getHeight();
	}

	@Override
	protected void __loadAdView(View view) {
		if(view instanceof AdView) {
			AdView ad = (AdView)view;
			ad.loadNextAd();
		}
	}

	@Override
	protected void __pauseAdView(View view) {
		if(view instanceof AdView) {
			AdView ad = (AdView)view;
			ad.pause();
		}
	}

	@Override
	protected void __resumeAdView(View view) {
		if(view instanceof AdView) {
			AdView ad = (AdView)view;
			ad.resume();
		}
	}

	@Override
	protected void __destroyAdView(View view) {
		if(view instanceof AdView) {
			AdView ad = (AdView)view;
			ad.release();
		}
	}

	@Override
	protected Object __createInterstitial(String adId) {
		if(isTesting) {
			adId = enableVideo ? TEST_ID_VIDEO : TEST_ID_INTERSTITIAL;
		}
		
		AdManager ad = new AdManager(getActivity(), MOBFOX_SERVER_URL, adId, true);
		ad.setInterstitialAdsEnabled( true );
        ad.setVideoAdsEnabled( enableVideo );
        ad.setPrioritizeVideoAds( enableVideo );
        ad.setListener(new AdListener(){
    		@Override
    		public void adClicked() {
            	fireAdEvent(EVENT_AD_LEAVEAPP, ADTYPE_INTERSTITIAL);
    		}

    		@Override
    		public void adClosed(Ad arg0, boolean arg1) {
            	fireAdEvent(EVENT_AD_DISMISS, ADTYPE_INTERSTITIAL);
    		}

    		@Override
    		public void adLoadSucceeded(Ad arg0) {
            	fireAdEvent(EVENT_AD_LOADED, ADTYPE_INTERSTITIAL);
    			if(autoShowInterstitial) {
    				showInterstitial();
    			}
    		}

    		@Override
    		public void adShown(Ad arg0, boolean arg1) {
            	fireAdEvent(EVENT_AD_PRESENT, ADTYPE_INTERSTITIAL);
    		}

    		@Override
    		public void noAdFound() {
            	fireAdErrorEvent(EVENT_AD_FAILLOAD, -1, "No Ad found", ADTYPE_INTERSTITIAL);
    		}
        });
        
        return ad;
	}

	@Override
	protected void __loadInterstitial(Object interstitial) {
		if(interstitial instanceof AdManager) {
			AdManager ad = (AdManager) interstitial;
			ad.requestAd();
		}
	}

	@Override
	protected void __showInterstitial(Object interstitial) {
		if(interstitial instanceof AdManager) {
			AdManager ad = (AdManager) interstitial;
			if(ad.isAdLoaded()) ad.showAd();
		}
	}

	@Override
	protected void __destroyInterstitial(Object interstitial) {
		if(interstitial instanceof AdManager) {
			AdManager ad = (AdManager) interstitial;
			ad.release();
		}
	}
}
