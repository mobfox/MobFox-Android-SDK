var exec = require('cordova/exec');

function MobFoxPlugin() { 
 	console.log("MobFoxPlugin.js: is created");
}

MobFoxPlugin.prototype.showBanner = function(hash, x, y, w, h) {
	
	if(typeof hash === 'undefined') hash = '8769bb5eb962eb39170fc5d8930706a9';
	if(typeof x === 'undefined') x = 0;
	if(typeof y === 'undefined') y = 0;
	if(typeof w === 'undefined') w = 320;
	if(typeof h === 'undefined') h = 50;
	
	exec(function(result){
					var myCustomEvent = new Event(result);
					document.dispatchEvent(myCustomEvent);
   				},
  				function(result){
					var myCustomEvent = new Event("onBannerFailed", result);
					document.dispatchEvent(myCustomEvent);
    				//alert("Error: " + result);
   				}, "MobFoxPlugin", "showBanner", [{hash:hash, x:x, y:y, w:w, h:h}] );
};

MobFoxPlugin.prototype.createInterstitial = function(hash) {

	if(typeof hash === 'undefined') hash = '267d72ac3f77a3f447b32cf7ebf20673';

	exec(function(result){
					var myCustomEvent = new Event(result);
					document.dispatchEvent(myCustomEvent);
   				},
  				function(result){
					var myCustomEvent = new Event("onInterstitialFailed", result);
					document.dispatchEvent(myCustomEvent);
   				}, "MobFoxPlugin", "createInterstitial", [ {hash:hash} ] );
};

MobFoxPlugin.prototype.showInterstitial = function() {

	exec(function(result){
					var myCustomEvent = new Event(result);
					document.dispatchEvent(myCustomEvent);
   				},
  				function(result){
					var myCustomEvent = new Event("onInterstitialFailed", result);
					document.dispatchEvent(myCustomEvent);
   				}, "MobFoxPlugin", "showInterstitial", [] );
};


MobFoxPlugin.prototype.showToast = function(text) {
    
    if(typeof text === 'undefined') text = 'Hello';
    
    exec(function(result){
         },
         function(result){
         }, "MobFoxPlugin", "showToast", [ {text:text} ] );
};

var mobFoxPlugin = new MobFoxPlugin();
 module.exports = mobFoxPlugin;

