//
//  MobFoxNativeCustomEvent.h
//  MobFoxSDKCore
//
//  Created by Itamar Nabriski on 10/11/15.
//  Copyright © 2015 Itamar Nabriski. All rights reserved.
//

#ifndef MobFoxNativeCustomEvent_h
#define MobFoxNativeCustomEvent_h
#import <UIKit/UIKit.h>

#import "MobFoxNativeData.h"


@class MobFoxNativeCustomEvent;

@protocol MobFoxNativeCustomEventDelegate <NSObject>

- (void)MFNativeCustomEventAd:(MobFoxNativeCustomEvent *)event didLoad:(MobFoxNativeData *)adData;

- (void)MFNativeCustomEventAdDidFailToReceiveAdWithError:(NSError *)error;

@end


@interface MobFoxNativeCustomEvent : NSObject

- (void)registerViewWithInteraction:(UIView *)view withViewController:(UIViewController *)viewController;

- (void)requestAdWithNetworkID:(NSString*)nid customEventInfo:(NSDictionary *)info;

@property (nonatomic, weak) id<MobFoxNativeCustomEventDelegate> delegate;

@end

#endif /* MobFoxNativeCustomEvent_h */
