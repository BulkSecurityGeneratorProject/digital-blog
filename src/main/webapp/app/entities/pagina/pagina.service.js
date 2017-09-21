(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('Pagina', Pagina);

    Pagina.$inject = ['$resource'];

    function Pagina ($resource) {
        var resourceUrl =  'api/paginas/:id';

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
