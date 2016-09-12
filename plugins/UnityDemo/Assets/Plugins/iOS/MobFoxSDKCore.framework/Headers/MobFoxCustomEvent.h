//
//  MobFoxCustomEvent.h
//  MobFoxSDKCore
//
//  Created by Itamar Nabriski on 10/7/15.
//  Copyright © 2015 Itamar Nabriski. All rights reserved.
//

#ifndef MobFoxCustomEvent_h
#define MobFoxCustomEvent_h

#import <UIKit/UIKit.h>

@class MobFoxCustomEvent;

@protocol MobFoxCustomEventDelegate <NSObject>

- (void)MFCustomEventAd:(MobFoxCustomEvent *)event didLoad:(UIView *)ad;

- (void)MFCustomEventAdDidFailToReceiveAdWithError:(NSError *)error;

@optional

- (void)MFCustomEventAdClosed;

- (void)MFCustomEventMobFoxAdClicked;

- (void)MFCustomEventMobFoxAdFinished;

@end


@interface MobFoxCustomEvent : NSObject

- (void)requestAdWithSize:(CGSize)size networkID:(NSString*)nid customEventInfo:(NSDictionary *)info;

@property (nonatomic, weak) id<MobFoxCustomEventDelegate> delegate;

@end

#endif /* MobFoxCustomEvent_h */
