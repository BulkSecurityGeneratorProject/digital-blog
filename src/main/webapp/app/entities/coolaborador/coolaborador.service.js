(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('Coolaborador', Coolaborador);

    Coolaborador.$inject = ['$resource'];

    function Coolaborador ($resource) {
        var resourceUrl =  'api/coolaboradors/:id';

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
