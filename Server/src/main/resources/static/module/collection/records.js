define([
    'backbone',
    '../model/record'
], function (Backbone, Record) {
    return Backbone.Collection.extend({
        model: Record
    });
});