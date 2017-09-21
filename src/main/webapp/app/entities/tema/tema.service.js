(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('Tema', Tema);

    Tema.$inject = ['$resource'];

    function Tema ($resource) {
        var resourceUrl =  'api/temas/:id';

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
