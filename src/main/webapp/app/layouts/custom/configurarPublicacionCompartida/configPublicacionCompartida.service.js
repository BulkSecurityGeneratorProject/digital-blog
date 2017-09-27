
(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('ConfigurarPublicacionCompartida', ConfigurarPublicacionCompartida);

    ConfigurarPublicacionCompartida.$inject = ['$resource','$http'];


    function ConfigurarPublicacionCompartida ($resource,$http) {
        return {
            configPublicacionCompartida: function (pathParams) {
                return $http.post('apiCustom/crearPublicacionIn',pathParams.publicacion);
            },
            crearCapitulos: function (pathParams) {
                return $http.post('apiCustom/crearCapituloCompatida',pathParams.capitulo);
            },
            registrarColaboradores: function (pathParams) {
                return $http.post('apiCustom/coolaboradores',pathParams.colaboradores);

            }
        }
    }

})();
