(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('Permiso', Permiso);

    Permiso.$inject = ['$resource'];

    function Permiso ($resource) {
        var resourceUrl =  'api/permisos/:id';

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
