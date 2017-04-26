define(['backbone'], function (Backbone) {
    return Backbone.Model.extend({
        default: {
            url: 'www.baidu.com',
            title: 'Baiduing or dying',
            desc: 'Whats the name for the the holes in Swiss cheese? ' +
            'Eyes. The state of being full of beer? Gambrinous. Dazzle ' +
            'your friends and family with this list of truly weird one-of-a-kind words.'
        }
    });
});