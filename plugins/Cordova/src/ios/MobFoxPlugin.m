//
//  MobFoxPlugin.m
//
//  Created by Shimon Shnitzer on 16/8/2016.
//
//

#import "MobFoxPlugin.h"
#import <Cordova/CDV.h>
#import <MobFoxSDKCore/MobFoxSDKCore.h>

@interface MobFoxPlugin()

    @property NSMutableDictionary* ads;
    @property int nextId;
    @property NSString* gameObject;
    @property (nonatomic,strong) MobFoxInterstitialAd* inter;
    

@end

@implementation MobFoxPlugin

CDVInvokedUrlCommand *mBannerCommand       = nil;
CDVInvokedUrlCommand *mInterstitialCommand = nil;

//======================================================================================
//======  T O A S T                                                               ======
//======================================================================================

- (void)showToast:(CDVInvokedUrlCommand*)command
{
    NSMutableDictionary* dict = [command.arguments objectAtIndex:0];

    if (dict != nil && [dict count] > 0)
    {
    	NSString *text = [dict objectForKey:@"text"];
    	
    	[self MyShowToast:text];
    }
}

- (void)MyShowToast:(NSString *)text
{
		UIAlertView *toast = [[UIAlertView alloc] initWithTitle:nil
                                                message:text
                                               delegate:nil
                                      cancelButtonTitle:nil
                                      otherButtonTitles:nil, nil];
		[toast show];

		int duration = 2; // duration in seconds

		dispatch_after(dispatch_time(DISPATCH_TIME_NOW, duration * NSEC_PER_SEC), dispatch_get_main_queue(), ^{
    		[toast dismissWithClickedButtonIndex:0 animated:YES];
		});
}

//======================================================================================
//======  B A N N E R                                                             ======
//======================================================================================

- (void)showBanner:(CDVInvokedUrlCommand*)command
{
    NSMutableDictionary* dict = [command.arguments objectAtIndex:0];

    if (dict != nil && [dict count] > 0)
    {
    	NSString *invh = [dict objectForKey:@"hash"];
    	NSNumber *x    = [dict objectForKey:@"x"];
    	NSNumber *y    = [dict objectForKey:@"y"];
    	NSNumber *w    = [dict objectForKey:@"w"];
    	NSNumber *h    = [dict objectForKey:@"h"];
    	
    	if ((invh!=nil) && (x!=nil) && (y!=nil) && (w!=nil) && (h!=nil))
    	{
    		mBannerCommand = command;
    	
    		dispatch_async(dispatch_get_main_queue(), ^{
    			// Your code to run on the main queue/thread
	    		[self createBanner:invh 
						   originX:[x floatValue]
						   originY:[y floatValue]
						 sizeWidth:[w floatValue]
						sizeHeight:[h floatValue]];
			});
					
        	return;
    	}
    }

	[self sendMobFoxCallback:command withSuccess:FALSE andText:@"No params"];
}

//======================================================================================

- (void)sendMobFoxCallback:(CDVInvokedUrlCommand *)command 
               withSuccess:(BOOL)bOk 
                   andText:(NSString *)msg
{
    CDVPluginResult* pluginResult;
    
    if (bOk)
    {
	    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:msg];
    } else {
    	pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:msg];
    }
    
    pluginResult.keepCallback = [NSNumber numberWithBool:TRUE];

    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

//======================================================================================

- (int)createBanner:(NSString*)invh 
			originX:(CGFloat)originX 
			originY:(CGFloat)originY
          sizeWidth:(CGFloat)sizeWidth
         sizeHeight:(CGFloat)sizeHeight
{
    NSLog(@"dbg: ### MobFoxUnityPlugin >> createBanner(%@)",invh);
    
    NSLog(@"dbg: ### MobFoxUnityPlugin >> rect x: %f", originX);
    NSLog(@"dbg: ### MobFoxUnityPlugin >> rect y: %f", originY);
    NSLog(@"dbg: ### MobFoxUnityPlugin >> rect width: %f", sizeWidth);
    NSLog(@"dbg: ### MobFoxUnityPlugin >> rect height: %f", sizeHeight);
    
    CGRect placement = CGRectMake(originX,originY,sizeWidth,sizeHeight);

    MobFoxAd* banner = [[MobFoxAd alloc] init:invh withFrame:placement];
    
    banner.delegate = self;
    
    NSString* key = [NSString stringWithFormat:@"key-%d",self.nextId];
    [self.ads setValue:banner forKey:key];
    int cur = self.nextId;
    self.nextId= self.nextId + 1;

    [banner loadAd];

    return cur;
}

//======================================================================================

// MobFox Ad Delegate
- (void)MobFoxAdDidLoad:(MobFoxAd *)banner{
    //show banner
    [self.viewController.view addSubview:banner];
    
    NSLog(@"dbg: ### MobFoxUnityPlugin >> MobFoxAdDidLoad:");
    [self sendMobFoxCallback:mBannerCommand withSuccess:TRUE andText:@"onBannerLoaded"];
    
}

