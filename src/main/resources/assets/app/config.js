require.config({

    baseUrl: "/app",
    urlArgs: "bust=" +  (new Date()).getTime(),
    deps: ['backbone.marionette', 'marionette.handlebars', 'main'],

    shim: {
        handlebars: {
            exports: 'Handlebars'
        },
        backbone: {
            deps: [
                'underscore',
                'jquery'
            ],
            exports: 'Backbone'
        }
    },

    paths: {
        jquery: 'vendor/bower/jquery/dist/jquery',
        backbone: 'vendor/bower/backbone-amd/backbone',
        underscore: 'vendor/bower/underscore-amd/underscore',

        /* alias all marionette libs */
        'backbone.marionette': 'vendor/bower/backbone.marionette/lib/core/backbone.marionette',
        'backbone.wreqr': 'vendor/bower/backbone.wreqr/lib/backbone.wreqr', 
        'backbone.babysitter': 'vendor/bower/backbone.babysitter/lib/backbone.babysitter',

        /* Alias text.js for template loading and shortcut the templates dir to tmpl */
        text: 'vendor/bower/requirejs-text/text',
        tmpl: "/templates",

        /* handlebars from the require handlerbars plugin below */
        handlebars: 'vendor/bower/require-handlebars-plugin/Handlebars',

        /* require handlebars plugin - Alex Sexton */
        i18nprecompile: 'vendor/bower/require-handlebars-plugin/hbs/i18nprecompile',
        json2: 'vendor/bower/require-handlebars-plugin/hbs/json2',
        hbs: 'vendor/bower/require-handlebars-plugin/hbs',

        /* marionette and handlebars plugin */
        'marionette.handlebars': 'vendor/bower/backbone.marionette.handlebars/backbone.marionette.handlebars'
    },

    hbs: {
        disableI18n: true,
        helperPathCallback: function(name) {return '!tmpl/helpers/' + name;}
    }
});