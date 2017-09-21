/**
 * Created by jose_ on 20/3/2017.
 */

(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('VisorPublicacion', VisorPublicacion);

    VisorPublicacion.$inject = ['$resource','$http','$q'];


    function VisorPublicacion ($resource,$http,$q) {
        return {
            obtenerPublicacion: function(pathParams) {
                return $http.get('api/publicacions/'+pathParams.idPublicacion);
            },
            obtenerCapitulos: function(pathParams) {
                return $http.get('apiCustom/capitulosPublicacion/'+pathParams.idPublicacion);
            },
            obtenerPaginas: function(pathParams) {
                var objPromise = $http.get('apiCustom/paginasCapitulo/'+pathParams.idCapitulo);
                return objPromise;
            },
            obtenerUsuarioById: function(pathParams){
                return $http.get('api/usuarios/'+pathParams.idUsuario);
            }
        };
    }

})();
