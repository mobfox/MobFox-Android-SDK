//
//  MobFoxPlugin.m
//  TestAdPlugins
//
//  Created by Xie Liming on 14-11-11.
//
//

#import "MobFoxPlugin.h"

#import <MobFox/MobFox.h>

#define MOBFOX_SERVER_URL       @"http://my.mobfox.com/request.php"
#define TEST_PUBLISHER_ID       @"3b7e06658db07c264f841f65a60eaf94"
#define TEST_ID_BANNER          @"fe96717d9875b9da4339ea5367eff1ec"
#define TEST_ID_INTERSTITIAL    @"fe96717d9875b9da4339ea5367eff1ec"
#define TEST_ID_NATIVE          @"80187188f458cfde788d961b6882fd53"
#define TEST_ID_VIDEO           @"80187188f458cfde788d961b6882fd53"

#define OPT_ENABLE_VIDEO        @"enableVideo"

@interface MobFoxPlugin()<MobFoxBannerViewDelegate, MobFoxVideoInterstitialViewControllerDelegate>

@property (assign) CGSize adSize;
@property (assign) BOOL enableVideo;

@property (assign) MobFoxAdType adTypeLoaded;

- (CGSize) __AdSizeFromString:(NSString*)str;

@end



@implementation MobFoxPlugin

- (void)pluginInitialize
{
    [super pluginInitialize];

    self.adSize = CGSizeMake(320, 50);
    self.enableVideo = true;
    self.adTypeLoaded = MobFoxAdTypeUnknown;
}

- (void) parseOptions:(NSDictionary *)options
{
    [super parseOptions:options];
    
    NSString* str = [options objectForKey:OPT_ENABLE_VIDEO];
    if(str) self.enableVideo = [str boolValue];
    
    str = [options objectForKey:OPT_AD_SIZE];
    if(str) {
        if([str isEqualToString:@"CUSTOM"]) {
            CGSize sz = {320,50};
            str = [options objectForKey:OPT_AD_WIDTH];
            if(str) sz.width = [str intValue];
            str = [options objectForKey:OPT_AD_HEIGHT];
            if(str) sz.height = [str intValue];
            self.adSize = sz;
            
        } else {
            self.adSize = [self __AdSizeFromString:str];
        }
    }
}

- (CGSize) __AdSizeFromString:(NSString*)str
{
    CGSize sz = {320,50};
    if ([str isEqualToString:@"BANNER"]) {
        sz.width = 320;
        sz.height = 50;
    } else if ([str isEqualToString:@"MEDIUM_RECTANGLE"]) {
        sz.width = 300;
        sz.height = 250;
    } else if ([str isEqualToString:@"FULL_BANNER"]) {
        sz.width = 468;
        sz.height = 60;
    } else if ([str isEqualToString:@"LEADERBOARD"]) {
        sz.width = 728;
        sz.height = 90;
    } else if ([str isEqualToString:@"SKYSCRAPER"]) {
        sz.width = 120;
        sz.height = 600;
    } else {
        //} else if ([adSize isEqualToString:@"SMART_BANNER"]) {
        BOOL isPad = (UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad);
        CGRect rect = [[self getView] bounds];
        sz.width = rect.size.width;
        // Have to choose the right Smart Banner constant according to orientation.
        if([self __isLandscape]) {
            sz.height = isPad ? 90 : 32;
        } else {
            sz.height = isPad ? 90 : 50;
        }
    }
    return sz;
}

- (NSString*) __getProductShortName
{
    return @"MobFox";
}

- (NSString*) __getTestBannerId
{
    return TEST_PUBLISHER_ID;
}

- (NSString*) __getTestInterstitialId
{
    return TEST_PUBLISHER_ID;
}

- (NSString*)publisherIdForMobFoxBannerView:(MobFoxBannerView *)banner {
    return self.bannerId;
}

- (NSString *)publisherIdForMobFoxVideoInterstitialView:(MobFoxVideoInterstitialViewController *)videoInterstitial {
    return self.interstitialId;
}

