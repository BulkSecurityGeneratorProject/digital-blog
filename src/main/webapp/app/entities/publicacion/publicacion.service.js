(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('Publicacion', Publicacion);

    Publicacion.$inject = ['$resource'];

    function Publicacion ($resource) {
        var resourceUrl =  'api/publicacions/:id';

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
