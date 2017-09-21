(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('RolePermiso', RolePermiso);

    RolePermiso.$inject = ['$resource'];

    function RolePermiso ($resource) {
        var resourceUrl =  'api/role-permisos/:id';

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
