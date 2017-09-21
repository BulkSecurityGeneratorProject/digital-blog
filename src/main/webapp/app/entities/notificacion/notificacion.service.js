(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('Notificacion', Notificacion);

    Notificacion.$inject = ['$resource'];

    function Notificacion ($resource) {
        var resourceUrl =  'api/notificacions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
