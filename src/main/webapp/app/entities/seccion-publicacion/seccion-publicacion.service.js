(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('SeccionPublicacion', SeccionPublicacion);

    SeccionPublicacion.$inject = ['$resource'];

    function SeccionPublicacion ($resource) {
        var resourceUrl =  'api/seccion-publicacions/:id';

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
