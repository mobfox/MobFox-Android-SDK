//
//  LocationManager.h
//  MobFoxSDKCore
//
//  Created by Shimi Sheetrit on 5/2/16.
//  Copyright © 2016 Itamar Nabriski. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreLocation/CoreLocation.h>


@interface LocationServicesManager : NSObject <CLLocationManagerDelegate, UIAlertViewDelegate>

@property (nonatomic) double latitude;
@property (nonatomic) double longitude;
@property (nonatomic, strong) CLLocationManager *locationManager;

+ (instancetype)sharedInstance;
- (void)findLocation;
- (void)stopFindingLocation;


@end
