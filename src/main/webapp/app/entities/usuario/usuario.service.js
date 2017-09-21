(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('Usuario', Usuario);

    Usuario.$inject = ['$resource', 'DateUtils'];

    function Usuario ($resource, DateUtils) {
        var resourceUrl =  'api/usuarios/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fechaNacimiento = DateUtils.convertDateTimeFromServer(data.fechaNacimiento);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
