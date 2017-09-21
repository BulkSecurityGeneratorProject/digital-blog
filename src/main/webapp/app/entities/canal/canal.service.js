(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('Canal', Canal);

    Canal.$inject = ['$resource'];

    function Canal ($resource) {
        var resourceUrl =  'api/canals/:id';

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