- (void)MobFoxAdDidFailToReceiveAdWithError:(NSError *)error{
    NSLog(@"dbg: ### MobFoxUnityPlugin >> MobFoxAdDidFailToReceiveAdWithError: %@",[error description]);
    NSString *msg;
    
    msg = [NSString stringWithFormat:@"Unknown error"];
    if ((error!=nil) && (error.userInfo!=nil))
    {
    	NSDictionary *errDict = [error.userInfo objectForKey:@"error"];
    	if (errDict!=nil)
    	{
    		msg = [NSString stringWithFormat:@"%@", errDict];
    	}
    }
    
    //[self MyShowToast:msg];

    [self sendMobFoxCallback:mBannerCommand withSuccess:FALSE andText:msg];
}

- (void)MobFoxAdClosed{
    NSLog(@"dbg: ### MobFoxUnityPlugin >> MobFoxAdClosed:");
    [self sendMobFoxCallback:mBannerCommand withSuccess:TRUE andText:@"onBannerClosed"];
}

- (void)MobFoxAdClicked{
    NSLog(@"dbg: ### MobFoxUnityPlugin >> MobFoxAdClicked:");
    [self sendMobFoxCallback:mBannerCommand withSuccess:TRUE andText:@"onBannerClicked"];
}

- (void)MobFoxAdFinished{
    NSLog(@"dbg: ### MobFoxUnityPlugin >> MobFoxAdFinished:");
    [self sendMobFoxCallback:mBannerCommand withSuccess:TRUE andText:@"onBannerFinished"];
}

//======================================================================================
//======  I N T E R S T I T I A L                                                 ======
//======================================================================================

- (void)createInterstitial:(CDVInvokedUrlCommand*)command
{
    NSMutableDictionary* dict = [command.arguments objectAtIndex:0];

    if (dict != nil && [dict count] > 0)
    {
    	NSString *invh = [dict objectForKey:@"hash"];
    	
    	if (invh!=nil)
    	{
    		mInterstitialCommand = command;
    
    		self.inter = [[MobFoxInterstitialAd alloc] init:invh
                             withRootViewController:self.viewController];//UnityGetGLViewController()];
    
    		self.inter.ad.demo_gender = @"f";
    		self.inter.delegate = self;
    
    		[self.inter loadAd];
					
        	return;
    	}
    }

	[self sendMobFoxCallback:command withSuccess:FALSE andText:@"No params"];
}

- (void)showInterstitial:(CDVInvokedUrlCommand*)command
{
    NSLog(@"dbg: ### MobFoxUnityPlugin >> showInterstitial");

    if(!self.inter){
        NSLog(@"dbg: ### MobFoxUnityPlugin >> showInterstitial >> inter not init");
        return;
    }
    
    if(self.inter.ready){
        NSLog(@"dbg: ### MobFoxUnityPlugin >> showInterstitial >> inter ready - show");
        [self.inter show];
    }
}

//======================================================================================

// MobFox Interstitial Ad Delegate
- (void)MobFoxInterstitialAdDidLoad:(MobFoxInterstitialAd *)interstitial{
    
    NSLog(@"dbg: ### MobFoxUnityPlugin >> MobFoxInterstitialAdDidLoad >> inter loaded");
    
    //@@@interstitial.rootViewController = UnityGetGLViewController();

    NSLog(@"dbg: ### MobFoxUnityPlugin >> MobFoxAdDidLoad:");
    [self sendMobFoxCallback:mInterstitialCommand withSuccess:TRUE andText:@"onInterstitialLoaded"];
}

- (void)MobFoxInterstitialAdDidFailToReceiveAdWithError:(NSError *)error{

    NSLog(@"dbg: ### MobFoxUnityPlugin >> MobFoxInterstitialAdDidFailToReceiveAdWithError >> %@",[error description]);
    
    NSString *msg;
    
    msg = [NSString stringWithFormat:@"Unknown error"];
    if ((error!=nil) && (error.userInfo!=nil))
    {
    	NSDictionary *errDict = [error.userInfo objectForKey:@"error"];
    	if (errDict!=nil)
    	{
    		msg = [NSString stringWithFormat:@"%@", errDict];
    	}
    }
    
    [self sendMobFoxCallback:mInterstitialCommand withSuccess:FALSE andText:msg];
}

- (void)MobFoxInterstitialAdClosed{
    NSLog(@"dbg: ### MobFoxUnityPlugin >> MobFoxInterstitialAdClosed:");
    [self sendMobFoxCallback:mInterstitialCommand withSuccess:TRUE andText:@"onInterstitialClosed"];
}


- (void)MobFoxInterstitialAdClicked{
    NSLog(@"dbg: ### MobFoxUnityPlugin >> MobFoxInterstitialAdClicked:");
    [self sendMobFoxCallback:mInterstitialCommand withSuccess:TRUE andText:@"onInterstitialClicked"];
}


- (void)MobFoxInterstitialAdFinished{
    NSLog(@"dbg: ### MobFoxUnityPlugin >> MobFoxInterstitialAdFinished:");
    [self sendMobFoxCallback:mInterstitialCommand withSuccess:TRUE andText:@"onInterstitialFinished"];
}


/*
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
*/
@end
