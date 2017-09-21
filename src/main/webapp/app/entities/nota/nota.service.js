(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('Nota', Nota);

    Nota.$inject = ['$resource'];

    function Nota ($resource) {
        var resourceUrl =  'api/notas/:id';

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
