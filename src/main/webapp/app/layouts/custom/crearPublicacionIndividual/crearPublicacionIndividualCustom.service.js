
(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        //Estandar: Nombre+CustomService
        .factory('CrearPublicacionIndividual', CrearPublicacionIndividual);

    CrearPublicacionIndividual.$inject = ['$resource','$http'];


    function CrearPublicacionIndividual ($resource,$http) {
        return {
            crearPublicacionIndividual: function(pathParams) {
                return $http.post('apiCustom/crearPublicacionIn',pathParams.publicacion);
            },
            crearCapitulo: function(pathParams) {
                return $http.post('apiCustom/crearCapituloIn',pathParams.capitulo);
            },
            crearPagina: function(pathParams) {
                return $http.put('apiCustom/crearPaginaIn',pathParams.pagina);
            },
            actualizarPagina: function(pathParams) {
                return $http.put('apiCustom/crearPaginaIn',pathParams.pagina);
            },
            verPaginaAnterior: function(pathParams) {
                return $http.get('apiCustom/verPaginaAnterior/'+pathParams.id+'&'+pathParams.idCapitulo);
            },
            publicar: function(pathParams){
                return $http.put('api/publicacions',pathParams.publicacion);
            },
            obtenerCapituloAnterior: function(pathParams){
                return $http.get('apiCustom/capituloAnterior/'+pathParams.idPub+ '&'+ pathParams.numeroCap);
            }
        };
    }

})();
