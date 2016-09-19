
var argscheck = require('cordova/argscheck'),
    exec = require('cordova/exec');

var mobfoxExport = {};

mobfoxExport.createBanner = function(hash, x, y, w, h, successCallback, failureCallback) {
	
	if(typeof hash === 'undefined') hash = '8769bb5eb962eb39170fc5d8930706a9';
	if(typeof x === 'undefined') x = 0;
	if(typeof y === 'undefined') y = 0;
	if(typeof w === 'undefined') w = 320;
	if(typeof h === 'undefined') h = 50;
	
	cordova.exec( successCallback, failureCallback, 'MobFoxPlugin', 'createBanner', [{hash:hash, x:x, y:y, w:w, h:h}] );
};

mobfoxExport.showBanner = function(successCallback, failureCallback) {

	cordova.exec( successCallback, failureCallback, 'MobFoxPlugin', 'showBanner', [] );
};

mobfoxExport.hideBanner = function(successCallback, failureCallback) {

	cordova.exec( successCallback, failureCallback, 'MobFoxPlugin', 'hideBanner', [] );
};

mobfoxExport.createInterstitial = function(hash, successCallback, failureCallback) {

	if(typeof hash === 'undefined') hash = '267d72ac3f77a3f447b32cf7ebf20673';

	cordova.exec( successCallback, failureCallback, 'MobFoxPlugin', 'createInterstitial', [ {hash:hash} ] );
};

mobfoxExport.showInterstitial = function(successCallback, failureCallback) {

	cordova.exec( successCallback, failureCallback, 'MobFoxPlugin', 'showInterstitial', [] );
};

module.exports = mobfoxExport;

