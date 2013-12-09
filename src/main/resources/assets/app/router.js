define(['backbone', 'backbone.marionette'],
	function( Backbone, Marionette ) {
	    "use strict";

		var Router =  Backbone.Marionette.AppRouter.extend( {
			appRoutes: {
				''                 : 'index'
			},
			initialize: function() {
				
			},
			nav: function(url) {
				this.navigate(url, {trigger: true});
			}
		});

		return Router;
});