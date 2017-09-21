(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('Suscripciones', Suscripciones);

    Suscripciones.$inject = ['$resource'];

    function Suscripciones ($resource) {
        var resourceUrl =  'api/suscripciones/:id';

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
