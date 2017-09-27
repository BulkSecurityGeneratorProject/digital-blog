(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('SeccionCustomService', SeccionCustomService);

    SeccionCustomService.$inject = ['$resource','$http'];

    function SeccionCustomService ($resource,$http) {

        return{
            listarSeccion: function (id) {
               return $http.get('apiCustom/seccionesPorBiblioteca/' + id);
            },
            obtenerBibliotecaPorJhiUser: function(pathParams){
                return $http.get('apiCustom/obtenerBibliotecaPorJhiUser/'+ pathParams);
            },
            agregarSeccion:function (pathParams) {
                return $http.post('apiCustom/agregarSecciones',pathParams.seccion);
            },
            actualizarSeccion: function (pathParams) {
                return $http.put('apiCustom/actualizarSecciones',pathParams.seccion);
            },
            eliminarSeccion: function (id) {
                return $http.delete('api/seccions/'+id);
            }
        };
    }
})();
