require(["config"], function() {
  require(["app", "router", "controller"], function(app, Router, Controller) {
	  
    app.router = new Router({controller: new Controller()});
    Backbone.history.start({ pushState: true, root: app.root });
    
  });
});
