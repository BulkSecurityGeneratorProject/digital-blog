(function() {
    'use strict';
    angular
        .module('digitalBlogApp')
        .factory('AgregarSeccionCustomService', AgregarSeccionCustomService);

    AgregarSeccionCustomService.$inject = ['$resource','$http'];

    function AgregarSeccionCustomService ($resource,$http) {

        return{
            agregarPublicacionAseccion:function (pathParams) {
                return $http.post('apiCustom/seccion-publicacion', pathParams.seccionPublicacion);
            },
        };
    }
})();
