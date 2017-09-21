(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('ListarSeguidores', ListarSeguidores);

    ListarSeguidores.$inject = ['$resource','$http'];

    function ListarSeguidores ($resource,$http) {
        return {
            obtenerSeguidores: function (pathParams) {
                return $http.get('/apiCustom/usuariosSeguidores/' + pathParams);
            }
        }
    }
})();

