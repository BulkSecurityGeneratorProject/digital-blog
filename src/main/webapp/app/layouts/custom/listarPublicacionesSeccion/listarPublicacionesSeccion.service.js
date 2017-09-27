(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('listarPublicacionesPorSeccionService', listarPublicacionesPorSeccionService);

    listarPublicacionesPorSeccionService.$inject = ['$resource','$http'];

    function listarPublicacionesPorSeccionService ($resource,$http) {
        return {
            getPublicacionTema: function (pathParams) {
                return $http.get('apiCustom/getPublicacionesTema/' + pathParams.seccion);
            },
            eliminar: function (pathParams) {
                return $http.post('apiCustom/publicacions-seccion',pathParams.obj);
            }
        }
    }
})();
