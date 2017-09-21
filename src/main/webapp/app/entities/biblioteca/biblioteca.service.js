(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('Biblioteca', Biblioteca);

    Biblioteca.$inject = ['$resource'];

    function Biblioteca ($resource) {
        var resourceUrl =  'api/bibliotecas/:id';

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
