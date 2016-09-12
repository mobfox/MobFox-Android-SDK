//
//  Constants.h
//  MobFoxSDKCore
//
//  Created by Itamar Nabriski on 11/9/15.
//  Copyright © 2015 Itamar Nabriski. All rights reserved.
//


#import <UIKit/UIKit.h>
//#import <Foundation/Foundation.h>


#define SDK_VERSION @"Core_2.1.2"
#define OS_VERSION [[[UIDevice currentDevice] systemVersion] floatValue]
#define FW_VERSION [[NSBundle mainBundle] objectForInfoDictionaryKey: @"CFBundleShortVersionString"]
#define BUILD_VERSION [[NSBundle mainBundle] objectForInfoDictionaryKey: (NSString *)kCFBundleVersionKey]
#define ERROR_DOMAIN @""






