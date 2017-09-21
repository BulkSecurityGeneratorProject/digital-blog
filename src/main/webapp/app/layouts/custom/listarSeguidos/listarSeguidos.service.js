(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('ListarSeguidos', ListarSeguidos);

    ListarSeguidos.$inject = ['$http'];

    function ListarSeguidos ($http) {
        return {
            obtenerSeguidos: function (pathParams) {
                return $http.get('/apiCustom/usuariosSeguidos/' + pathParams);
            }
        }
    }
})();

