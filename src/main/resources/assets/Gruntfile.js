/*global module:false*/
module.exports = function(grunt) {

  // Project configuration.
  grunt.initConfig({
    // Metadata.
    meta: {
      version: '0.1.0'
    },
    banner: '/*! dropwizard-backbone-app - v<%= meta.version %> - ' +
      '<%= grunt.template.today("yyyy-mm-dd") %>\n' +
      '* https://github.com/yokuze/dropwizard-backbone-app/\n' +
      '* Copyright (c) <%= grunt.template.today("yyyy") %> ' +
      'Matt Luedke; Licensed MIT */\n',
    bower: {
      target: {
        rjsConfig: 'init.js'
      }
    },
    uglify: {
      options: {
        banner: '<%= banner %>'
      },
      dist: {
        src: '<%= concat.dist.dest %>',
        dest: 'app/main.min.js'
      }
    },
    jshint: {
      options: {
        curly: true,
        eqeqeq: true,
        immed: true,
        latedef: true,
        newcap: true,
        noarg: true,
        sub: true,
        undef: true,
        unused: true,
        boss: true,
        eqnull: true,
        browser: true,
        globals: {
          jQuery: true
        }
      },
      gruntfile: {
        src: 'Gruntfile.js'
      },
      lib_test: {
        src: ['lib/**/*.js', 'test/**/*.js']
      }
    },
    less: {
      development: {
        options: {
          paths: ["css"]
        },
        files: {
          "css/main.css": "less/main.less"
        }
      },
      production: {
        options: {
          paths: ["css"],
          cleancss: true,
          modifyVars: {
            //imgPath: '"http://mycdn.com/path/to/images"',
          }
        },
        files: {
          "css/main.css": "less/main.less"
        }
      }
    },
    qunit: {
      files: ['test/**/*.html']
    },
    requirejs: {
      compile: {
        options: {
          baseUrl: "app",
          mainConfigFile: "app/config.js",
          optimize: "uglify",
          out: "app/main.min.js",
          include: "main",
          findNestedDependencies: true
        }
      }
    },
    watch: {
      gruntfile: {
        files: '<%= jshint.gruntfile.src %>',
        tasks: ['jshint:gruntfile']
      },
      lib_test: {
        files: '<%= jshint.lib_test.src %>',
        tasks: ['jshint:lib_test', 'qunit']
      }
    }
  });

  // These plugins provide necessary tasks.
  grunt.loadNpmTasks('grunt-contrib-concat');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-qunit');
  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-less');
  grunt.loadNpmTasks('grunt-bower-requirejs');
  grunt.loadNpmTasks('grunt-contrib-requirejs');

  // Default task.
  grunt.registerTask('default', ['jshint', 'requirejs', 'less']);
  grunt.registerTask('bower', ['bower']);

};
