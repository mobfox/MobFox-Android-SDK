//
//  MobFoxInterstitialCustomEvent.h
//  MobFoxSDKCore
//
//  Created by Itamar Nabriski on 10/8/15.
//  Copyright © 2015 Itamar Nabriski. All rights reserved.
//

#ifndef MobFoxInterstitialCustomEvent_h
#define MobFoxInterstitialCustomEvent_h

#import <UIKit/UIKit.h>

@class MobFoxInterstitialCustomEvent;

@protocol MobFoxInterstitialCustomEventDelegate <NSObject>

- (void)MFInterstitialCustomEventAdDidLoad:(MobFoxInterstitialCustomEvent *)event;

- (void)MFInterstitialCustomEventAdDidFailToReceiveAdWithError:(NSError *)error;

- (void)MFInterstitialCustomEventAdClosed;

- (void)MFInterstitialCustomEventMobFoxAdClicked;

- (void)MFInterstitialCustomEventMobFoxAdFinished;

@end


@interface MobFoxInterstitialCustomEvent : NSObject

-(void)requestInterstitialWithNetworkId:(NSString*)networkId customEventInfo:(NSDictionary *)info;

-(void)presentWithRootController:(UIViewController *)rootViewController;

@property (nonatomic, weak) id<MobFoxInterstitialCustomEventDelegate> delegate;

@end

#endif /* MobFoxInterstitialCustomEvent_h */
