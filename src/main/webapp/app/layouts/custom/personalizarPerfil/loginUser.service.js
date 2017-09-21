(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('LoginUser',LoginUser);
    LoginUser.$inject = ['$resource'];
    function LoginUser ($resource) {
        var resourceUrl =  'api/users/:login';
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray:false}
        });
    }
})();
