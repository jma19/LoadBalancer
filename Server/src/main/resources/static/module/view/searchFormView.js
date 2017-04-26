define([
    'backbone',
    'handlebars',
    'module/view/resultTableView',
    'module/collection/records',
    'text!module/template/search-form.hbs'
], function (Backbone, Handlebars, ResultTableView, RecordList, SearchFormTpl) {
    return Backbone.View.extend({
        template: Handlebars.compile(SearchFormTpl),

        events: {
            'click .search-button': 'doSearch',
            'keypress input[name="keyword"]': 'doSearch'
        },

        initialize: function (options) {
            this.keyword = options.keyword,
            this.resultTableView = new ResultTableView,
            this.recordList = new RecordList
        },
        render: function () {
            this.$el.html(this.template({keyword: this.keyword}));
            return this.$el;
        },

        doSearch: function (e) {
            var self = this;
            if (e.type == 'click' || e.keyCode == 13) {
                var keyword = this.$('input[name="keyword"]').val();
                if (keyword) {
                    this.recordList.url = 'miya/query/' + keyword;
                    this.recordList.fetch({
                        success: function (collection, resp, options) {
                            self.$('#recordList').append(self.resultTableView.render({$parent: self.el, records: collection}));
                        },
                        error: function (collection, resp, options) {
                            self.resultTableView.render({error: 'Service Error!'});
                        }
                    });
                }
            }
        }
    });
})