- (UIView*) __createAdView:(NSString*)adId
{
    if(self.isTesting) adId = TEST_ID_BANNER;
    
    self.bannerId = adId;
    
    MobFoxBannerView* ad = [[MobFoxBannerView alloc] initWithFrame:CGRectZero];
    ad.allowDelegateAssigmentToRequestAd = NO;
    ad.adspaceWidth = self.adSize.width;
    ad.adspaceHeight = self.adSize.height;
    ad.adspaceStrict = NO;
    ad.delegate = self;

    return ad;
}

- (int) __getAdViewWidth:(UIView*)view
{
    if([view isKindOfClass:[MobFoxBannerView class]]) {
        MobFoxBannerView* ad = (MobFoxBannerView*) view;
        return ad.frame.size.width;
    }
    return 320;
}

- (int) __getAdViewHeight:(UIView*)view
{
    if([view isKindOfClass:[MobFoxBannerView class]]) {
        MobFoxBannerView* ad = (MobFoxBannerView*) view;
        return ad.frame.size.height;
    }
    return 50;
}

- (void) __loadAdView:(UIView*)view
{
    if([view isKindOfClass:[MobFoxBannerView class]]) {
        MobFoxBannerView* ad = (MobFoxBannerView*) view;
        ad.requestURL = MOBFOX_SERVER_URL;
        [ad requestAd];
    }
}

- (void) __pauseAdView:(UIView*)view
{
    if([view isKindOfClass:[MobFoxBannerView class]]) {
        //MobFoxBannerView* ad = (MobFoxBannerView*) view;
        //[ad pause];
    }
}

- (void) __resumeAdView:(UIView*)view
{
    if([view isKindOfClass:[MobFoxBannerView class]]) {
        //MobFoxBannerView* ad = (MobFoxBannerView*) view;
        //[ad resume];
    }
}

- (void) __destroyAdView:(UIView*)view
{
    if([view isKindOfClass:[MobFoxBannerView class]]) {
        MobFoxBannerView* ad = (MobFoxBannerView*) view;
        ad.delegate = nil;
        //[ad destroy];
    }
}

- (NSObject*) __createInterstitial:(NSString*)adId
{
    if(self.isTesting) {
        if(self.enableVideo) {
            adId = TEST_ID_VIDEO;
        } else {
            adId = TEST_ID_INTERSTITIAL;
        }
    }
    
    self.interstitialId = adId;
    
    MobFoxVideoInterstitialViewController* ad = [[MobFoxVideoInterstitialViewController alloc] init];
    ad.delegate = self;
    
    UIWindow *window = [UIApplication sharedApplication].keyWindow;
    [window.rootViewController.view addSubview:ad.view];
    
    ad.enableInterstitialAds = YES;
    if(self.enableVideo) {
        ad.enableVideoAds = YES;
        ad.prioritizeVideoAds = YES;
    }
    
    return ad;
}

- (void) __loadInterstitial:(NSObject*)interstitial
{
    if([interstitial isKindOfClass:[MobFoxVideoInterstitialViewController class]]) {
        MobFoxVideoInterstitialViewController* ad = (MobFoxVideoInterstitialViewController*) interstitial;
        ad.requestURL = MOBFOX_SERVER_URL;
        [ad requestAd];
    }
}

- (void) __showInterstitial:(NSObject*)interstitial
{
    if([interstitial isKindOfClass:[MobFoxVideoInterstitialViewController class]]) {
        MobFoxVideoInterstitialViewController* ad = (MobFoxVideoInterstitialViewController*) interstitial;
        [ad presentAd:self.adTypeLoaded];
    }
}

- (void) __destroyInterstitial:(NSObject*)interstitial
{
    if([interstitial isKindOfClass:[MobFoxVideoInterstitialViewController class]]) {
        MobFoxVideoInterstitialViewController* ad = (MobFoxVideoInterstitialViewController*) interstitial;
        ad.delegate = nil;
        //[ad destroy];
    }
}

- (void)mobfoxBannerViewDidLoadMobFoxAd:(MobFoxBannerView *)banner {
    [self fireAdEvent:EVENT_AD_LOADED withType:ADTYPE_BANNER];
    if((! self.bannerVisible) && self.autoShowBanner) {
        [self __showBanner:self.adPosition atX:self.posX atY:self.posY];
    }
}

