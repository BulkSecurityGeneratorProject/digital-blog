(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('ImagenPorPublicacion', ImagenPorPublicacion);

    ImagenPorPublicacion.$inject = ['$resource'];

    function ImagenPorPublicacion ($resource) {
        var resourceUrl =  'api/imagen-por-publicacions/:id';

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
