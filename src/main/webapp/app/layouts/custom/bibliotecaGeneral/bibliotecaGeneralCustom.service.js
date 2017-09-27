(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('BibliotecaGeneralService', BibliotecaGeneralService);

    BibliotecaGeneralService.$inject = ['$resource','$http'];

    function BibliotecaGeneralService ($resource,$http) {
        var resourceUrl =  'api/bibliotecas/:id';

        return {
            cargarPublicacionesPorBusqueda:function (pathParams) {
                return $http.get('apiCustom/publicacionesPorBusqueda/'+ pathParams.nombre);
            },
            cargarPublicacionPublicada:function () {
                return $http.get('apiCustom/publicacionPublicada');
            },
            cargarPublicacionPorCategoriaYTema:function(pathParams){
                return $http.get('apiCustom/publicacionPorCategoriaYTema/'+pathParams.nombre +'&'+pathParams.temaNombre);
            },
            likePublicacion :function (pathParams) {
                return $http.post('apiCustom/likePublicacion',pathParams.like);
            },
            obtenerLikeDeUsuario:function (pathParams) {
              return $http.get('apiCustom/likeDeUsuario/'+ pathParams.idUsuario +'&'+ pathParams.idPublicacion);
            },
            obtenerLikesDeUnUsuario:function (idUsuario) {
                return $http.get('apiCustom/likesDeUsuario/'+idUsuario);
            },
            updatePublicacion:function (publicacion) {
                return $http.put('apiCustom/updatePublicacion/',publicacion);
            }
        }
    }
})();
