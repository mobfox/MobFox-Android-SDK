//
//  MobFoxPlugin.h
//
//  Created by Shimon Shnitzer on 16/8/2016.
//
//

#import <Cordova/CDV.h>
#import <MobFoxSDKCore/MobFoxSDKCore.h>

@interface MobFoxPlugin : CDVPlugin <MobFoxAdDelegate, MobFoxInterstitialAdDelegate>

- (void)showBanner:(CDVInvokedUrlCommand*)command;

- (void)createInterstitial:(CDVInvokedUrlCommand*)command;

- (void)showInterstitial:(CDVInvokedUrlCommand*)command;

- (void)showToast:(CDVInvokedUrlCommand*)command;

@end