- (void)mobfoxBannerViewDidLoadRefreshedAd:(MobFoxBannerView *)banner {
    [self fireAdEvent:EVENT_AD_LOADED withType:ADTYPE_BANNER];
    if((! self.bannerVisible) && self.autoShowBanner) {
        dispatch_async(dispatch_get_main_queue(), ^{
            [self __showBanner:self.adPosition atX:self.posX atY:self.posY];
        });
    }
}

- (void)mobfoxBannerView:(MobFoxBannerView *)banner didFailToReceiveAdWithError:(NSError *)error {
    [self fireAdErrorEvent:EVENT_AD_FAILLOAD withCode:(int)error.code withMsg:[error localizedDescription] withType:ADTYPE_BANNER];
}

- (BOOL)mobfoxBannerViewActionShouldBegin:(MobFoxBannerView *)banner willLeaveApplication:(BOOL)willLeave {
    return TRUE;
}

- (void)mobfoxBannerViewActionWillLeaveApplication:(MobFoxBannerView *)banner {
    [self fireAdEvent:EVENT_AD_LEAVEAPP withType:ADTYPE_BANNER];
}

- (void)mobfoxBannerViewActionWillPresent:(MobFoxBannerView *)banner {
    [self fireAdEvent:EVENT_AD_PRESENT withType:ADTYPE_BANNER];
}

- (void)mobfoxBannerViewActionWillFinish:(MobFoxBannerView *)banner {
    [self fireAdEvent:EVENT_AD_WILLDISMISS withType:ADTYPE_BANNER];
}

- (void)mobfoxBannerViewActionDidFinish:(MobFoxBannerView *)banner {
    [self fireAdEvent:EVENT_AD_DISMISS withType:ADTYPE_BANNER];
}

- (void)mobfoxVideoInterstitialViewDidLoadMobFoxAd:(MobFoxVideoInterstitialViewController *)videoInterstitial
                                  advertTypeLoaded:(MobFoxAdType)advertType {
    [self fireAdEvent:EVENT_AD_LOADED withType:ADTYPE_INTERSTITIAL];
    
    self.adTypeLoaded = advertType;
    
    if(self.autoShowInterstitial) {
        dispatch_async(dispatch_get_main_queue(), ^{
            [self __showInterstitial:self.interstitial];
        });
    }
}

- (void)mobfoxVideoInterstitialView:(MobFoxVideoInterstitialViewController *)banner
        didFailToReceiveAdWithError:(NSError *)error {
    [self fireAdErrorEvent:EVENT_AD_FAILLOAD withCode:(int)error.code withMsg:[error localizedDescription] withType:ADTYPE_INTERSTITIAL];
}

// Sent immediately before Video/Interstitial is shown to the user. At this point pause any animations, timers or other activities that assume user interaction and save app state, much like on UIApplicationDidEnterBackgroundNotification. Remember that the user may press Home or touch links to other apps like AppStore or iTunes within the interstitial, thus leaving your app.
- (void)mobfoxVideoInterstitialViewActionWillPresentScreen:(MobFoxVideoInterstitialViewController *)videoInterstitial {
    [self fireAdEvent:EVENT_AD_PRESENT withType:ADTYPE_INTERSTITIAL];
}

// Sent immediately before interstitial leaves the screen. At this point restart any foreground activities paused as part of interstitialWillPresentScreen.
- (void)mobfoxVideoInterstitialViewWillDismissScreen:(MobFoxVideoInterstitialViewController *)videoInterstitial {
    [self fireAdEvent:EVENT_AD_WILLDISMISS withType:ADTYPE_INTERSTITIAL];
}

// Sent when the user has dismissed interstitial and it has left the screen.
- (void)mobfoxVideoInterstitialViewDidDismissScreen:(MobFoxVideoInterstitialViewController *)videoInterstitial {
    [self fireAdEvent:EVENT_AD_DISMISS withType:ADTYPE_INTERSTITIAL];
}

// Called when a user tap results in Application Switching.
- (void)mobfoxVideoInterstitialViewActionWillLeaveApplication:(MobFoxVideoInterstitialViewController *)videoInterstitial {
    [self fireAdEvent:EVENT_AD_LEAVEAPP withType:ADTYPE_INTERSTITIAL];
}

@end
