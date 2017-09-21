(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('Capitulo', Capitulo);

    Capitulo.$inject = ['$resource'];

    function Capitulo ($resource) {
        var resourceUrl =  'api/capitulos/:id';

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
