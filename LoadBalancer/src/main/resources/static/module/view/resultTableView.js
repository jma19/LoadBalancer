define([
    'backbone',
    'handlebars',
    'text!module/template/result-table.hbs'
], function (Backbone, Handlebars, ResultTableTpl) {

    return Backbone.View.extend({
        template: Handlebars.compile(ResultTableTpl),
        render: function (options) {
            this.$el.html(this.template({ records: options.records.models}));
            return this.$el;
        }
    });
});