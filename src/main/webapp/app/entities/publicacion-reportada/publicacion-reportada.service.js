(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('PublicacionReportada', PublicacionReportada);

    PublicacionReportada.$inject = ['$resource'];

    function PublicacionReportada ($resource) {
        var resourceUrl =  'api/publicacion-reportadas/:id';

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
