/**
 * Created by jose_ on 12/4/2017.
 */

(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        //Estandar: Nombre+CustomService
        .factory('CrearPublicacionCompartida', CrearPublicacionCompartida);

    CrearPublicacionCompartida.$inject = ['$resource','$http'];


    function CrearPublicacionCompartida ($resource,$http) {
        return {
            // obtenerPublicacionCompartida: function(pathParams){
            //     return $http.get('apiCustom/capituloAnterior',pathParams.capitulo);
            // },
            terminarParticipacionCapitulo: function(pathParams){
                return $http.post('apiCustom/terminarParticipacion', pathParams.capitulo);
            }
        };
    }

})();

