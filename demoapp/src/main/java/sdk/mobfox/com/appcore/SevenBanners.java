package sdk.mobfox.com.appcore;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mobfox.sdk.bannerads.Banner;
import com.mobfox.sdk.bannerads.BannerListener;

/**
 * Created by asafgabar on 12/21/16.
 */

public class SevenBanners extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seven_banners);

        String invh = "fe96717d9875b9da4339ea5367eff1ec";

        Banner banner1 = new Banner(this, 320, 50);
        Banner banner2 = new Banner(this, 320, 50);
        Banner banner3 = new Banner(this, 320, 50);
        Banner banner4 = new Banner(this, 320, 50);
        Banner banner5 = new Banner(this, 320, 50);
        Banner banner6 = new Banner(this, 320, 50);
        Banner banner7 = new Banner(this, 320, 50);

        banner1.setInventoryHash(invh);
        banner1.setListener(new BannerListener() {
            @Override
            public void onBannerError(View banner, Exception e) {
                Log.d("tag", "e " + e.toString());
            }

            @Override
            public void onBannerLoaded(View banner) {
                Log.d("tag", "loaded");
            }

            @Override
            public void onBannerClosed(View banner) {
            }

            @Override
            public void onBannerFinished() {
            }

            @Override
            public void onBannerClicked(View banner) {
            }

            @Override
            public void onNoFill(View banner) {
            }
        });

        banner2.setInventoryHash(invh);
        banner2.setListener(new BannerListener() {
            @Override
            public void onBannerError(View banner, Exception e) {
                Log.d("tag", "e " + e.toString());

            }

            @Override
            public void onBannerLoaded(View banner) {
                Log.d("tag", "loaded");
            }

            @Override
            public void onBannerClosed(View banner) {

            }

            @Override
            public void onBannerFinished() {

            }

            @Override
            public void onBannerClicked(View banner) {

            }

            @Override
            public void onNoFill(View banner) {

            }
        });

        banner3.setInventoryHash(invh);
        banner3.setListener(new BannerListener() {
            @Override
            public void onBannerError(View banner, Exception e) {
                Log.d("tag", "e " + e.toString());

            }

            @Override
            public void onBannerLoaded(View banner) {
                Log.d("tag", "loaded");
            }

            @Override
            public void onBannerClosed(View banner) {

            }

            @Override
            public void onBannerFinished() {

            }

            @Override
            public void onBannerClicked(View banner) {

            }

            @Override
            public void onNoFill(View banner) {

            }
        });

        banner4.setInventoryHash(invh);
        banner4.setListener(new BannerListener() {
            @Override
            public void onBannerError(View banner, Exception e) {
                Log.d("tag", "e " + e.toString());

            }

            @Override
            public void onBannerLoaded(View banner) {
                Log.d("tag", "loaded");
            }

            @Override
            public void onBannerClosed(View banner) {

            }

            @Override
            public void onBannerFinished() {

            }

            @Override
            public void onBannerClicked(View banner) {

            }

            @Override
            public void onNoFill(View banner) {

            }
        });

        banner5.setInventoryHash(invh);
        banner5.setListener(new BannerListener() {
            @Override
            public void onBannerError(View banner, Exception e) {
                Log.d("tag", "e " + e.toString());

            }

            @Override
            public void onBannerLoaded(View banner) {
                Log.d("tag", "loaded");
            }

            @Override
            public void onBannerClosed(View banner) {

            }

            @Override
            public void onBannerFinished() {

            }

            @Override
            public void onBannerClicked(View banner) {

            }

            @Override
            public void onNoFill(View banner) {

            }
        });

        banner6.setInventoryHash(invh);
        banner6.setListener(new BannerListener() {
            @Override
            public void onBannerError(View banner, Exception e) {
                Log.d("tag", "e " + e.toString());

            }

            @Override
            public void onBannerLoaded(View banner) {
                Log.d("tag", "loaded");
            }

            @Override
            public void onBannerClosed(View banner) {

            }

            @Override
            public void onBannerFinished() {

            }

            @Override
            public void onBannerClicked(View banner) {

            }

            @Override
            public void onNoFill(View banner) {

            }
        });

        banner7.setInventoryHash(invh);
        banner7.setListener(new BannerListener() {
            @Override
            public void onBannerError(View banner, Exception e) {
                Log.d("tag", "e " + e.toString());
            }

            @Override
            public void onBannerLoaded(View banner) {
                Log.d("tag", "loaded");
            }

            @Override
            public void onBannerClosed(View banner) {

            }

            @Override
            public void onBannerFinished() {

            }

            @Override
            public void onBannerClicked(View banner) {

            }

            @Override
            public void onNoFill(View banner) {

            }
        });

        banner1.load();
        banner2.load();
        banner3.load();
        banner4.load();
        banner5.load();
        banner6.load();
        banner7.load();

    }
}
