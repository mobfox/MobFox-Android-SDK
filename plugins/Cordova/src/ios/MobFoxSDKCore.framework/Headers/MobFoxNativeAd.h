//
//  MobFoxNativeRequest.h
//  MobFoxSDKCore
//
//  Created by Itamar Nabriski on 9/29/15.
//  Copyright © 2015 Itamar Nabriski. All rights reserved.
//

#ifndef MobFoxNativeRequest_h
#define MobFoxNativeRequest_h

#import <UIKit/UIKit.h>
#import "MobFoxNativeCustomEvent.h"
#import "MobFoxNativeData.h"


@class MobFoxNativeAd;


@protocol MobFoxNativeAdDelegate <NSObject>

- (void)MobFoxNativeAdDidLoad:(MobFoxNativeAd*)ad withAdData:(MobFoxNativeData *)adData;

- (void)MobFoxNativeAdDidFailToReceiveAdWithError:(NSError *)error;

@end

@interface MobFoxNativeAd : NSObject<MobFoxNativeCustomEventDelegate>

    @property (nonatomic, weak) id<MobFoxNativeAdDelegate> delegate;
    
    @property (nonatomic, copy) NSString* longitude;
    @property (nonatomic, copy) NSString* latitude;
    @property (nonatomic, copy) NSString* demo_gender; //"m/f"
    @property (nonatomic, copy) NSString* demo_age;
    @property (nonatomic, copy) NSString* s_subid;
    @property (nonatomic, copy) NSString* sub_name;
    @property (nonatomic, copy) NSString* sub_domain;
    @property (nonatomic, copy) NSString* sub_storeurl;
    @property (nonatomic, copy) NSString* v_dur_min;
    @property (nonatomic, copy) NSString* v_dur_max;
    @property (nonatomic, copy) NSString* r_floor;
    @property (nonatomic, strong) NSString* invh;
    @property (nonatomic, strong) NSString* serverURL;;



    - (id) init:(NSString*)invh;
    - (void) loadAd;
    - (void) registerViewWithInteraction:(UIView *)view withViewController:(UIViewController *)viewController;
    + (void) locationServicesDisabled:(BOOL)disabled;

    //- (void)MFNativeCustomEventAd:(MobFoxNativeCustomEvent *)event didLoad:(NSDictionary *)ad;
    //- (void)MFNativeCustomEventAdDidFailToReceiveAdWithError:(NSError *)error;

@end

#endif /* MobFoxNativeRequest_h */
