(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('TemaCustomService', TemaCustomService);

    TemaCustomService.$inject = ['$resource','$http'];

    function TemaCustomService ($resource,$http) {
            return {
                agregarTema: function(pathParams) {
                    return $http.post('apiCustom/temas',pathParams.type);
                },
                listarTemas: function() {
                    return $http.get('api/temas');
                }
            };
    }
})();
