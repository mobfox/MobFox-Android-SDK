//
//  MobFoxNativeData.h
//  MobFoxSDKCore
//
//  Created by Shimi Sheetrit on 1/19/16.
//  Copyright © 2016 Itamar Nabriski. All rights reserved.
//

#import <Foundation/Foundation.h>


@interface MobFoxNativeTracker : NSObject

@property (nonatomic, copy) NSString *type;
@property (nonatomic, copy) NSURL *url;

- (instancetype)initWithURL:(NSURL *)url type:(NSString *)type;

@end

@interface MobFoxNativeImage : NSObject

@property (nonatomic, copy) NSURL *url;
@property (nonatomic, copy) NSNumber *width;
@property (nonatomic, copy) NSNumber *height;

- (instancetype)initWithURL:(NSURL *)url width:(NSNumber *)width height:(NSNumber *)height;

@end


@interface MobFoxNativeData : NSObject

@property (nonatomic, strong) MobFoxNativeImage *icon;
@property (nonatomic, strong) MobFoxNativeImage *main;

@property (nonatomic, copy) NSString *assetHeadline;
@property (nonatomic, copy) NSString *assetDescription;
@property (nonatomic, copy) NSString *callToActionText;
@property (nonatomic, copy) NSString *advertiserName;
@property (nonatomic, copy) NSString *socialContext;
@property (nonatomic, copy) NSNumber *rating;
@property (nonatomic, copy) NSURL *clickURL;

@property (nonatomic, strong) NSMutableArray *trackersArray;

//@property (nonatomic, strong) id sourceAd;


- (instancetype)initWithDictionary:(NSDictionary *)dictionary;

@end



