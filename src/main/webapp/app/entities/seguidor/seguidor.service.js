(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('Seguidor', Seguidor);

    Seguidor.$inject = ['$resource'];

    function Seguidor ($resource) {
        var resourceUrl =  'api/seguidors/:id';

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
