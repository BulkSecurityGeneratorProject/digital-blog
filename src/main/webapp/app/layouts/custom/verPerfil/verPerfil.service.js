
(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('VerPerfil', VerPerfil);

    VerPerfil.$inject = ['$resource','$http'];

    function VerPerfil ($resource,$http) {
        return {
            obtenerPublicacionesPorUsuario: function (pathParams) {
                return $http.get('/apiCustom/getPublicaciones/' + pathParams);
            },
            obtenerSeguidores: function (pathParams) {
                return $http.get('/apiCustom/usuariosSeguidores/' + pathParams);
            },
            seguir: function (pathParams) {
                return $http.post('/api/seguidors' + pathParams);
            },
            obtenerSeguimiento: function (pathParams) {
                 console.log(pathParams);
                return $http.post('/apiCustom/seguidors',pathParams);
            }
        }
    }
})();
