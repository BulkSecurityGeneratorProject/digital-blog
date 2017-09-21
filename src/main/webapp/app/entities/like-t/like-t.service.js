(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('LikeT', LikeT);

    LikeT.$inject = ['$resource'];

    function LikeT ($resource) {
        var resourceUrl =  'api/like-ts/:id';

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
