require(["app", "router", "controller", "backbone"], function(app, Router, Controller, Backbone) {
	  
	app.router = new Router({controller: new Controller()});
	Backbone.history.start({ pushState: true, root: app.root });

});