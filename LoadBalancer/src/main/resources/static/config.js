require.config({
    // map: {
    //     '*': {
    //         'css': 'lib/requirejs/require-css'
    //     }
    // },
    paths: {
        'jquery': 'lib/jquery/jquery-3.1.1',
        'underscore': 'lib/underscore/underscore',
        'backbone': 'lib/backbone/backbone-1.3.3',
        // 'bootstrap': 'lib/bootstrap/js/bootstrap',
        'handlebars': 'lib/handlebars/handlebars',
        'text': 'lib/text/text'
    },
    shim: {
        'backbone': {
            deps: ['underscore', 'jquery'],
            export: 'Backbone'
        }
        // 'bootstrap': {
        //     deps: ['jquery', 'css!lib/bootstrap/css/bootstrap']
        // }
    }
});

require([
    'backbone',
    'module/view/searchFormView'
], function (Backbone, SearchFormView) {
    var AppView = Backbone.View.extend({
        el: '.main-container',
        render: function() {
            var searchFormView = new SearchFormView({ keyword:'' });
            this.$el.append(searchFormView.render());
        }
    });

    (new AppView()).render();
});