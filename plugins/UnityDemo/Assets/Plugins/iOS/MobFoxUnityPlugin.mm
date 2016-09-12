//
//  MobFoxUnityPlugin.m
//  MobFoxUnityPlugin
//
//  Created by Itamar Nabriski on 11/17/15.
//  Copyright © 2015 Itamar Nabriski. All rights reserved.
//


#import "MobFoxUnityPlugin.h"

extern "C"
{
    extern UIViewController* UnityGetGLViewController();
    extern void UnitySendMessage(const char *, const char *, const char *);
}

@interface MobFoxUnityPlugin()
    @property NSMutableDictionary* ads;
    @property int nextId;
    @property NSString* gameObject;
    @property (nonatomic,strong) MobFoxInterstitialAd* inter;

@end

@implementation MobFoxUnityPlugin

//======================================================================================
//======  I N I T                                                                 ======
//======================================================================================

-(id) init{
    self = [super init];
    self.ads = [[NSMutableDictionary alloc] init];
    self.nextId = 1;
    self.gameObject = nil;
    return self;
}

//======================================================================================
//======  B A N N E R                                                             ======
//======================================================================================

-(int) createBanner:(NSString*)invh 
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
    //banner.type = @"video";
    [banner loadAd];
    return cur;
}

-(void) showBanner:(int) bannerId{
    NSString* key = [NSString stringWithFormat:@"key-%d",bannerId];
    MobFoxAd* banner = [self.ads valueForKey:key];
    if(!banner){
        NSLog(@"dbg: ### MobFoxUnityPlugin >> showBanner >> banner with id %d was not found",bannerId);
        return;
    }
    banner.hidden = NO;
    NSLog(@"dbg: ### MobFoxUnityPlugin >> showBanner >> show banner %d",bannerId);

}

-(void) hideBanner:(int) bannerId{
     NSString* key = [NSString stringWithFormat:@"key-%d",bannerId];
     MobFoxAd* banner = [self.ads valueForKey:key];
     if(!banner){
        NSLog(@"dbg: ### MobFoxUnityPlugin >> hideBanner >> banner with id %d was not found",bannerId);
        return;
    }
    banner.hidden = YES;
    NSLog(@"dbg: ### MobFoxUnityPlugin >> hideBanner >> hiding banner %d",bannerId);
}

//======================================================================================

// MobFox Ad Delegate
- (void)MobFoxAdDidLoad:(MobFoxAd *)banner{
    //show banner
    UIViewController* vc = UnityGetGLViewController();
    [vc.view addSubview:banner];
    
    NSLog(@"dbg: ### MobFoxUnityPlugin >> MobFoxAdDidLoad:");
    UnitySendMessage([self.gameObject UTF8String], "bannerReady", "MobFoxAdDidLoad msg");
    
}

- (void)MobFoxAdDidFailToReceiveAdWithError:(NSError *)error{
    NSLog(@"dbg: ### MobFoxUnityPlugin >> MobFoxAdDidFailToReceiveAdWithError: %@",[error description]);
    UnitySendMessage([self.gameObject UTF8String], "bannerError", [[error description] UTF8String]);
}

- (void)MobFoxAdClosed{
    NSLog(@"dbg: ### MobFoxUnityPlugin >> MobFoxAdClosed:");
    UnitySendMessage([self.gameObject UTF8String], "bannerClosed", "MobFoxAdClosed msg");
}

- (void)MobFoxAdClicked{
    NSLog(@"dbg: ### MobFoxUnityPlugin >> MobFoxAdClicked:");
    UnitySendMessage([self.gameObject UTF8String], "bannerClicked", "MobFoxAdClicked msg");
}

- (void)MobFoxAdFinished{
    NSLog(@"dbg: ### MobFoxUnityPlugin >> MobFoxAdFinished:");
    UnitySendMessage([self.gameObject UTF8String], "bannerFinished", "MobFoxAdFinished msg");
}

//======================================================================================
//======  I N T E R S T I T I A L                                                 ======
//======================================================================================

-(void) createInterstitial:(NSString*)invh{
    
    NSLog(@"dbg: ### MobFoxUnityPlugin >> createInterstitial(%@)",invh);
    
    //self.inter = [[MobFoxInterstitialAd alloc] init:invh];
    
    self.inter = [[MobFoxInterstitialAd alloc] init:invh
                             withRootViewController:UnityGetGLViewController()];
    
    self.inter.ad.demo_gender = @"f";
    self.inter.delegate = self;
    
    [self.inter loadAd];
}

-(void) showInterstitial{
   
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
    
    UnitySendMessage([self.gameObject UTF8String], "interstitialReady", "MobFoxInterstitialAdDidLoad msg");
        
}

- (void)MobFoxInterstitialAdDidFailToReceiveAdWithError:(NSError *)error{

    NSLog(@"dbg: ### MobFoxUnityPlugin >> MobFoxInterstitialAdDidFailToReceiveAdWithError >> %@",[error description]);
    

    UnitySendMessage([self.gameObject UTF8String], "interstitalError", [[error description] UTF8String]);
}

- (void)MobFoxInterstitialAdClosed{
    NSLog(@"dbg: ### MobFoxUnityPlugin >> MobFoxInterstitialAdClosed:");
    UnitySendMessage([self.gameObject UTF8String], "interstitialClosed", "MobFoxInterstitialAdClosed msg");
}


- (void)MobFoxInterstitialAdClicked{
    NSLog(@"dbg: ### MobFoxUnityPlugin >> MobFoxInterstitialAdClicked:");
    UnitySendMessage([self.gameObject UTF8String], "interstitialClicked", "MobFoxInterstitialAdClicked msg");
}


- (void)MobFoxInterstitialAdFinished{
    NSLog(@"dbg: ### MobFoxUnityPlugin >> MobFoxInterstitialAdFinished:");
    UnitySendMessage([self.gameObject UTF8String], "interstitialFinished", "MobFoxInterstitialAdFinished msg");
}

@end

//======================================================================================
//======  P L U G I N                                                             ======
//======================================================================================

extern "C"
{
    static MobFoxUnityPlugin* plugin = [[MobFoxUnityPlugin alloc] init];
    
    void _setGameObject(const char* gameObject){
        plugin.gameObject = [NSString stringWithUTF8String:gameObject];
    }
    
    int _createBanner(const char* invh, float x, float y, float width, float height){
        
        return [plugin createBanner:[NSString stringWithUTF8String:invh]
                            originX:x
                            originY:y
                          sizeWidth:width
                         sizeHeight:height];
        
    }
    void _showBanner(int bannerId){
        [plugin showBanner:bannerId];
    }
    void  _hideBanner(int bannerId){
        [plugin hideBanner:bannerId];
    }
    
    void _createInterstitial(const char* invh){
        [plugin createInterstitial:[NSString stringWithUTF8String:invh]];
    }
    
    void _showInterstitial(){
        [plugin showInterstitial];
    }
}
